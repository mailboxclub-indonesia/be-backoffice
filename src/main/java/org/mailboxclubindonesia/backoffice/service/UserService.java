package org.mailboxclubindonesia.backoffice.service;

import java.util.Optional;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.User;
import org.mailboxclubindonesia.backoffice.model.UserDetail;
import org.mailboxclubindonesia.backoffice.model.UserAddress;
import org.mailboxclubindonesia.backoffice.repository.UserRepository;
import org.mailboxclubindonesia.backoffice.repository.UserDetailRepository;
import org.mailboxclubindonesia.backoffice.repository.UserAddressRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserDetailRepository userDetailRepository;
  private final UserAddressRepository userAddressRepository;

  public UserService(UserRepository userRepository, UserDetailRepository userDetailRepository,
      UserAddressRepository userAddressRepository) {
    this.userRepository = userRepository;
    this.userDetailRepository = userDetailRepository;
    this.userAddressRepository = userAddressRepository;
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
}
