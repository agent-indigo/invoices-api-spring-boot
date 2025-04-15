package com.keyin.hynes.braden.invoices.api.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.keyin.hynes.braden.invoices.api.services.UserService;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  private UserService userDetailsService = new UserService();
  private DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity chain) throws Exception {
    chain.authenticationProvider(authenticationProvider());
    chain.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/users/login").permitAll();
      auth.requestMatchers("/config").permitAll();
      auth.anyRequest().authenticated();
      auth.requestMatchers("/users/changePassword").hasAuthority("user");
      auth.requestMatchers("/users/logout").hasAuthority("user");
      auth.requestMatchers("/users").hasAuthority("root");
      auth.anyRequest().hasAuthority("user");
    });
    return chain.build();
  }
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
    return auth.getAuthenticationManager();
  }
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    auth.setUserDetailsService(userDetailsService);
    auth.setPasswordEncoder(passwordEncoder());
    return auth;
  }
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
}