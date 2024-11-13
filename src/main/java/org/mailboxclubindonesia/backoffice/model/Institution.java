package org.mailboxclubindonesia.backoffice.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "institutions")
@EntityListeners(AuditingEntityListener.class)
public class Institution extends BaseEntity {

  @NotNull
  private String name;

  @Enumerated(EnumType.STRING)
  private InstitutionType type;

}
