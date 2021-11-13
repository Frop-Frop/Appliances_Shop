package com.foxminded.appliancesshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void test() {
		Customer customer = new Customer();
		customer.setEmail("email");
		customer.setFirstName("John");
		customer.setLastName("Johns");
		customer.setPassword(passwordEncoder.encode("password"));
		customer.setRole(Role.CUSTOMER);
		customer.setStatus(Status.ACTIVE);
		Customer expected = customerRepository.save(customer);
		Customer actual = customerRepository.findByEmail("email").get();
		assertEquals(expected, actual);
	}

}
