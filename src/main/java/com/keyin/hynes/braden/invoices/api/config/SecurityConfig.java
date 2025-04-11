package com.keyin.hynes.braden.invoices.api.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    return chain.build();
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