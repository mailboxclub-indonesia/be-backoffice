package org.mailboxclubindonesia.backoffice.repository;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.UserInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInstitutionRepository extends JpaRepository<UserInstitution, UUID> {

}
