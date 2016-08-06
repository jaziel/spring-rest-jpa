package nz.co.springrestjpa.dao.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nz.co.springrestjpa.dao.CustomerDao;
import nz.co.springrestjpa.model.Customer;


public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Customer createCustomer(Customer customer) {
		entityManager.persist(customer);
		entityManager.flush();
		
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		return entityManager.merge(customer);
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		String qlString = "SELECT p FROM Customer p";
		TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);		

		return query.getResultList();
	}

	@Override
	public void deleteCustomers() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE Customer");		
		query.executeUpdate();

	}

	@Override
	public Customer deleteCustomer(Long id) {
		Customer customer = entityManager.find(Customer.class, id);
		entityManager.remove(customer);
		return customer;
	}

	@Override
	public Customer getCustomer(Long id) {
		try {
			String qlString = "SELECT p FROM Customer p WHERE p.id = ?1";
			TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
