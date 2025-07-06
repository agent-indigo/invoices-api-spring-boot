package com.keyin.hynes.braden.invoices.api.services;
import java.util.UUID;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.entities.User;
import com.keyin.hynes.braden.invoices.api.enums.Role;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.UserRepository;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
import com.keyin.hynes.braden.invoices.api.records.NewPassword;
import jakarta.servlet.http.HttpServletRequest;
@Service
public final class UserManagementService {
  private final UserRepository userRepository;
  private User target;
  private final JwtService jwtService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final String emptyFieldMessage = "At least one field is empty.";
  private final String failedPasswordConfirmationMessage = "Passwords don't match.";
  @Autowired
  public UserManagementService(
    final UserRepository userRepository,
    final JwtService jwtService,
    final BCryptPasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }
  public User add(Credentials credentials) throws BadRequestException {
    if (
      credentials.username() == null |
      credentials.password() == null |
      credentials.confirmPassword() == null
    ) {
      throw new BadRequestException(emptyFieldMessage);
    } else if (!credentials.password().equals(credentials.confirmPassword())) {
      throw new BadRequestException(failedPasswordConfirmationMessage);
    } else {
      return userRepository.save(new User(
        credentials.username(),
        passwordEncoder.encode(credentials.password())
      ));
    }
  }
  public User changePassword(
    final HttpServletRequest request,
    NewPassword newPassword
  ) throws BadRequestException {
    target = userRepository.findById(jwtService.getUserId(request.getHeader("Authorization").substring(7))).get();
    if (
      newPassword.currentPassword() == null |
      newPassword.newPassword() == null |
      newPassword.confirmNewPassword() == null
    ) {
      throw new BadRequestException(emptyFieldMessage);
    } else if (!newPassword.newPassword().equals(newPassword.confirmNewPassword())) {
      throw new BadRequestException(failedPasswordConfirmationMessage);
    } else if (!passwordEncoder.matches(
      newPassword.newPassword(),
      target.getPassword()
    )) {
      throw new BadRequestException("Incorrect password.");
    } else if (passwordEncoder.matches(
      newPassword.newPassword(),
      target.getPassword()
    )) {
      throw new BadRequestException("New password cannot be the same as the current password.");
    } else {
      target.setPassword(passwordEncoder.encode(newPassword.newPassword()));
      return userRepository.save(target);
    }
  }
  public User resetPassword(
    final HttpServletRequest request,
    final UUID targetUserId,
    NewPassword newPassword
  ) throws BadRequestException {
    if (jwtService.getUserId(request.getHeader("Authorization").substring(7)) == targetUserId) {
      throw new BadRequestException("You can't change your own password this way.");
    } else if (
      newPassword.newPassword() == null |
      newPassword.confirmNewPassword() == null
    ) {
      throw new BadRequestException(emptyFieldMessage);
    } else if (!newPassword.newPassword().equals(newPassword.confirmNewPassword())) {
      throw new BadRequestException(failedPasswordConfirmationMessage);
    } else {
      target = userRepository.findById(targetUserId).get();
      target.setPassword(passwordEncoder.encode(newPassword.newPassword()));
      return userRepository.save(target);
    }
  }
  public void delete(final UUID id) throws BadRequestException {
    if (userRepository.findById(id).get().getRole() == Role.root) {
      throw new BadRequestException("The root user shouldn't be deleted.");
    } else {
      userRepository.deleteById(id);
    }
  }
}