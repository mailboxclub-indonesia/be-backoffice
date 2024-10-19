package org.mailboxclubindonesia.backoffice.model;

import java.util.UUID;

import org.springframework.lang.Nullable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "institutions")
public class Institution {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  private String name;

  @Nullable
  @Enumerated(EnumType.STRING)
  private InstitutionType type;

}
