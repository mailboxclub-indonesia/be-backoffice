package org.mailboxclubindonesia.backoffice.dto.GoogleMapDto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AddressComponent {
  private String long_name;
  private String short_name;
  private List<String> types;
}
