package com.keyin.hynes.braden.invoices.api.services;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.entities.User;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.UserRepository;
@Service
public final class UserLookupService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }
  public UserDetails loadUserById(final UUID id) {
    return userRepository.findById(id).get();
  }
  public List<User> loadAllUsers() {
    return userRepository.findAll();
  }
}