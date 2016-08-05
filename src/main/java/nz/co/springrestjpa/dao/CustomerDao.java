package nz.co.springrestjpa.dao;

import java.util.Collection;

import nz.co.springrestjpa.model.Customer;


public interface CustomerDao {
	
	public Customer createCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer);
	
	public Collection<Customer> getAllCustomer();
	
	public void deleteCustomers();
	
	public Customer deleteCustomer(Integer id); 
	
	public Customer getCustomer(Integer id);

}
