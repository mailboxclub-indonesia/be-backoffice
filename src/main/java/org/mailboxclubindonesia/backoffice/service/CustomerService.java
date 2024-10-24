package org.mailboxclubindonesia.backoffice.service;

import org.mailboxclubindonesia.backoffice.model.Customer;
import org.mailboxclubindonesia.backoffice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer saveCustomer(Customer customer) {
    return this.customerRepository.save(customer);
  }
}
