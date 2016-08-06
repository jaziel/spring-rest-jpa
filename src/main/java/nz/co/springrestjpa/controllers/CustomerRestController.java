package nz.co.springrestjpa.controllers;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nz.co.springrestjpa.dao.CustomerDao;
import nz.co.springrestjpa.model.Customer;

@RestController
public class CustomerRestController {

  @Autowired
  private CustomerDao customerDao;
  private String phoneNumberExpression = "^\\+[1-9]{1}[0-9]{3,14}$";
  private String nameExpression = "^[\\p{L} .'-]+$";

  @RequestMapping(value = "/customers", method = RequestMethod.GET)
  public Collection<Customer> getCustomers() {
    return customerDao.getAllCustomers();
  }

  @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
  public Customer getCustomer(@PathVariable Long id) {
    return customerDao.getCustomer(id);
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
    customer = customerDao.createCustomer(customer);
    return new ResponseEntity<Customer>(customer, HttpStatus.OK);
  }

  @Transactional
  @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
    ResponseEntity<Customer> response;
    Customer customer = customerDao.deleteCustomer(id);
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
    customer = customerDao.updateCustomer(customer);
    return new ResponseEntity<Customer>(customer, HttpStatus.OK);
  }
}
