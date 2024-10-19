package org.mailboxclubindonesia.backoffice.service;

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

  public InstitutionAddress saveInstitutionAddress(InstitutionAddress institutionAddress) {
    return this.institutionAddressRepository.save(institutionAddress);
  }
}
