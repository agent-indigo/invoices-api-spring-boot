package com.keyin.hynes.braden.invoices.api.services;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.entities.Invoice;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.InvoiceRepository;
@Service
public final class InvoiceService {
  private final InvoiceRepository invoiceRepository;
  private Invoice target;
  @Autowired
  public InvoiceService(final InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }
  /**
   * @name    getAll
   * @desc    List all invoices
   * @route   GET /invoices
   * @access  private
   */
  public List<Invoice> getAll() {
    return invoiceRepository.findAll();
  }
  /**
   * @name    add
   * @desc    Add an invoice
   * @route   POST /invoices
   * @access  private
   */
  public Invoice add(final Invoice invoice) {
    return invoiceRepository.save(invoice);
  }
  /**
   * @name    edt
   * @desc    Edit an invoice
   * @route   changes /invoices/:id
   * @access  private
   */
  public Invoice edit(
    final UUID id,
    final Invoice changes
  ) {
    target = invoiceRepository.findById(id).get();
    if (changes.getVendor() != null) target.setVendor(changes.getVendor());
    if (changes.getSubtotal() != null) target.setSubtotal(changes.getSubtotal());
    if (changes.getHst() != null) target.setHst(changes.getHst());
    if (changes.getTotal() != null) target.setTotal(changes.getTotal());
    if (changes.getInvoiceId() != null) target.setInvoiceId(changes.getInvoiceId());
    if (changes.getDate() != null) target.setDate(changes.getDate());
    return invoiceRepository.save(target);
  }
  /**
   * @name    delete
   * @desc    Delete an invoice
   * @route   DELETE /invoices/:id
   * @access  private
   */
  public void delete(final UUID id) {
    invoiceRepository.deleteById(id);
  }
}