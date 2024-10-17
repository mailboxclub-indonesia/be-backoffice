package org.mailboxclubindonesia.backoffice.repository;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

  @Query("SELECT * FROM users WHERE email = :email")
  User findUserByEmail(String email);

}
