package com.foxminded.appliancesshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foxminded.appliancesshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByName(String name);

	@Query(value = "SELECT * FROM product WHERE product.category_id = :id", nativeQuery = true)
	List<Product> findAllProductsInCategory(@Param("id") Long id);

	@Query(value = "SELECT * FROM product WHERE product.brand = :brand", nativeQuery = true)
	List<Product> findAllProductsByBrand(@Param("brand") String brand);
}
