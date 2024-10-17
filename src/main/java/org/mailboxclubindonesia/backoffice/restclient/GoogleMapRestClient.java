package org.mailboxclubindonesia.backoffice.restclient;

import org.mailboxclubindonesia.backoffice.config.GoogleMapConfig;
import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.GeocodeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GoogleMapRestClient {

  private final GoogleMapConfig gmapConfig;
  private final RestClient restClient;

  public GoogleMapRestClient(GoogleMapConfig gmapConfig, RestClient.Builder builder) {
    this.gmapConfig = gmapConfig;
    this.restClient = builder
        .baseUrl("https://maps.googleapis.com")
        .build();
  }

  public GeocodeResponse getGeocodeFromAddress(String address) {
    GeocodeResponse response = restClient.get()
        .uri(uriBuilder -> uriBuilder.path("/maps/api/geocode/json")
            .queryParam("address", address)
            .queryParam("key", gmapConfig.getKey())
            .build())
        .retrieve()
        .body(GeocodeResponse.class);

    return response;
  }

}
