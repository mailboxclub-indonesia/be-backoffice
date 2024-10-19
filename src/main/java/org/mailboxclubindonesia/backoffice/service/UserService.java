package org.mailboxclubindonesia.backoffice.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.User;
import org.mailboxclubindonesia.backoffice.model.UserAddress;
import org.mailboxclubindonesia.backoffice.model.UserDetail;
import org.mailboxclubindonesia.backoffice.model.UserInstitution;
import org.mailboxclubindonesia.backoffice.repository.InstitutionRepository;
import org.mailboxclubindonesia.backoffice.repository.UserAddressRepository;
import org.mailboxclubindonesia.backoffice.repository.UserDetailRepository;
import org.mailboxclubindonesia.backoffice.repository.UserInstitutionRepository;
import org.mailboxclubindonesia.backoffice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserDetailRepository userDetailRepository;
  private final UserAddressRepository userAddressRepository;
  private final UserInstitutionRepository userInstitutionRepository;

  private final InstitutionRepository InstitutionRepository;

  public UserService(UserRepository userRepository, UserDetailRepository userDetailRepository,
      UserAddressRepository userAddressRepository, UserInstitutionRepository userInstitutionRepository,
      InstitutionRepository institutionRepository) {
    this.userRepository = userRepository;
    this.userDetailRepository = userDetailRepository;
    this.userAddressRepository = userAddressRepository;
    this.userInstitutionRepository = userInstitutionRepository;

    this.InstitutionRepository = institutionRepository;
  }

  public Optional<User> findUserById(UUID id) {
    return this.userRepository.findById(id);
  }

  public UserDetail saveUserDetail(UserDetail userDetail) {
    UserDetail newUserDetail = this.userDetailRepository.save(userDetail);
    return newUserDetail;
  }

  public Optional<UserDetail> findUserDetailById(UUID id) {
    return this.userDetailRepository.findById(id);
  }

  public UserAddress saveUserAddress(UserAddress userAddress) {
    UserAddress newUserAddress = this.userAddressRepository.save(userAddress);
    return newUserAddress;
  }

  public Optional<UserAddress> findUserAddressById(UUID id) {
    return this.userAddressRepository.findById(id);
  }

  public UserInstitution registerUserUnderInstitution(UUID userId, UUID instititutionId) throws NoSuchElementException {
    boolean userIsExists = this.userRepository.existsById(userId);
    boolean institutionIsExists = this.InstitutionRepository.existsById(instititutionId);

    if (!userIsExists)
      throw new NoSuchElementException("User is not exists");
    if (!institutionIsExists)
      throw new NoSuchElementException("Institution is not exists");

    UserInstitution userInstitution = new UserInstitution(userId, instititutionId);
    UserInstitution newUserInstitution = this.userInstitutionRepository.save(userInstitution);
    return newUserInstitution;
  }

  public void removeUserFromInstitution(UUID id) {
    boolean relationIsExists = this.userInstitutionRepository.existsById(id);
    if (!relationIsExists)
      return;
    this.userInstitutionRepository.deleteById(id);
  }
}
