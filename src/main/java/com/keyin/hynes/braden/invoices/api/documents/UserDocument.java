package com.keyin.hynes.braden.invoices.api.documents;
import java.util.List;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
@Data
@Document(collection = "users")
public final class UserDocument implements UserDetails {
  @Indexed(unique = true)
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
}