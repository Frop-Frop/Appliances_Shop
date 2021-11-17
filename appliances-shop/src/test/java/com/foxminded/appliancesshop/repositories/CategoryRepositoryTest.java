package com.foxminded.appliancesshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.foxminded.appliancesshop.domain.Category;

@DataJpaTest
class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void findByNameTest() {
		Category category = new Category();
		category.setName("Super category");
		Category savedSuperCategory = categoryRepository.save(category);
		Category subCategory = new Category();
		subCategory.setName("sub category1");
		subCategory.setSuperCategory(savedSuperCategory);
		Category savedSubCategory = categoryRepository.save(subCategory);
		Category subCategory1 = new Category();
		subCategory1.setName("sub category2");
		subCategory1.setSuperCategory(savedSuperCategory);
		Category savedSubCategory1 = categoryRepository.save(subCategory1);
		List<Category> expected = Arrays.asList(savedSubCategory, savedSubCategory1);
		List<Category> actual = categoryRepository.findSubCategories(savedSuperCategory.getId());
		assertEquals(expected, actual);
	}

}
