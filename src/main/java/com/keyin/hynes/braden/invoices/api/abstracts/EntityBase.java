package com.keyin.hynes.braden.invoices.api.abstracts;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
@Data
@MappedSuperclass
public abstract class EntityBase {
  @Id
  private final UUID id = UUID.randomUUID();
  @CreationTimestamp
  private final LocalDateTime createdAt = LocalDateTime.now();
  @UpdateTimestamp
  private LocalDateTime updatedAt = LocalDateTime.now();
}