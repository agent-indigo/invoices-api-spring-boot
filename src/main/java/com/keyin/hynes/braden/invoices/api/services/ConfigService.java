package com.keyin.hynes.braden.invoices.api.services;
import java.util.Set;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.entities.User;
import com.keyin.hynes.braden.invoices.api.enums.Role;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.UserRepository;
import com.keyin.hynes.braden.invoices.api.records.ConfigStatus;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
@Service
public final class ConfigService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  @Autowired
  public ConfigService(
    final UserRepository userRepository,
    final BCryptPasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  private boolean rootExists() {
    return userRepository.findAllByRole(Role.root).size() > 0;
  }
  public ConfigStatus getConfigStatus() {
    return new ConfigStatus(
      rootExists()
    );
  }
  public UserDetails createRootUser(Credentials credentials) throws BadRequestException {
    if (rootExists()) {
      throw new BadRequestException("The root user already exists.");
    } else if (
      credentials.password() == null ||
      credentials.confirmPassword() == null
    ) {
      throw new BadRequestException("At least one field is empty.");
    } else if (!credentials.password().equals(credentials.confirmPassword())) {
      throw new BadRequestException("Passwords don't match.");
    } else {
      return userRepository.save(new User(
        "root",
        passwordEncoder.encode(credentials.password()),
        Role.root,
        Set.of(
          new SimpleGrantedAuthority("ROLE_ROOT"),
          new SimpleGrantedAuthority("ROLE_USER")
        )
      ));
    }
  }
}