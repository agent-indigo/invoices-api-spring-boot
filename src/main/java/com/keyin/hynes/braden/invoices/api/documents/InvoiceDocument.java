package com.keyin.hynes.braden.invoices.api.documents;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import com.keyin.hynes.braden.invoices.api.abstracts.DataDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Document(collection = "invoices")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class InvoiceDocument extends DataDocument {
  private String vendor;
  private String invoiceId;
  private Date date;
  private Float subtotal;
  private Float hst;
  private Float total;
}