package org.mailboxclubindonesia.backoffice.controller;

import java.util.Optional;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.Institution;
import org.mailboxclubindonesia.backoffice.model.InstitutionAddress;
import org.mailboxclubindonesia.backoffice.service.GoogleMapService;
import org.mailboxclubindonesia.backoffice.service.InstitutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/{id}")
  public ResponseEntity<Institution> getInstitution(@PathVariable UUID id) {
    try {
      Optional<Institution> institution = institutionService.findInstitutionById(id);

      if (institution.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Institution not found");
      }

      return ResponseEntity.status(HttpStatus.OK).body(institution.get());
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to get institution: " + exception.getMessage(), exception);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Institution> putInstitution(@PathVariable UUID id,
      @Valid @RequestBody Institution institution) {
    try {
      institution.setId(id);
      Institution updatedInstitution = institutionService.saveInstitution(institution);

      return ResponseEntity.status(HttpStatus.OK).body(updatedInstitution);
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to update institution: " + exception.getMessage(), exception);
    }
  }

  @PostMapping("/address")
  public ResponseEntity<InstitutionAddress> postInstitutionAddress(
      @Valid @RequestBody InstitutionAddress institutionAddress) {
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

  @GetMapping("/address/{id}")
  public ResponseEntity<InstitutionAddress> getInstitutionAddress(@PathVariable UUID id) {
    try {
      Optional<InstitutionAddress> institutionAddress = institutionService.findInstitutionAddressById(id);

      if (institutionAddress.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Institution address not found");
      }

      return ResponseEntity.status(HttpStatus.OK).body(institutionAddress.get());
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to get institution address: " + exception.getMessage(), exception);
    }
  }

  @PutMapping("/address/{id}")
  public ResponseEntity<InstitutionAddress> putInstitution(@PathVariable UUID id,
      @Valid @RequestBody InstitutionAddress institutionAddress) {
    try {
      institutionAddress.setId(id);
      InstitutionAddress institutionAddressWithGeometry = gmapService.setGeometry(institutionAddress);
      InstitutionAddress updatedInstitutionAddress = institutionService
          .saveInstitutionAddress(institutionAddressWithGeometry);

      return ResponseEntity.status(HttpStatus.OK).body(updatedInstitutionAddress);
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to update institution address: " + exception.getMessage(), exception);
    }
  }
}
