package org.mailboxclubindonesia.backoffice.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
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

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime LastModifiedDate;

  protected User() {
  }

  public User(UUID id, String email, String hash, String salt) {
    this.id = id;
    this.email = email;
    this.hash = hash;
    this.salt = salt;
  }

  public void setLastLogin() {
    this.lastLogin = LocalDateTime.now();
  }

}
