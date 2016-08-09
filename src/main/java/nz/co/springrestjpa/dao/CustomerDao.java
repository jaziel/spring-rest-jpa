package nz.co.springrestjpa.dao;

import java.util.Collection;

import nz.co.springrestjpa.model.Customer;


public interface CustomerDao {
	
	Customer createCustomer(Customer customer);
	
	Customer updateCustomer(Customer customer);
	
	Collection<Customer> getAllCustomers();
	
	void deleteCustomers();
	
	Customer deleteCustomer(Long id); 
	
	Customer getCustomer(Long id);

}
