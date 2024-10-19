package org.mailboxclubindonesia.backoffice.service;

import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.GeocodeResponse;
import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.Geometry;
import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.Location;
import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.Result;
import org.mailboxclubindonesia.backoffice.model.Address;
import org.mailboxclubindonesia.backoffice.restclient.GoogleMapRestClient;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapService {

  public final GoogleMapRestClient gmapRestClient;

  public GoogleMapService(GoogleMapRestClient gmapRestClient) {
    this.gmapRestClient = gmapRestClient;
  }

  public static double getLatitude(GeocodeResponse response) {
    Result firstResult = response.getResults().get(0);
    Geometry geometry = firstResult.getGeometry();
    Location location = geometry.getLocation();

    return location.getLat();
  }

  public static double getLongitude(GeocodeResponse response) {
    Result firstResult = response.getResults().get(0);
    Geometry geometry = firstResult.getGeometry();
    Location location = geometry.getLocation();

    return location.getLng();
  }

  public <T extends Address> T setGeometry(T address) {
    GeocodeResponse response = gmapRestClient.getGeocodeFromAddress(address.getFullAddress());

    double latitude = getLatitude(response);
    double longitude = getLongitude(response);

    address.setLatitude(latitude);
    address.setLongitude(longitude);

    return address;
  }

}
