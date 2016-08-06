package nz.co.springrestjpa.repositories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import nz.co.springrestjpa.model.Customer;

/**
 * Unit tests for CustomerRepository
 * 
 * @author jaziel
 *
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class CustomerRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

  @Autowired
  CustomerRepository repository;

  @Test
  void testSave() {
    Customer customer = new Customer();
    customer.setName("Jhon Nox");
    customer.setAddress("1234 Cross Ave, Michigan");
    customer.setEmail("jhon@nox.com");
    customer.setPhone("+1 (12) 3456789");
    customer = repository.save(customer);
    Assert.assertNotNull(customer);
    Assert.assertNotNull(customer.getId());
  }


  @Test
  void testFindOne() {
    Long id = 1001l;
    Customer customer = repository.findOne(id);
    Assert.assertNotNull(customer);
    Assert.assertEquals(customer.getId(), id);
  }

  @Test
  void testGetAllCustomers() {
    Collection<Customer> customers = repository.findAll();
    Assert.assertNotNull(customers);
    Assert.assertEquals(customers.isEmpty(), false);
    Assert.assertEquals(customers.size(), 1000);
  }

  @Test
  void testUpdateCustomer() {
    Long id = 1001l;
    Customer customer = repository.findOne(id);
    customer.setName("Lenon Brown");
    customer = repository.save(customer);
    Assert.assertEquals(customer.getName(), "Lenon Brown");
  }

  @Test
  void testDeleteCustomer() {
    Long id = 1001l;
    repository.delete(id);
    Assert.assertEquals(repository.exists(id), false);
  }

  @Test
  void testDeleteCustomers() {
    Collection<Customer> customers = repository.findAll();
    Assert.assertEquals(customers.isEmpty(), false);
    repository.deleteAllInBatch();
    customers = repository.findAll();
    Assert.assertEquals(customers.isEmpty(), true);
  }

}
