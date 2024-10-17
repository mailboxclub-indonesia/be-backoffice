package org.mailboxclubindonesia.backoffice.repository;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.UserDetail;
import org.springframework.data.repository.ListCrudRepository;

public interface UserDetailRepository extends ListCrudRepository<UserDetail, UUID> {

}
