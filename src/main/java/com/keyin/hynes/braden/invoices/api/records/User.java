package com.keyin.hynes.braden.invoices.api.records;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.context.support.BeanDefinitionDsl.Role;
public record User(
  UUID id,
  LocalDateTime createdAt,
  LocalDateTime updatedAt,
  String username,
  Role role,
  String token
) {}