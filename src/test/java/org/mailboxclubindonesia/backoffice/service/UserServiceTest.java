package org.mailboxclubindonesia.backoffice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mailboxclubindonesia.backoffice.repository.InstitutionRepository;
import org.mailboxclubindonesia.backoffice.repository.UserAddressRepository;
import org.mailboxclubindonesia.backoffice.repository.UserDetailRepository;
import org.mailboxclubindonesia.backoffice.repository.UserInstitutionRepository;
import org.mailboxclubindonesia.backoffice.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserDetailRepository userDetailRepository;

  @Mock
  private UserAddressRepository userAddressRepository;

  @Mock
  private UserInstitutionRepository userInstitutionRepository;

  @Mock
  private InstitutionRepository institutionRepository;

  @InjectMocks
  private UserService userService;

  @Test
  void shouldThrowNotFoundForMissingUserId() {
    UUID userId = UUID.randomUUID();
    UUID institutionId = UUID.randomUUID();

    when(userRepository.existsById(userId)).thenReturn(false);

    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () -> userService.registerUserUnderInstitution(userId, institutionId));

    assertEquals(exception.getMessage(), "User is not exists");
  }

  @Test
  void shouldThrowNotFoundForMissingInstitutionId() {
    UUID userId = UUID.randomUUID();
    UUID institutionId = UUID.randomUUID();

    when(userRepository.existsById(userId)).thenReturn(true);
    when(institutionRepository.existsById(institutionId)).thenReturn(false);

    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () -> userService.registerUserUnderInstitution(userId, institutionId));

    assertEquals(exception.getMessage(), "Institution is not exists");
  }
}
