package org.mailboxclubindonesia.backoffice.dto.GoogleMapDto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Result {
  private List<AddressComponent> address_components;
  private String formatted_address;
  private Geometry geometry;
  private boolean partial_match;
  private String place_id;
  private PlusCode plus_code;
  private List<String> types;
}
