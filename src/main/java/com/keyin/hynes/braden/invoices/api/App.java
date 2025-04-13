package com.keyin.hynes.braden.invoices.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {
  "com.keyin.hynes.braden.invoices.api.config",
  "com.keyin.hynes.braden.invoices.api.controllers",
  "com.keyin.hynes.braden.invoices.api.entities",
  "com.keyin.hynes.braden.invoices.api.interfaces.repositories",
  "com.keyin.hynes.braden.invoices.api.services"
})
public class App {
  public static void main(String[] args) {
    SpringApplication.run(
      App.class,
      args
    );
  }
}