package org.mailboxclubindonesia.backoffice.repository;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.UserInstitution;
import org.springframework.data.repository.ListCrudRepository;

public interface UserInstitutionRepository extends ListCrudRepository<UserInstitution, UUID> {

}
