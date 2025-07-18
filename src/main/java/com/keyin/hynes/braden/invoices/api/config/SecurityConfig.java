package com.keyin.hynes.braden.invoices.api.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.keyin.hynes.braden.invoices.api.filters.JwtFilter;
import com.keyin.hynes.braden.invoices.api.services.UserLookupService;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final UserLookupService userLookupService;
  private final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
  private final DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
  private final DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
  private final JwtFilter jwtFilter = new JwtFilter();
  @Autowired
  public SecurityConfig(final UserLookupService userLookupService) {
    this.userLookupService = userLookupService;
  }
  @Bean
  public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    daoAuthenticationProvider.setUserDetailsService(userLookupService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }
  @Bean
  public RoleHierarchyImpl roleHierarchy() {
    return RoleHierarchyImpl.fromHierarchy("ROLE_ROOT > ROLE_USER");
  }
  @Bean
  public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
    defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
    return defaultWebSecurityExpressionHandler;
  }
  @Bean
  public DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
    defaultMethodSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
    return defaultMethodSecurityExpressionHandler;
  }
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authenticationProvider(authenticationProvider());
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.httpBasic(Customizer.withDefaults());
    httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    httpSecurity.authorizeHttpRequests(authorization -> {
      authorization.requestMatchers(
        "/users/logout",
        "/users/changePassword",
        "/invoices/*"
      ).hasRole("USER");
      authorization.requestMatchers(
        "/config/*",
        "/users/login"
      ).permitAll();
      authorization.anyRequest().hasRole("ROOT");
    });
    httpSecurity.addFilterBefore(
      jwtFilter,
      UsernamePasswordAuthenticationFilter.class
    );
    return httpSecurity.build();
  }
}