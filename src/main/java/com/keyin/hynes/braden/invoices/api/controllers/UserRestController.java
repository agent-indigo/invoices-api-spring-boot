package com.keyin.hynes.braden.invoices.api.controllers;
import java.util.List;
import java.util.UUID;
import org.apache.coyote.BadRequestException;
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
import com.keyin.hynes.braden.invoices.api.entities.User;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
import com.keyin.hynes.braden.invoices.api.records.NewPassword;
import com.keyin.hynes.braden.invoices.api.services.UserLookupService;
import com.keyin.hynes.braden.invoices.api.services.UserManagementService;
@RestController
@CrossOrigin
@RequestMapping("/users")
public final class UserRestController {
  private final UserManagementService userManagementService;
  private final UserLookupService userLookupService;
  @Autowired
  public UserRestController(
    final UserManagementService userManagementService,
    final UserLookupService userLookupService
  ) {
    this.userManagementService = userManagementService;
    this.userLookupService = userLookupService;
  }
  @GetMapping
  public List<User> list() {
    return userLookupService.loadAllUsers();
  }
  @GetMapping("/{id}")
  public UserDetails get(@PathVariable("id") final UUID id) {
    return userLookupService.loadUserById(id);
  }
  @PostMapping
  public UserDetails add(@RequestBody Credentials credentials) throws BadRequestException {
    return userManagementService.add(credentials);
  }
  @PatchMapping
  public UserDetails changePassword(
    // TO DO: Get this from the JWT
    final UUID id,
    @RequestBody NewPassword newPassword
  ) throws BadRequestException {
    return userManagementService.changePassword(
      id,
      newPassword
    );
  }
  @PatchMapping("/{id}")
  public UserDetails resetPassword(
    // TO DO: Get this from the JWT
    final UUID currentUserId,
    @PathVariable("id") final UUID targetUserId,
    @RequestBody NewPassword newPassword
  ) throws BadRequestException {
    return userManagementService.resetPassword(
      currentUserId,
      targetUserId,
      newPassword
    );
  }
  @DeleteMapping("/{id}")
  public void delete(@PathVariable final UUID id) throws BadRequestException {
    userManagementService.delete(id);
  }
}