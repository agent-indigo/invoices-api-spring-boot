package com.keyin.hynes.braden.invoices.api.interfaces.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.keyin.hynes.braden.invoices.api.entities.InvoiceEntity;
@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {}