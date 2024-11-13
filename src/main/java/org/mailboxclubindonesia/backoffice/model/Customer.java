package org.mailboxclubindonesia.backoffice.model;

import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
public class Customer extends Person {

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "institution_id")
  private UUID institutionId;

  @NotEmpty
  @Column(unique = true)
  @Pattern(regexp = "^\\+\\d{1,3}\\s?\\d{1,14}$", message = "Invalid phone number pattern")
  private String phone;

  @Email
  private String email;

}
