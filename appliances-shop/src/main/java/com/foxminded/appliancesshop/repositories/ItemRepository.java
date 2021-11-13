package com.foxminded.appliancesshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.appliancesshop.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	@Query(value = "SELECT * FROM item WHERE customer_id = :id", nativeQuery = true)
	List<Item> findCustomerDeferreds(@Param("id") Long id);

	@Query(value = "SELECT * FROM item WHERE cart_id = :id", nativeQuery = true)
	List<Item> findAllItemsInCart(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM item WHERE item.product_id = :id", nativeQuery = true)
	void deleteItemsByProduct(@Param("id") Long id);

}
