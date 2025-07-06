package com.keyin.hynes.braden.invoices.api.services;
import java.util.UUID;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.entities.User;
import com.keyin.hynes.braden.invoices.api.enums.Role;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.UserRepository;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
import com.keyin.hynes.braden.invoices.api.records.NewPassword;
@Service
public final class UserManagementService {
  @Autowired
  private UserRepository userRepository;
  private User target;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  private final String emptyFieldMessage = "At least one field is empty.";
  private final String failedPasswordConfirmationMessage = "Passwords don't match.";
  public UserDetails add(Credentials credentials) throws BadRequestException {
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
  public UserDetails changePassword(
    final UUID id,
    NewPassword newPassword
  ) throws BadRequestException {
    target = userRepository.findById(id).get();
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
  public UserDetails resetPassword(
    final UUID currentUserID,
    final UUID targetUserId,
    NewPassword newPassword
  ) throws BadRequestException {
    if (currentUserID == targetUserId) {
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