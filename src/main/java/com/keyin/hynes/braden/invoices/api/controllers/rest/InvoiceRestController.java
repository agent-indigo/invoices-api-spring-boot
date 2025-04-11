package com.keyin.hynes.braden.invoices.api.controllers.rest;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.keyin.hynes.braden.invoices.api.documents.InvoiceDocument;
import com.keyin.hynes.braden.invoices.api.services.InvoiceService;
@RestController
@CrossOrigin
@RequestMapping("/invoices")
public final class InvoiceRestController {
  @Autowired
  private InvoiceService service = new InvoiceService();
  @GetMapping
  public List<InvoiceDocument> getAll() {
    return service.getAll();
  }
  @PostMapping
  public InvoiceDocument add(@RequestBody InvoiceDocument invoice) {
    return service.add(invoice);
  }
  @PatchMapping("/{id}")
  public InvoiceDocument edit (
    @PathVariable("id") ObjectId id,
    @RequestBody InvoiceDocument changes
  ) {
    return service.edit(
      id,
      changes
    );
  }
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") ObjectId id) {
    service.delete(id);
  }
}