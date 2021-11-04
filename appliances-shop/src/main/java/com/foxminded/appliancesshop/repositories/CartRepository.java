package com.foxminded.appliancesshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.appliancesshop.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
