package com.keyin.hynes.braden.invoices.api.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.UserRepository;
import com.keyin.hynes.braden.invoices.api.records.ConfigStatus;
@Service
public final class UserService implements UserDetailsService {
  @Autowired
  private UserRepository repo;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repo.findByUsername(username);
  }
  public ConfigStatus geConfigStatus() {
    return new ConfigStatus(
      repo.findAllByAuthority(new SimpleGrantedAuthority("root")).size() > 0
    );
  }
}