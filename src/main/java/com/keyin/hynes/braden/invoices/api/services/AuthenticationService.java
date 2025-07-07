package com.keyin.hynes.braden.invoices.api.services;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.entities.User;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.UserRepository;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
import com.keyin.hynes.braden.invoices.api.records.UserWithJwt;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
@Service
public final class AuthenticationService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final HttpServletResponse response;
  private Authentication authentication;
  private User user;
  private UUID userId;
  private Cookie cookie;
  private String jwt;
  @Autowired
  public AuthenticationService(
    final UserRepository userRepository,
    final JwtService jwtService,
    final AuthenticationManager authenticationManager,
    final HttpServletResponse response
  ) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.response = response;
  }
  public ResponseEntity<?> login (Credentials credentials) {
    authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
      credentials.username(),
      credentials.password()
    ));
    if (authentication.isAuthenticated()) {
      user = userRepository.findByUsername(authentication.getName());
      userId = user.getId();
      jwt = jwtService.generateJwt(userId);
      cookie = new Cookie(
        "token",
        jwt
      );
      cookie.setHttpOnly(true);
      cookie.setSecure(true);
      cookie.setPath("/");
      cookie.setMaxAge(2592030); // 30 days
      response.addCookie(cookie);
      return ResponseEntity.ok().body(new UserWithJwt(
        userId,
        user.getCreatedAt(),
        user.getUpdatedAt(),
        user.getUsername(),
        user.getRole(),
        jwt
      ));
    } else {
      return ResponseEntity.status(401).build();
    }
  }
  public ResponseEntity<?> logout () {
    cookie = new Cookie(
      "token",
      null
    );
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(0); // Delete the cookie
    response.addCookie(cookie);
    return ResponseEntity.ok().build();
  }
}