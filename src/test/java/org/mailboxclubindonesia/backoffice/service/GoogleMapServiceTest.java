package org.mailboxclubindonesia.backoffice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mailboxclubindonesia.backoffice.config.GoogleMapConfig;
import org.mailboxclubindonesia.backoffice.dto.GoogleMapDto.GeocodeResponse;
import org.mailboxclubindonesia.backoffice.model.UserAddress;
import org.mailboxclubindonesia.backoffice.restclient.GoogleMapRestClient;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class GoogleMapServiceTest {

  @InjectMocks
  private GoogleMapService gmapService;

  @Mock
  private GoogleMapRestClient gmapRestClient;

  @Mock
  private GoogleMapConfig gmapConfig;

  private GeocodeResponse response;

  @BeforeEach
  public void loadMockResponse() throws DatabindException {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      response = objectMapper.readValue(Paths.get("src/main/resources/responses/geocode.json").toFile(),
          GeocodeResponse.class);

    } catch (StreamReadException exception) {
      exception.printStackTrace();
    } catch (IOException exception) {
      exception.printStackTrace();
    } catch (RuntimeException exception) {
      throw exception;
    }
  }

  @Test
  void shouldGetLatitude() {
    double latitude = GoogleMapService.getLatitude(response);
    System.out.println("lat: " + latitude);
    assertEquals(30.8704067, latitude);
  }

  @Test
  void shouldGetLongitude() {
    double longitude = GoogleMapService.getLongitude(response);
    System.out.println("lng: " + longitude);
    assertEquals(-83.2988532, longitude);
  }

  @Test
  void shouldSetUserAddressGeometry() {
    UserAddress userAddress = new UserAddress();
    userAddress.setId(null);
    userAddress.setUserId(null);
    userAddress.setAddress("404 Eager Road");
    userAddress.setCity("Valdosta");
    userAddress.setState("Georgia");
    userAddress.setSubdistrict(null);
    userAddress.setWard(null);

    when(gmapRestClient.getGeocodeFromAddress(userAddress.getFullAddress())).thenReturn(response);

    UserAddress newUserAddress = gmapService.setUserAddressGeometry(userAddress);
    assertEquals(30.8704067, newUserAddress.getLatitude());
    assertEquals(-83.2988532, newUserAddress.getLongitude());
  }
}
