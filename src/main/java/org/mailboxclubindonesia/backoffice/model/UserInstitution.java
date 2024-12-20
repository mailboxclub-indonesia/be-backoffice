package org.mailboxclubindonesia.backoffice.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "user_institutions")
@EntityListeners(AuditingEntityListener.class)
public class UserInstitution {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(name = "user_id")
  private UUID userId;

  @NotNull
  @Column(name = "institution_id")
  private UUID institutionId;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private UUID createdBy;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime lastModifiedDate;

  @LastModifiedBy
  @Column(name = "updated_by")
  private UUID updatedBy;

  protected UserInstitution() {

  }

  public UserInstitution(UUID userId, UUID institutionId) {
    this.userId = userId;
    this.institutionId = institutionId;
  }
}
