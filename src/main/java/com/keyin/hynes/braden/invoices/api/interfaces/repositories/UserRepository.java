package com.keyin.hynes.braden.invoices.api.interfaces.repositories;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import com.keyin.hynes.braden.invoices.api.entities.User;
import com.keyin.hynes.braden.invoices.api.enums.Role;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  UserDetails findByUsername(final String username);
  Set<UserDetails> findAllByRole(final Role role);
}