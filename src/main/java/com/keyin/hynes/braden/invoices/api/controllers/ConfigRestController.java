package com.keyin.hynes.braden.invoices.api.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.keyin.hynes.braden.invoices.api.records.ConfigStatus;
import com.keyin.hynes.braden.invoices.api.records.Credentials;
import com.keyin.hynes.braden.invoices.api.services.UserService;
@RestController
@CrossOrigin
@RequestMapping("/config")
public final class ConfigRestController {
  @Autowired
  private UserService service = new UserService();
  @GetMapping("/status")
  public ConfigStatus geConfigStatus() {
    return service.geConfigStatus();
  }
  @PostMapping("/rootUserPassword")
  public UserDetails setRootUserPassword(@RequestBody Credentials credentials) throws Exception {
    return service.setRootUserPassword(credentials);
  }
}