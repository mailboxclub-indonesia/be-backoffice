package org.mailboxclubindonesia.backoffice.service;

import java.util.Optional;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.Institution;
import org.mailboxclubindonesia.backoffice.model.InstitutionAddress;
import org.mailboxclubindonesia.backoffice.repository.InstitutionAddressRepository;
import org.mailboxclubindonesia.backoffice.repository.InstitutionRepository;
import org.springframework.stereotype.Service;

@Service
public class InstitutionService {

  private final InstitutionRepository institutionRepository;
  private final InstitutionAddressRepository institutionAddressRepository;

  public InstitutionService(InstitutionRepository institutionRepository,
      InstitutionAddressRepository institutionAddressRepository) {
    this.institutionRepository = institutionRepository;
    this.institutionAddressRepository = institutionAddressRepository;
  }

  public Institution saveInstitution(Institution institution) {
    return this.institutionRepository.save(institution);
  }

  public Optional<Institution> findInstitutionById(UUID id) {
    return this.institutionRepository.findById(id);
  }

  public InstitutionAddress saveInstitutionAddress(InstitutionAddress institutionAddress) {
    return this.institutionAddressRepository.save(institutionAddress);
  }

  public Optional<InstitutionAddress> findInstitutionAddressById(UUID id) {
    return this.institutionAddressRepository.findById(id);
  }
}
