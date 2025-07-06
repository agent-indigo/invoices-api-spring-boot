package com.keyin.hynes.braden.invoices.api.abstracts;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
@Data
@MappedSuperclass
public abstract class EntityBase {
  @Id
  private final UUID id = UUID.randomUUID();
  @CreatedDate
  private final LocalDateTime createdAt = LocalDateTime.now();
  @LastModifiedDate
  private LocalDateTime updatedAt = LocalDateTime.now();
}