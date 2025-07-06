package com.keyin.hynes.braden.invoices.api.records;
import java.time.LocalDateTime;
import java.util.UUID;
import com.keyin.hynes.braden.invoices.api.enums.Role;
public record UserWithJwt(
  UUID id,
  LocalDateTime createdAt,
  LocalDateTime updatedAt,
  String username,
  Role role,
  String token
) {}