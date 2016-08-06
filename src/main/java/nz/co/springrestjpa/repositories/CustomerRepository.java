package nz.co.springrestjpa.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import nz.co.springrestjpa.model.Customer;

/**
 * Spring data jpa repository for customers.
 * 
 * @author jaziel
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Collection<Customer> findByName(String name);
}
