package feature.customer;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nz.co.springrestjpa.controllers.CustomerRestController;
import nz.co.springrestjpa.model.Customer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class CRUDCustomerSteps {
  
  @Autowired
  CustomerRestController controller;
  Customer customer;
  
  @Given(".+customer with the name '(.+)', address '(.+)' and phone '(.+)'")
  public void createCustomer(final String name, final String address, final String phone) throws Exception{
    Customer customer = new Customer();
    customer.setName(name);
    customer.setAddress(address);
    customer.setPhone(phone);
    controller.createCustomer(customer);
  }
  
  @When("^the user searches for a customer with id (\\d+)$")
  @Given(".+the user searches for a customer with id (\\d+)")
  public void findCustomer(final long id) {
    customer = controller.getCustomer(id);
  }
  
  @Then("customer should have the name '(.+)'$")
  public void verifyCustomerName(final String name) {
    assertThat(customer.getName(), equalTo(name));
  }
  
  @Then("customer should have the address '(.+)'$")
  public void verifyCustomerAddress(final String address) {
      assertThat(customer.getAddress(), equalTo(address));
  }
  
  @When("^the user updates the customer's name to '(.+)'$")
  public void updateCustomerName(final String name) throws Exception {
    customer.setName(name);
    controller.updateCustomer(customer.getId(), customer);
  }
  
  @Then("the customer with id (\\d+) should have the name '(.+)'$")
  public void verifyCustomerName(final long id, final String name) {
    Customer customer = controller.getCustomer(id);
    assertThat(customer.getName(), equalTo(name));
  }
  
  @When("^the user deletes the customer with id (\\d+)$")
  public void deleteCustomer(final long id) throws Exception {
    controller.deleteCustomer(id);
  }
  
  @Then("the user cannot find a user with id (\\d+)$")
  public void findDeletedCustomer(final long id) {
    Customer customer = controller.getCustomer(id);
    assertThat(customer, equalTo(null));
  }

}
