package com.foxminded.appliancesshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.appliancesshop.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
