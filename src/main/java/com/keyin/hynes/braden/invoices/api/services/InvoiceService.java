package com.keyin.hynes.braden.invoices.api.services;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.entities.InvoiceEntity;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.InvoiceRepository;
@Service
public final class InvoiceService {
  @Autowired
  private InvoiceRepository repo;
  private InvoiceEntity target;
  /**
   * @name    getAll
   * @desc    List all invoices
   * @route   GET /invoices
   * @access  private
   */
  public List<InvoiceEntity> getAll() {
    return repo.findAll();
  }
  /**
   * @name    add
   * @desc    Add an invoice
   * @route   POST /invoices
   * @access  private
   */
  public InvoiceEntity add(InvoiceEntity invoice) {
    return repo.save(invoice);
  }
  /**
   * @name    edt
   * @desc    Edit an invoice
   * @route   changes /invoices/:id
   * @access  private
   */
  public InvoiceEntity edit(
    UUID id,
    InvoiceEntity changes
  ) {
    target = repo.findById(id).get();
    if (changes.getVendor() != null) target.setVendor(changes.getVendor());
    if (changes.getSubtotal() != null) target.setSubtotal(changes.getSubtotal());
    if (changes.getHst() != null) target.setHst(changes.getHst());
    if (changes.getTotal() != null) target.setTotal(changes.getTotal());
    if (changes.getInvoiceId() != null) target.setInvoiceId(changes.getInvoiceId());
    if (changes.getDate() != null) target.setDate(changes.getDate());
    return repo.save(target);
  }
  /**
   * @name    delete
   * @desc    Delete an invoice
   * @route   DELETE /invoices/:id
   * @access  private
   */
  public void delete(UUID id) {
    repo.deleteById(id);
  }
}