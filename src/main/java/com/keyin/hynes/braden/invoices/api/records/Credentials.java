package com.keyin.hynes.braden.invoices.api.records;
public record Credentials(
  String username,
  String password,
  String confirmPassword
) {}