package com.foxminded.appliancesshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void test() {
		Customer customer = new Customer();
		customer.setEmail("email");
		customer.setFirstName("John");
		customer.setLastName("Johns");
		customer.setPassword("password");
		customer.setRole(Role.CUSTOMER);
		customer.setStatus(Status.ACTIVE);
		Customer expected = customerRepository.save(customer);
		Customer actual = customerRepository.findByEmail("email").get();
		assertEquals(expected, actual);
	}

}
