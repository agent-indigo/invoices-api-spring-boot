package com.keyin.hynes.braden.invoices.api.interfaces.repositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import com.keyin.hynes.braden.invoices.api.documents.UserDocument;
@Repository
public interface UserRepository extends MongoRepository<UserDocument, ObjectId> {
  UserDetails findByUsername(String username);
}