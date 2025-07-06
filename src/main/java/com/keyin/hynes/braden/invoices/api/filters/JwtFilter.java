package com.keyin.hynes.braden.invoices.api.filters;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.keyin.hynes.braden.invoices.api.services.JwtService;
import com.keyin.hynes.braden.invoices.api.services.UserLookupService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public final class JwtFilter extends OncePerRequestFilter {
  @Autowired
  private final JwtService jwtService = new JwtService();
  @Autowired
  private ApplicationContext applicationContext;
  private String authorizationHeader;
  private String jwt;
  private UUID id;
  private UserDetails userDetails;
  private UsernamePasswordAuthenticationToken authenticationToken;
  @Override
  protected void doFilterInternal(
    final @NonNull HttpServletRequest request,
    final @NonNull HttpServletResponse response,
    final @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      id = jwtService.getUserId(jwt);
      if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        userDetails = applicationContext.getBean(UserLookupService.class).loadUserById(id);
        if (userDetails != null && jwtService.isJwtValid(jwt)) {
          authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
          );
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }
    filterChain.doFilter(
      request,
      response
    );
  }
}