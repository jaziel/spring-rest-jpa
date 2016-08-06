package nz.co.springrestjpa.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import nz.co.springrestjpa.dao.CustomerDao;
import nz.co.springrestjpa.model.Customer;

/**
 * Unit tests for CustomerRestController
 * 
 * @author jaziel
 *
 */
@Test
public class CustomerRestControllerTest {

  @InjectMocks
  private CustomerRestController controller;

  @Mock
  CustomerDao dao;

  @Spy
  List<Customer> customers = new ArrayList<Customer>();
  
  @Spy
  Customer customer;

  @Spy
  ModelMap model;

  @Mock
  BindingResult result;

  @BeforeClass
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    
    customer = new Customer();
    customer.setName("Jhon Nox");
    customer.setAddress("1234 Cross Ave, Michigan");
    customer.setEmail("jhon@nox.com");
    customer.setPhone("+1 (12) 3456789");
    customers.add(customer);

    Customer customer2 = new Customer();
    customer2.setName("Arlindo Geni");
    customer2.setAddress("132 Lincon Ave, New York");
    customer2.setEmail("geni@arlindo.com");
    customer2.setPhone("+64 (22) 5543321");
    customers.add(customer2);
    
  }


  @Test
  void testgetCustomers() {
    when(dao.getAllCustomers()).thenReturn(customers);
    Assert.assertEquals(controller.getCustomers(), customers);
  }
  
  @Test
  void testGetCustomer() {
    when(dao.getCustomer(anyLong())).thenReturn(customer);
    Assert.assertEquals(controller.getCustomer(1l), customers.get(0));
  }
  
  @Test
  void testCreateCustomer() throws Exception {
    Customer customer = new Customer();
    customer.setName("Test ");
    customer.setAddress("Test address");
    customer.setPhone("+123456");
    customer.setEmail("test@test.com");
    
    when(dao.createCustomer(any(Customer.class))).thenReturn(customer);
    ResponseEntity<Customer> response = controller.createCustomer(customer);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody(), customer);
  }
  
  @Test(expectedExceptions = {Exception.class})
  void testCreateCustomerEmptyName() throws Exception {
    Customer customer = new Customer();
    customer.setName("");
    customer.setAddress("Test address");
    customer.setPhone("+123456");
    customer.setEmail("test@test.com");
    controller.createCustomer(customer);
  }
  
  @Test(expectedExceptions = {Exception.class})
  void testCreateCustomerInvalidPhone() throws Exception {
    Customer customer = new Customer();
    customer.setName("test");
    customer.setAddress("test");
    customer.setPhone("invalidphone");
    customer.setEmail("test@test.com");
    controller.createCustomer(customer);
  }
  
  @Test
  void testDeleteCustomer() {
    when(dao.deleteCustomer(anyLong())).thenReturn(customer);
    ResponseEntity<Customer> response = controller.deleteCustomer(1l);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(response.getBody(), customer);
  }
  
  @Test
  void testDeleteCustomerBadRequest() {
    Long id = 1l;
    when(dao.deleteCustomer(id)).thenReturn(null);
    ResponseEntity<Customer> response = controller.deleteCustomer(id);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    Assert.assertNull(response.getBody());
  }
  
  @Test
  void testUpdateCustomer() throws Exception {
    when(dao.getCustomer(anyLong())).thenReturn(customer);
    Customer customer = controller.getCustomer(1l);
    customer.setName("Update");
    when(dao.updateCustomer(customer)).thenReturn(customer);
    ResponseEntity<Customer> response = controller.updateCustomer(customer.getId(), customer);
    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(customer.getName(), response.getBody().getName());
  }

}
