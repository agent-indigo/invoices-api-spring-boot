package com.keyin.hynes.braden.invoices.api.services;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repo.findByUsername(username);
  }
  public ConfigStatus geConfigStatus() {
    return new ConfigStatus(
      repo.findAllByAuthority(new SimpleGrantedAuthority("root")).size() > 0
    );
  }
  public UserDetails setRootUserPassword(Credentials credentials) throws Exception {
    if (repo.findAllByAuthority(new SimpleGrantedAuthority("root")).size() > 0) {
      throw new Exception("The root user already exists.");
    } else if (credentials.password() == null | credentials.confirmPassword() == null) {
      throw new Exception("At least one field is empty.");
    } else if (credentials.password() != credentials.confirmPassword()) {
      throw new Exception("Passwords do not match.");
    } else {
      return repo.save(new UserEntity(
        "root",
        credentials.password(),
        List.of(new SimpleGrantedAuthority("root")),
        true,
        true,
        true,
        true
      ));
    }
  }
  public UserDetails add(Credentials credentials) throws Exception {
    if (credentials.username() == null | credentials.password() == null | credentials.confirmPassword() == null) {
      throw new Exception("At least one field is empty.");
    } else if (credentials.password() != credentials.confirmPassword()) {
      throw new Exception("Passwords do not match.");
    } else {
      return repo.save(new UserEntity(
        credentials.username(),
        credentials.password(),
        List.of(new SimpleGrantedAuthority("user")),
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
    if (newPassword.currentPassword() == null | newPassword.newPassword() == null | newPassword.confirmNewPassword() == null) {
      throw new Exception("At least one field is empty.");
    } else if (newPassword.newPassword() != newPassword.confirmNewPassword()) {
      throw new Exception("Passwords do not match.");
    } else {
      target = repo.findById(id).get();
      target.setPassword(newPassword.newPassword());
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
    } else if (newPassword.newPassword() == null | newPassword.confirmNewPassword() == null) {
      throw new Exception("At least one field is empty.");
    } else if (newPassword.newPassword() != newPassword.confirmNewPassword()) {
      throw new Exception("Passwords do not match.");
    } else {
      target = repo.findById(targetUserId).get();
      target.setPassword(newPassword.newPassword());
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