package com.keyin.hynes.braden.invoices.api.services;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyin.hynes.braden.invoices.api.documents.InvoiceDocument;
import com.keyin.hynes.braden.invoices.api.interfaces.repositories.InvoiceRepository;
@Service
public final class InvoiceService {
  @Autowired
  private InvoiceRepository repo;
  private InvoiceDocument target;
  /**
   * @name    getAll
   * @desc    List all invoices
   * @route   GET /invoices
   * @access  private
   */
  public List<InvoiceDocument> getAll() {
    return repo.findAll();
  }
  /**
   * @name    add
   * @desc    Add an invoice
   * @route   POST /invoices
   * @access  private
   */
  public InvoiceDocument add(InvoiceDocument invoice) {
    return repo.save(invoice);
  }
  /**
   * @name    edit
   * @desc    Edit an invoice
   * @route   PATCH /invoices/:id
   * @access  private
   */
  public InvoiceDocument edit(
    ObjectId id,
    InvoiceDocument patch
  ) {
    target = repo.findById(id).get();
    if (patch.getVendor() != null) target.setVendor(patch.getVendor());
    if (patch.getSubtotal() != null) target.setSubtotal(patch.getSubtotal());
    if (patch.getHst() != null) target.setHst(patch.getHst());
    if (patch.getTotal() != null) target.setTotal(patch.getTotal());
    if (patch.getInvoiceId() != null) target.setInvoiceId(patch.getInvoiceId());
    if (patch.getDate() != null) target.setDate(patch.getDate());
    return repo.save(target);
  }
  /**
   * @name    delete
   * @desc    Delete an invoice
   * @route   DELETE /invoices/:id
   * @access  private
   */
  public void delete(ObjectId id) {
    repo.deleteById(id);
  }
}