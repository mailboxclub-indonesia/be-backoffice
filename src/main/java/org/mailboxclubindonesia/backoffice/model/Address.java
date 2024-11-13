package org.mailboxclubindonesia.backoffice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Address extends BaseEntity {

  @NotNull
  private String address;

  @Nullable
  private String ward;

  @Nullable
  private String subdistrict;

  @NotNull
  private String city;

  @NotNull
  private String state;

  @Nullable
  private double latitude;

  @Nullable
  private double longitude;

  @JsonIgnore
  public String getFullAddress() {
    String fullAddress = this.address
        + (this.ward != null ? " " + this.ward : "")
        + (this.subdistrict != null ? " " + this.subdistrict : "")
        + " " + this.city
        + " " + this.state;

    return fullAddress.trim();
  }
}
