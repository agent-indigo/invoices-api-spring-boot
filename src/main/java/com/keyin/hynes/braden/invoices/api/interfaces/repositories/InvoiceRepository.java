package com.keyin.hynes.braden.invoices.api.interfaces.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.keyin.hynes.braden.invoices.api.entities.Invoice;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {}