package com.keyin.hynes.braden.invoices.api.records;
public record NewPassword(
  String currentPassword,
  String newPassword,
  String confirmNewPassword
) {}