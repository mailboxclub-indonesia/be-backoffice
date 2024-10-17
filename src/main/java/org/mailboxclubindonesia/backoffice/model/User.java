package org.mailboxclubindonesia.backoffice.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Email
  private String email;

  @NotNull
  private String hash;

  @NotNull
  private String salt;

  protected User() {
  }

  public User(UUID id, String email, String hash, String salt) {
    this.id = id;
    this.email = email;
    this.hash = hash;
    this.salt = salt;
  }
}
