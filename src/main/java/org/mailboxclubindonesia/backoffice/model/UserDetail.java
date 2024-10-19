package org.mailboxclubindonesia.backoffice.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "user_details")
public class UserDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(name = "user_id")
  private UUID userId;

  @NotEmpty
  private String firstname;

  @NotEmpty
  private String lastname;

  @NotNull
  private Date birthdate;

  @NotEmpty
  @Column(unique = true)
  @Pattern(regexp = "^\\+\\d{1,3}\\s?\\d{1,14}$", message = "Invalid phone number pattern")
  private String phone;

  protected UserDetail() {
  }

  public String getFullName() {
    return firstname + " " + lastname;
  }

}
