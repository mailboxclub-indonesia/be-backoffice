
package org.mailboxclubindonesia.backoffice.repository;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.InstitutionAddress;
import org.springframework.data.repository.ListCrudRepository;

public interface InstitutionAddressRepository extends ListCrudRepository<InstitutionAddress, UUID> {

}
