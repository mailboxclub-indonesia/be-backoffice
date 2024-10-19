package org.mailboxclubindonesia.backoffice.controller;

import org.mailboxclubindonesia.backoffice.model.Institution;
import org.mailboxclubindonesia.backoffice.model.InstitutionAddress;
import org.mailboxclubindonesia.backoffice.service.GoogleMapService;
import org.mailboxclubindonesia.backoffice.service.InstitutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

  private final InstitutionService institutionService;
  private final GoogleMapService gmapService;

  public InstitutionController(InstitutionService institutionService, GoogleMapService gmapService) {
    this.institutionService = institutionService;
    this.gmapService = gmapService;
  }

  @PostMapping()
  public ResponseEntity<Institution> postInstitution(@Valid @RequestBody Institution institution) {
    try {
      Institution newInstitution = institutionService.saveInstitution(institution);
      return ResponseEntity.status(HttpStatus.CREATED).body(newInstitution);
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to create new institution: " + exception.getMessage(), exception);
    }
  }

  @PostMapping("/address")
  public ResponseEntity<InstitutionAddress> postInstitutionAddress(@RequestBody InstitutionAddress institutionAddress) {
    try {
      InstitutionAddress institutionAddressWithGeometry = gmapService.setGeometry(institutionAddress);
      InstitutionAddress newInstitutionAddress = institutionService
          .saveInstitutionAddress(institutionAddressWithGeometry);
      return ResponseEntity.status(HttpStatus.CREATED).body(newInstitutionAddress);
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to create new institution address: " + exception.getMessage(), exception);
    }
  }
}
