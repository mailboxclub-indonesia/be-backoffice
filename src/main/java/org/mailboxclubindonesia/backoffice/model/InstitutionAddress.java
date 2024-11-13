package org.mailboxclubindonesia.backoffice.model;

import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "institution_addresses")
@EntityListeners(AuditingEntityListener.class)
public class InstitutionAddress extends Address {

  @NotNull
  @Column(name = "institution_id")
  private UUID institutionId;

}
