package com.keyin.hynes.braden.invoices.api.documents;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.keyin.hynes.braden.invoices.api.abstracts.DataDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Document(collection = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UserDocument extends DataDocument implements UserDetails {
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
}