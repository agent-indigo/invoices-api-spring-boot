package com.keyin.hynes.braden.invoices.api.interfaces.repositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.keyin.hynes.braden.invoices.api.documents.InvoiceDocument;
@Repository
public interface InvoiceRepository extends MongoRepository<InvoiceDocument, ObjectId> {}