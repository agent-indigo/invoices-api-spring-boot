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
import com.keyin.hynes.braden.invoices.api.services.ConfigService;
@RestController
@CrossOrigin
@RequestMapping("/config")
public final class ConfigRestController {
  private final ConfigService configService;
  @Autowired
  public ConfigRestController(final ConfigService configService) {
    this.configService = configService;
  }
  @GetMapping("/status")
  public ConfigStatus getConfigStatus() {
    return configService.getConfigStatus();
  }
  @PostMapping("/rootPassword")
  public UserDetails createRootUser(@RequestBody Credentials credentials) throws Exception {
    return configService.createRootUser(credentials);
  }
}