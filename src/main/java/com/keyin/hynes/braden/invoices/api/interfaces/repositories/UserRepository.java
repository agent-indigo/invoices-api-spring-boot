package com.keyin.hynes.braden.invoices.api.interfaces.repositories;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import com.keyin.hynes.braden.invoices.api.entities.UserEntity;
import com.keyin.hynes.braden.invoices.api.enums.Role;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  UserDetails findByUsername(String username);
  List<UserEntity> findAllByRole(Role role);
}