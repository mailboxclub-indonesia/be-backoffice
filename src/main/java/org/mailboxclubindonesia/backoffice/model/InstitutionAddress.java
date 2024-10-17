package org.mailboxclubindonesia.backoffice.model;

import java.util.UUID;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
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
@Table(name = "institution_addresses")
public class InstitutionAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(name = "institution_id")
  private UUID institutionId;

  @NotNull
  private String address;

  @NotNull
  private String state;

  @NotNull
  private String city;

  @Nullable
  private String subdistrict;

  @Nullable
  private String ward;

  @Nullable
  private float latitude;

  @Nullable
  private float longitude;
}
