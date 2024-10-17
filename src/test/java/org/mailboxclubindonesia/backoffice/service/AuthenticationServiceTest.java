package org.mailboxclubindonesia.backoffice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mailboxclubindonesia.backoffice.config.JwtConfig;
import org.mailboxclubindonesia.backoffice.exception.PasswordMissmatchException;
import org.mailboxclubindonesia.backoffice.model.User;
import org.mailboxclubindonesia.backoffice.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AuthenticationService authenticationService;

  @InjectMocks
  private JwtConfig jwtCongig;

  private PasswordEncoder passwordEncoder;

  private JwtConfig jwtConfig;

  @BeforeEach
  public void setup() {
    passwordEncoder = new BCryptPasswordEncoder();

    authenticationService = new AuthenticationService(userRepository, jwtConfig);
  }

  @Test
  void shouldGenerateSalt() {
    String salt = authenticationService.generateSalt();
    System.out.println("salt: " + salt);

    boolean isBase64 = salt.matches("^[A-Za-z0-9+/]*(={0,2})$");

    assertTrue(isBase64);
  }

  @Test
  void shouldCreateUserPassword() {
    String email = "marvin.white@mailbox.org";
    String password = "password";
    String salt = authenticationService.generateSalt();
    String hash = passwordEncoder.encode(password + salt);

    User user = new User(null, email, hash, salt);
    when(userRepository.save(any(User.class))).thenReturn(user);

    User createdUser = authenticationService.createUserPassword(email, password);

    assertNotNull(createdUser);
    assertEquals(email, createdUser.getEmail());

    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void shoulThrowUserNotFound() {
    String email = "reizkian@mailbox.org";
    String password = "password";
    when(userRepository.findUserByEmail(email)).thenReturn(null);

    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () -> authenticationService.authenticateUser(email, password));

    assertEquals(exception.getMessage(), "User not found with the provided email");
  }

  @Test
  void shouldThrowPasswordMissmatchException() {
    UUID id = UUID.randomUUID();
    String email = "marvin.white@mailbox.org";
    String password = "wrongpassword";
    String salt = authenticationService.generateSalt();
    String hash = passwordEncoder.encode("correctpassword" + salt);

    User user = new User(id, email, hash, salt);
    when(userRepository.findUserByEmail(email)).thenReturn(user);

    PasswordMissmatchException exception = assertThrows(PasswordMissmatchException.class,
        () -> authenticationService.authenticateUser(email, password));

    assertEquals("Password is not match", exception.getMessage());
  }

  @Test
  void shouldAuthenticateUser() {
    UUID id = UUID.randomUUID();
    String email = "marvin.white@mailbox.org";
    String password = "password";
    String salt = authenticationService.generateSalt();
    String hash = passwordEncoder.encode(password + salt);

    User user = new User(id, email, hash, salt);
    when(userRepository.findUserByEmail(email)).thenReturn(user);

    User authenticatedUser = authenticationService.authenticateUser(email, password);

    assertEquals(authenticatedUser.getId(), user.getId());
    assertEquals(authenticatedUser.getEmail(), user.getEmail());
  }
}
