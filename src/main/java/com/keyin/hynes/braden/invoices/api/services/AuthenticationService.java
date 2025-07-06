package com.keyin.hynes.braden.invoices.api.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
@Service
public final class AuthenticationService {
  @Autowired
  private AuthenticationManager authenticationManager;
  private Authentication authentication;
  public String login (Credentials credentials) {
    authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
      credentials.username(),
      credentials.password()
    ));
    return authentication.isAuthenticated()? "Welcome!" : "Unauthorized.";
  }
}