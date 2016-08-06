package nz.co.springrestjpa.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nz.co.springrestjpa.model.Customer;
import nz.co.springrestjpa.repositories.CustomerRepository;

@RestController
@RequestMapping(value = "/v2")
public class CustomerRepositoryRestController {

  @Autowired
  private CustomerRepository repository; 
  private String phoneNumberExpression = "^\\+[1-9]{1}[0-9]{3,14}$";
  private String nameExpression = "^[\\p{L} .'-]+$";

  @RequestMapping(value = "/customers", method = RequestMethod.GET)
  public Collection<Customer> getCustomers() {
    return repository.findAll();
  }

  @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
  public Customer getCustomer(@PathVariable Long id) {
    return repository.findOne(id);
  }

  @Transactional
  @RequestMapping(value = "/customers", method = RequestMethod.POST)
  public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws Exception {
    if (customer.getName().isEmpty()) {
      throw new Exception("Customer name is empty!");
    } else {
      if (!customer.getName().matches(nameExpression)) {
        throw new Exception("Customer name is invalid!");
      }
    }
    if (customer.getPhone().isEmpty()) {
      throw new Exception("Customer telephone is empty!");
    } else {
      if (!customer.getPhone().matches(phoneNumberExpression)) {
        throw new Exception("Customer telephone is invalid!");
      }
    }
    customer = repository.save(customer);
    return new ResponseEntity<Customer>(customer, HttpStatus.OK);
  }

  @Transactional
  @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
    ResponseEntity<Customer> response;
    Customer customer = repository.getOne(id);
    repository.delete(id);
    if (customer == null) {
      response = new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
    } else {
      response = new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
    return response;
  }
  
  @Transactional
  @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) throws Exception {
    customer = repository.save(customer);
    return new ResponseEntity<Customer>(customer, HttpStatus.OK);
  }
}
