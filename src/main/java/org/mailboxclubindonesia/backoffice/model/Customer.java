package org.mailboxclubindonesia.backoffice.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Embedded.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Nullable
  @Column(name = "user_id")
  private UUID userId;

  @NotNull
  @Column(name = "institution_id")
  private UUID institutionId;

  @NotEmpty
  private String firstname;

  @NotEmpty
  private String lastname;

  @NotEmpty
  @Column(unique = true)
  @Pattern(regexp = "^\\+\\d{1,3}\\s?\\d{1,14}$", message = "Invalid phone number pattern")
  private String phone;

  @NotEmpty
  @Email
  private String email;

  @Nullable
  @CreatedDate
  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Nullable
  @LastModifiedDate
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;

  protected Customer() {
  }

  @JsonIgnore
  public String getFullName() {
    return firstname + " " + lastname;
  }

}
