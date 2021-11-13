package com.foxminded.appliancesshop.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.security.SecurityUser;
import com.foxminded.appliancesshop.repositories.AdministratorRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AdministratorRepository administratorRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Administrator> optionalAdmin = administratorRepository.findByEmail(email);
		if (optionalAdmin.isPresent()) {
			Administrator administrator = optionalAdmin.get();
			return SecurityUser.fromAdministrator(administrator);
		}

		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(() -> new ResourseNotFoundException("User doesn't exists"));
		return SecurityUser.fromCustomer(customer);

	}

}
