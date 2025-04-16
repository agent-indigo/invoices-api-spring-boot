package com.keyin.hynes.braden.invoices.api.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import com.keyin.hynes.braden.invoices.api.services.UserService;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  private UserService userDetailsService = new UserService();
  private DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
  private DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
  private DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity chain) throws Exception {
    chain.authenticationProvider(authenticationProvider());
    chain.csrf(AbstractHttpConfigurer::disable);
    chain.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/config/*").permitAll();
      auth.requestMatchers("/users/*").hasRole("ROOT");
      auth.requestMatchers("/users/login").permitAll();
      auth.requestMatchers("/users/logout").hasRole("USER");
      auth.requestMatchers("/users/changePassword").hasRole("USER");
      auth.anyRequest().hasRole("USER");
    });
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
  @Bean
  public RoleHierarchyImpl roleHierarchy() {
    return RoleHierarchyImpl.fromHierarchy("ROLE_ROOT > ROLE_USER");
  }
  @Bean
  public DefaultWebSecurityExpressionHandler expressionHandler() {
    expressionHandler.setRoleHierarchy(roleHierarchy());
    return expressionHandler;
  }
  @Bean
  public DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler() {
    methodSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
    return methodSecurityExpressionHandler;
  }
}