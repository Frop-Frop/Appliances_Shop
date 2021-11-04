package com.foxminded.appliancesshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.appliancesshop.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
