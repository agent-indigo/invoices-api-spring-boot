package com.keyin.hynes.braden.invoices.api.abstracts;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.Data;
@Data
public abstract class DataDocument {
  @Id
  private final ObjectId id = new ObjectId();
  @CreatedDate
  private final LocalDateTime createdAt = LocalDateTime.now();
  @LastModifiedDate
  private LocalDateTime updatedAt = LocalDateTime.now();
}