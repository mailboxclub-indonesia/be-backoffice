package org.mailboxclubindonesia.backoffice.controller;

import org.mailboxclubindonesia.backoffice.model.Customer;
import org.mailboxclubindonesia.backoffice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping()
  ResponseEntity<Customer> postCustomer(@Valid @RequestBody Customer customer) {
    try {
      Customer newCustomer = customerService.saveCustomer(customer);
      return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to create new user: " + exception.getMessage(), exception);
    }
  }
}
