package org.mailboxclubindonesia.backoffice.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "institutions")
public class Institution {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  private String name;

}
