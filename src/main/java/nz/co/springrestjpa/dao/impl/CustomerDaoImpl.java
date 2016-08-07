package nz.co.springrestjpa.dao.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import nz.co.springrestjpa.dao.CustomerDao;
import nz.co.springrestjpa.model.Customer;


/**
 * CustomerDaoImpl class. Using second level cache to performance/avoid going to underlining database.
 * 
 * @author jaziel
 *
 */
public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@CachePut(cacheNames="getCustomerCache", key="#customer.id")
	public Customer createCustomer(Customer customer) {
		entityManager.persist(customer);
		entityManager.flush();
		return customer;
	}

	@Caching(put = {@CachePut(cacheNames="getCustomerCache", key="#customer.id")}, evict = { @CacheEvict(cacheNames ="getAllCustomersCache", allEntries=true) })
	public Customer updateCustomer(Customer customer) {
		return entityManager.merge(customer);
	}

	@Cacheable(value="getAllCustomersCache")
	public Collection<Customer> getAllCustomers() {
		String qlString = "SELECT p FROM Customer p";
		TypedQuery<Customer> query = entityManager.createQuery(qlString, Customer.class);		

		return query.getResultList();
	}

	@Caching(evict = {@CacheEvict(cacheNames="getCustomerCache", allEntries=true),@CacheEvict(cacheNames ="getAllCustomersCache", allEntries=true) })
	public void deleteCustomers() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE Customer");		
		query.executeUpdate();

	}

	@Caching(evict = {@CacheEvict(cacheNames="getCustomerCache", key="#id"),@CacheEvict(cacheNames ="getAllCustomersCache", allEntries=true) })
	public Customer deleteCustomer(Long id) {
		Customer customer = entityManager.find(Customer.class, id);
		entityManager.remove(customer);
		return customer;
	}

	@Cacheable(value="getCustomerCache", key="#id")
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
