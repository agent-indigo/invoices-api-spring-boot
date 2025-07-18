package com.keyin.hynes.braden.invoices.api.entities;
import java.sql.Date;
import com.keyin.hynes.braden.invoices.api.abstracts.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@EqualsAndHashCode(
  callSuper = true
)
@ToString(
  callSuper = true
)
@Entity
@Table(
  name = "invoices"
)
public final class Invoice extends EntityBase {
  private String vendor;
  @Column(unique = true)
  private String invoiceId;
  private Date date;
  private Float subtotal;
  private Float hst;
  private Float total;
}