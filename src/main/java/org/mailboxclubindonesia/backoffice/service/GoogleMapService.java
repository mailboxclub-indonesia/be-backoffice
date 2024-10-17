package org.mailboxclubindonesia.backoffice.service;

import org.mailboxclubindonesia.backoffice.restclient.GoogleMapRestClient;
import org.springframework.stereotype.Service;
import org.mailboxclubindonesia.backoffice.model.UserAddress;

import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.Geometry;
import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.Location;
import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.GeocodeResponse;
import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.Result;

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

  public UserAddress setUserAddressGeometry(UserAddress userAddress) {
    GeocodeResponse response = this.gmapRestClient.getGeocodeFromAddress(userAddress.getFullAddress());

    double latitude = getLatitude(response);
    double longitude = getLongitude(response);

    userAddress.setLatitude(latitude);
    userAddress.setLongitude(longitude);

    return userAddress;
  }
}
