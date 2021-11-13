package com.foxminded.appliancesshop.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.SecurityUser;
import com.foxminded.appliancesshop.domain.security.Status;
import com.foxminded.appliancesshop.repositories.AdministratorRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;

@SpringBootTest(classes = { UserService.class })
class UserServiceTest {

	@MockBean
	private AdministratorRepository administratorRepository;

	@MockBean
	private CustomerRepository customerRepository;

	@Autowired
	private UserService userService;

	@Test
	void shouldReturnAdminUser() {
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		administrator.setEmail("email");
		administrator.setFirstName("name");
		administrator.setLastName("lastname");
		administrator.setPassword("password");
		administrator.setRole(Role.ADMINISTRATOR);
		administrator.setStatus(Status.ACTIVE);
		when(administratorRepository.findByEmail("email")).thenReturn(Optional.of(administrator));
		UserDetails actual = userService.loadUserByUsername("email");
		UserDetails expected = SecurityUser.fromAdministrator(administrator);
		verify(administratorRepository, times(1)).findByEmail("email");
		verify(customerRepository, never()).findByEmail("email");
		assertEquals(expected, actual);
	}

	@Test
	void shouldReturnCustomerUser() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setEmail("email");
		customer.setFirstName("name");
		customer.setLastName("lastname");
		customer.setPassword("password");
		customer.setRole(Role.CUSTOMER);
		customer.setStatus(Status.ACTIVE);
		when(customerRepository.findByEmail("email")).thenReturn(Optional.of(customer));
		when(administratorRepository.findByEmail("email")).thenReturn(Optional.empty());
		UserDetails actual = userService.loadUserByUsername("email");
		verify(customerRepository, times(1)).findByEmail("email");
		verify(administratorRepository, times(1)).findByEmail("email");
		UserDetails expected = SecurityUser.fromCustomer(customer);
		assertEquals(expected, actual);
	}

	@Test
	void shouldReturnResourseNotFoundException() {
		when(administratorRepository.findByEmail("email")).thenReturn(Optional.empty());
		when(customerRepository.findByEmail("email")).thenReturn(Optional.empty());
		Throwable thrown = catchThrowable(() -> {
			userService.loadUserByUsername("email");
		});
		assertThat(thrown).isInstanceOf(ResourseNotFoundException.class);
		verify(customerRepository, times(1)).findByEmail("email");
		verify(administratorRepository, times(1)).findByEmail("email");
	}

}
