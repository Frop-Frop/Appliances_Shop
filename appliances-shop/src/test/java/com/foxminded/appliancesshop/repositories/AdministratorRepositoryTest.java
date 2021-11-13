package com.foxminded.appliancesshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class AdministratorRepositoryTest {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void test() {
		Administrator admin = new Administrator();
		admin.setEmail("email");
		admin.setFirstName("John");
		admin.setLastName("Johns");
		admin.setPassword(passwordEncoder.encode("password"));
		admin.setRole(Role.ADMINISTRATOR);
		admin.setStatus(Status.ACTIVE);
		Administrator expected = administratorRepository.save(admin);
		Administrator actual = administratorRepository.findByEmail("email").get();
		assertEquals(expected, actual);
	}

}
