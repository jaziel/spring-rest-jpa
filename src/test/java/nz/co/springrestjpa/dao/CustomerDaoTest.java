package nz.co.springrestjpa.dao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import nz.co.springrestjpa.model.Customer;


@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class CustomerDaoTest extends AbstractTransactionalTestNGSpringContextTests {

  @Autowired
  CustomerDao customerDao;
  
  @Test
  @Rollback(false)
  void testCreateCustomer() {
    Customer customer = new Customer();
    customer.setName("Jhon Nox");
    customer.setAddress("1234 Cross Ave, Michigan");
    customer.setEmail("jhon@nox.com");
    customer.setPhone("+1 (12) 3456789");
    customer = customerDao.createCustomer(customer);
    Assert.assertNotNull(customer);
    Assert.assertNotNull(customer.getId());
  }
  
  
  @Test(dependsOnMethods={ "testCreateCustomer" })
  void testGetCustomer() {
    Long id = 1l;
    Customer customer = customerDao.getCustomer(1l);
    Assert.assertNotNull(customer);
    Assert.assertEquals(customer.getId(), id);
  }
  
  @Test(dependsOnMethods={ "testGetCustomer" })
  void testGetAllCustomers() {
    Collection<Customer> customers = customerDao.getAllCustomers();
    Assert.assertNotNull(customers);
    Assert.assertEquals(customers.isEmpty(), false);
    Assert.assertEquals(customers.size(), 1);
  }
  
  @Test(dependsOnMethods={ "testGetAllCustomers" })
  void testUpdateCustomer() {
    Long id = 1l;
    Customer customer = customerDao.getCustomer(1l);
    customer.setName("Lenon Brown");
    customer = customerDao.updateCustomer(customer);
    Assert.assertEquals(customer.getName(), "Lenon Brown");
  }
  
  @Test(dependsOnMethods={ "testUpdateCustomer" })
  void testDeleteCustomer() {
    Long id = 1l;
    Customer customer = customerDao.deleteCustomer(id);
    Assert.assertEquals(customer.getId(), id);
    customer = customerDao.getCustomer(id);
    Assert.assertNull(customer);
  }
  
  @Test(dependsOnMethods={ "testDeleteCustomer"})
  void testDeleteCustomers() {
    Customer customer = new Customer();
    customer.setName("Elliot Grey");
    customer.setAddress("4321 Connection Road, New York");
    customer.setEmail("grey@elliot.com");
    customer.setPhone("+1 (21) 66554431");
    customer = customerDao.createCustomer(customer);
    customerDao.deleteCustomers();
    Collection<Customer> customers = customerDao.getAllCustomers();
    Assert.assertEquals(customers.isEmpty(), true);
  }

}
