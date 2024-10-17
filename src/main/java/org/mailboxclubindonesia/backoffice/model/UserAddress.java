
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
@Table(name = "user_addresses")
public class UserAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(name = "user_id")
  private UUID userId;

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
  private double latitude;

  @Nullable
  private double longitude;

  public String getFullAddress() {
    String fullAddress = this.address
        + (this.ward != null ? " " + this.ward : "")
        + (this.subdistrict != null ? " " + this.subdistrict : "")
        + " " + this.city
        + " " + this.state;

    return fullAddress.trim();
  }
}
