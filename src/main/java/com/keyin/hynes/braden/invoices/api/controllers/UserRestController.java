package com.keyin.hynes.braden.invoices.api.controllers;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.keyin.hynes.braden.invoices.api.entities.UserEntity;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
import com.keyin.hynes.braden.invoices.api.records.NewPassword;
import com.keyin.hynes.braden.invoices.api.services.UserService;
@RestController
@CrossOrigin
@RequestMapping("/users")
public final class UserRestController {
  @Autowired
  private UserService service = new UserService();
  @GetMapping
  public List<UserEntity> list() {
    return service.list();
  }
  @GetMapping("/{id}")
  public UserDetails get(@PathVariable UUID id) {
    return service.get(id);
  }
  @PostMapping
  public UserDetails add(@RequestBody Credentials credentials) throws Exception {
    return service.add(credentials);
  }
  @PatchMapping
  public UserDetails changePassword(
    // TO DO: Get this from the JWT
    UUID id,
    @RequestBody NewPassword newPassword
  ) throws Exception {
    return service.changePassword(
      id,
      newPassword
    );
  }
  @PatchMapping("/{id}")
  public UserDetails resetPassword(
    // TO DO: Get this from the JWT
    UUID currentUserId,
    @PathVariable("id") UUID targetUserId,
    @RequestBody NewPassword newPassword
  ) throws Exception {
    return service.resetPassword(
      currentUserId,
      targetUserId,
      newPassword
    );
  }
  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}