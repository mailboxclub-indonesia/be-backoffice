package org.mailboxclubindonesia.backoffice.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Person extends BaseEntity {

  @NotNull
  private String firstname;

  @NotNull
  private String lastname;

  @NotNull
  @Enumerated(EnumType.STRING)
  private GenderType gender;

  @NotNull
  private LocalDate birthdate;

  @JsonIgnore
  public String getFullName() {
    return firstname + " " + lastname;
  }
}
