package com.foxminded.appliancesshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foxminded.appliancesshop.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String name);

	@Query(value = "SELECT * FROM category WHERE category.super_category_id = :id", nativeQuery = true)
	List<Category> findSubCategories(@Param("id") Long id);
}
