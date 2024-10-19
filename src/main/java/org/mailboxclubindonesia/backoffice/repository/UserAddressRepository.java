package org.mailboxclubindonesia.backoffice.repository;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.UserAddress;
import org.springframework.data.repository.CrudRepository;

public interface UserAddressRepository extends CrudRepository<UserAddress, UUID> {

}
