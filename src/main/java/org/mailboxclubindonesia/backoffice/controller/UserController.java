package org.mailboxclubindonesia.backoffice.controller;

import java.util.Optional;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.User;
import org.mailboxclubindonesia.backoffice.model.UserAddress;
import org.mailboxclubindonesia.backoffice.model.UserDetail;
import org.mailboxclubindonesia.backoffice.service.EmailService;
import org.mailboxclubindonesia.backoffice.service.GoogleMapService;
import org.mailboxclubindonesia.backoffice.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final EmailService emailService;
  private final GoogleMapService gmapService;

  public UserController(UserService userService, EmailService emailService, GoogleMapService gmapService) {
    this.userService = userService;
    this.emailService = emailService;
    this.gmapService = gmapService;
  }

  @PostMapping("/detail")
  ResponseEntity<UserDetail> postUserDetail(@Valid @RequestBody UserDetail userDetail) {
    try {
      UserDetail newUserDetail = userService.saveUserDetail(userDetail);
      Optional<User> user = userService.findUserById(newUserDetail.getUserId());

      if (user.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to create user detail, user id is not exists");
      }

      this.emailService.sendEmailAuthRegister(user.get().getEmail(), newUserDetail);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUserDetail);
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to create new user: " + exception.getMessage(), exception);
    } catch (MessagingException exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send registration email");
    }
  }

  @GetMapping("/detail/{id}")
  ResponseEntity<UserDetail> getUserDetail(@PathVariable String id, @RequestAttribute("userId") UUID userId) {
    try {
      Optional<UserDetail> userDetail = userService.findUserDetailById(UUID.fromString(id));

      if (userDetail.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User detail not found");
      }

      return ResponseEntity.status(HttpStatus.OK).body(userDetail.get());
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to get user detail: " + exception.getMessage(), exception);
    }
  }

  @PostMapping("/address")
  ResponseEntity<UserAddress> postUserAddress(@Valid @RequestBody UserAddress userAddress) {
    try {
      UserAddress userAddressWithGeometry = gmapService.setUserAddressGeometry(userAddress);
      userService.saveUserAddress(userAddressWithGeometry);
      return ResponseEntity.status(HttpStatus.CREATED).body(userAddressWithGeometry);
    } catch (DataIntegrityViolationException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Failed to create new user: " + exception.getMessage(), exception);

    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to create new user: " + exception.getMessage(), exception);
    }
  }

  @GetMapping("/address/{id}")
  ResponseEntity<UserAddress> getUserAddress(@PathVariable String id) {
    try {
      Optional<UserAddress> userAddress = userService.findUserAddressById(UUID.fromString(id));

      if (userAddress.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User detail not found");
      }

      return ResponseEntity.status(HttpStatus.OK).body(userAddress.get());
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to get user detail: " + exception.getMessage(), exception);
    }
  }
}
