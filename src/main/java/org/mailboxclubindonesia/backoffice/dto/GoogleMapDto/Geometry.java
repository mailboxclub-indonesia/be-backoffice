package org.mailboxclubindonesia.backoffice.dto.GoogleMapDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {
  private Location location;
  private String location_type;
  private Viewport viewport;
}
