package com.foxminded.appliancesshop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.appliancesshop.domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

	Optional<Administrator> findByEmail(String email);
}
