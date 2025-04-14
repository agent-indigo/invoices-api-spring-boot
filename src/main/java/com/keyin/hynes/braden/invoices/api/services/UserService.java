package com.keyin.hynes.braden.invoices.api.services;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.entities.UserEntity;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.UserRepository;
import com.keyin.hynes.braden.invoices.api.records.ConfigStatus;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
import com.keyin.hynes.braden.invoices.api.records.NewPassword;
@Service
public final class UserService implements UserDetailsService {
  @Autowired
  private UserRepository repo;
  private UserEntity target;
  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
  private SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("user");
  private String emptyFieldMessage = "At least one field is empty.";
  private String failedPasswordConfirmationMessage = "Passwords don't match.";
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repo.findByUsername(username);
  }
  private boolean rootExists() {
    return repo.findAllByAuthoritiesContaining("root").size() > 0;
  }
  public ConfigStatus getConfigStatus() {
    return new ConfigStatus(
      rootExists()
    );
  }
  public UserDetails createRootUser(Credentials credentials) throws Exception {
    if (rootExists()) {
      throw new Exception("The root user already exists.");
    } else if (
      credentials.password() == null |
      credentials.confirmPassword() == null
     ) {
      throw new Exception(emptyFieldMessage);
    } else if (credentials.password() != credentials.confirmPassword()) {
      throw new Exception(failedPasswordConfirmationMessage);
    } else {
      return repo.save(new UserEntity(
        "root",
        passwordEncoder.encode(credentials.password()),
        List.of(
          new SimpleGrantedAuthority("root"),
          userAuthority
        ),
        true,
        true,
        true,
        true
      ));
    }
  }
  public UserDetails add(Credentials credentials) throws Exception {
    if (
      credentials.username() == null |
      credentials.password() == null |
      credentials.confirmPassword() == null
    ) {
      throw new Exception(emptyFieldMessage);
    } else if (credentials.password() != credentials.confirmPassword()) {
      throw new Exception(failedPasswordConfirmationMessage);
    } else {
      return repo.save(new UserEntity(
        credentials.username(),
        passwordEncoder.encode(credentials.password()),
        List.of(userAuthority),
        true,
        true,
        true,
        true
      ));
    }
  }
  public UserDetails changePassword(
    UUID id,
    NewPassword newPassword
  ) throws Exception {
    target = repo.findById(id).get();
    if (
      newPassword.currentPassword() == null |
      newPassword.newPassword() == null |
      newPassword.confirmNewPassword() == null
    ) {
      throw new Exception(emptyFieldMessage);
    } else if (newPassword.newPassword() != newPassword.confirmNewPassword()) {
      throw new Exception(failedPasswordConfirmationMessage);
    } else if (!passwordEncoder.matches(
      newPassword.newPassword(),
      target.getPassword()
    )) {
      throw new Exception("Incorrect password.");
    } else {
      target.setPassword(passwordEncoder.encode(newPassword.newPassword()));
      return repo.save(target);
    }
  }
  public UserDetails resetPassword(
    UUID currentUserID,
    UUID targetUserId,
    NewPassword newPassword
  ) throws Exception {
    if (currentUserID == targetUserId) {
      throw new Exception("You can't change your own password this way.");
    } else if (
      newPassword.newPassword() == null |
      newPassword.confirmNewPassword() == null
    ) {
      throw new Exception(emptyFieldMessage);
    } else if (newPassword.newPassword() != newPassword.confirmNewPassword()) {
      throw new Exception(failedPasswordConfirmationMessage);
    } else {
      target = repo.findById(targetUserId).get();
      target.setPassword(passwordEncoder.encode(newPassword.newPassword()));
      return repo.save(target);
    }
  }
  public UserDetails get(UUID id) {
    return repo.findById(id).get();
  }
  public List<UserEntity> list() {
    return repo.findAll();
  }
  public void delete(UUID id) {
    repo.deleteById(id);
  }
}