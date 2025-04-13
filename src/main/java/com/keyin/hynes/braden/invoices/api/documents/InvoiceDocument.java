package com.keyin.hynes.braden.invoices.api.documents;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
@Data
@Document(collection = "invoices")
public final class InvoiceDocument {
  private String vendor;
  private String invoiceId;
  private Date date;
  private Float subtotal;
  private Float hst;
  private Float total;
}