package com.foxminded.appliancesshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;

@DataJpaTest
class AdministratorRepositoryTest {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Test
	void test() {
		Administrator admin = new Administrator();
		admin.setEmail("email");
		admin.setFirstName("John");
		admin.setLastName("Johns");
		admin.setPassword("password");
		admin.setRole(Role.ADMINISTRATOR);
		admin.setStatus(Status.ACTIVE);
		Administrator expected = administratorRepository.save(admin);
		Administrator actual = administratorRepository.findByEmail("email").get();
		assertEquals(expected, actual);
	}

}
