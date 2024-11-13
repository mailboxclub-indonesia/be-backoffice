
package org.mailboxclubindonesia.backoffice.repository;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.InstitutionAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionAddressRepository extends JpaRepository<InstitutionAddress, UUID> {

}
