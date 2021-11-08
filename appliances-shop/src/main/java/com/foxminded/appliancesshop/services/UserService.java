package com.foxminded.appliancesshop.services;

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

@Service("userDetailsServiceImpl")
public class UserService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AdministratorRepository administratorRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (administratorRepository.findByEmail(email).isPresent()) {
			Administrator administrator = administratorRepository.findByEmail(email).get();
			return SecurityUser.fromAdministrator(administrator);
		}

		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(() -> new ResourseNotFoundException("User doesn't exists"));
		return SecurityUser.fromStudent(customer);

	}

}
