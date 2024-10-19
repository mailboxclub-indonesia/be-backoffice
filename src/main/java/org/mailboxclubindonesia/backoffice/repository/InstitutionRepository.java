package org.mailboxclubindonesia.backoffice.repository;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.Institution;
import org.springframework.data.repository.ListCrudRepository;

public interface InstitutionRepository extends ListCrudRepository<Institution, UUID> {

}
