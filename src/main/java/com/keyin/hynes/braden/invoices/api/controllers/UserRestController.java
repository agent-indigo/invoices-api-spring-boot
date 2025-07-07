package com.keyin.hynes.braden.invoices.api.controllers;
import java.util.List;
import java.util.UUID;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.keyin.hynes.braden.invoices.api.entities.User;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
import com.keyin.hynes.braden.invoices.api.records.NewPassword;
import com.keyin.hynes.braden.invoices.api.services.AuthenticationService;
import com.keyin.hynes.braden.invoices.api.services.UserLookupService;
import com.keyin.hynes.braden.invoices.api.services.UserManagementService;
@RestController
@CrossOrigin
@RequestMapping("/users")
public final class UserRestController {
  private final AuthenticationService authenticationService;
  private final UserManagementService userManagementService;
  private final UserLookupService userLookupService;
  @Autowired
  public UserRestController(
    final AuthenticationService authenticationService,
    final UserManagementService userManagementService,
    final UserLookupService userLookupService
  ) {
    this.authenticationService = authenticationService;
    this.userManagementService = userManagementService;
    this.userLookupService = userLookupService;
  }
  @GetMapping
  public List<User> list() {
    return userLookupService.loadAllUsers();
  }
  @GetMapping("/{id}")
  public User get(@PathVariable("id") final UUID id) {
    return userLookupService.loadUserById(id);
  }
  @PostMapping
  public User add(@RequestBody Credentials credentials) throws BadRequestException {
    return userManagementService.add(credentials);
  }
  @PatchMapping
  public User changePassword(
    @RequestHeader("Authorization") String authorizationHeader,
    @RequestBody NewPassword newPassword
  ) throws BadRequestException {
    return userManagementService.changePassword(
      authorizationHeader,
      newPassword
    );
  }
  @PatchMapping("/{id}")
  public User resetPassword(
    @RequestHeader("Authorization") String authorizationHeader,
    @PathVariable("id") final UUID targetUserId,
    @RequestBody NewPassword newPassword
  ) throws BadRequestException {
    return userManagementService.resetPassword(
      authorizationHeader,
      targetUserId,
      newPassword
    );
  }
  @DeleteMapping("/{id}")
  public void delete(@PathVariable final UUID id) throws BadRequestException {
    userManagementService.delete(id);
  }
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Credentials credentials) {
    return authenticationService.login(credentials);
  }
  @GetMapping("/logout")
  public ResponseEntity<?> logout() {
    return authenticationService.logout();
  }
}