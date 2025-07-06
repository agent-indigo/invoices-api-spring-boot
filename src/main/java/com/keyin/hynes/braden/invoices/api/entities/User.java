package com.keyin.hynes.braden.invoices.api.entities;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keyin.hynes.braden.invoices.api.abstracts.EntityBase;
import com.keyin.hynes.braden.invoices.api.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
  callSuper = true
)
@ToString(
  callSuper = true
)
@Entity
@Table(
  name = "users"
)
public final class User extends EntityBase implements UserDetails {
  @Column(
    unique = true
  )
  private String username;
  @JsonIgnore
  private String password;
  private Role role = Role.user;
  private Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_USER"));
  private boolean accountNonExpired = true;
  private boolean accountNonLocked = true;
  private boolean credentialsNonExpired = true;
  private boolean enabled = true;
  public User(
    final String username,
    final String password
  ) {
    this.username = username;
    this.password = password;
  }
  public User(
    final String username,
    final String password,
    final Role role,
    final Set<SimpleGrantedAuthority> authorities
  ) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.authorities = authorities;
  }
}