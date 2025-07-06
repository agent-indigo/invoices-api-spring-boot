package com.keyin.hynes.braden.invoices.api.controllers;
import java.util.List;
import java.util.UUID;
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
import com.keyin.hynes.braden.invoices.api.entities.Invoice;
import com.keyin.hynes.braden.invoices.api.services.InvoiceService;
@RestController
@CrossOrigin
@RequestMapping("/invoices")
public final class InvoiceRestController {
  private final InvoiceService service;
  @Autowired
  public InvoiceRestController(final InvoiceService service) {
    this.service = service;
  }
  @GetMapping
  public List<Invoice> getAll() {
    return service.getAll();
  }
  @PostMapping
  public Invoice add(@RequestBody Invoice invoice) {
    return service.add(invoice);
  }
  @PatchMapping("/{id}")
  public Invoice edit (
    @PathVariable("id") UUID id,
    @RequestBody Invoice changes
  ) {
    return service.edit(
      id,
      changes
    );
  }
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") UUID id) {
    service.delete(id);
  }
}