package com.foxminded.appliancesshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.appliancesshop.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
