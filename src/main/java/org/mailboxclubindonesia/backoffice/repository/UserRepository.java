package org.mailboxclubindonesia.backoffice.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, UUID> {

  User findUserByEmail(String email);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = "UPDATE users SET last_login = ?2 WHERE id = ?1")
  void updateLastLogin(UUID id, LocalDateTime lastLogin);
}
