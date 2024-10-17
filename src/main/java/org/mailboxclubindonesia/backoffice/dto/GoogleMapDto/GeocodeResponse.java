package org.mailboxclubindonesia.backoffice.dto.GoogleMapDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GeocodeResponse {
  private List<Result> results;
  private String status;
}
