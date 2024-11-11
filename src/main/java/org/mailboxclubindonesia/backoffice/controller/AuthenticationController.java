package org.mailboxclubindonesia.backoffice.controller;

import java.util.NoSuchElementException;

import org.mailboxclubindonesia.backoffice.dto.AuthenticationDto.LoginRequest;
import org.mailboxclubindonesia.backoffice.dto.AuthenticationDto.LoginResponse;
import org.mailboxclubindonesia.backoffice.dto.AuthenticationDto.UserRegisterRequest;
import org.mailboxclubindonesia.backoffice.dto.AuthenticationDto.UserRegisterResponse;
import org.mailboxclubindonesia.backoffice.exception.PasswordMissmatchException;
import org.mailboxclubindonesia.backoffice.model.User;
import org.mailboxclubindonesia.backoffice.service.AuthenticationService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request) {
    boolean isConfirmPasswordMatch = request.getConfirmPassword().equals(request.getPassword());

    if (!isConfirmPasswordMatch)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Confirmation password do not match");
    try {
      User userWithHashedPassword = authenticationService.createUserPassword(request.getEmail(), request.getPassword());
      UserRegisterResponse response = new UserRegisterResponse(userWithHashedPassword);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (DataIntegrityViolationException exception) {
      System.out.println("DataIntegrityViolationException catch");
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already registered");
    }
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws ResponseStatusException {
    try {
      User user = authenticationService.authenticateUser(request.getEmail(), request.getPassword());
      String token = authenticationService.generateTokenFromUser(user);
      LoginResponse response = new LoginResponse(user, token);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (PasswordMissmatchException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is invalid");
    } catch (NoSuchElementException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is invalid");
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to authenticate user");
    }
  }
}
