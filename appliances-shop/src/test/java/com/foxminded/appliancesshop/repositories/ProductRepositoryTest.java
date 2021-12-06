package com.foxminded.appliancesshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Test
	void findAllProductsByBrandTest() {
		Category category = new Category();
		category.setName("Category");
		Category savedCategory = categoryRepository.save(category);
		Product product = new Product();
		product.setName("product");
		product.setBrand("brand");
		product.setPrice(123);
		product.setCategory(savedCategory);
		Product savedProduct = productRepository.save(product);
		Product product1 = new Product();
		product1.setName("product1");
		product1.setBrand("brand");
		product1.setPrice(1234);
		product1.setCategory(savedCategory);
		Product savedProduct1 = productRepository.save(product1);
		List<Product> expected = Arrays.asList(savedProduct, savedProduct1);
		List<Product> actual = productRepository.findAllProductsByBrand("brand");
		assertEquals(expected, actual);
	}

	void findByNameTest() {
		Product product = new Product();
		product.setName("product");
		product.setBrand("brand");
		product.setPrice(123);
		product.setCategory(categoryRepository.getById(1l));
		Product expected = productRepository.save(product);
		Product actual = productRepository.findByName("product").get();
		assertEquals(expected, actual);
	}

	void findAllProductsInCategoryTest() {
		Category category = new Category();
		category.setName("category");
		Category savedCategory = categoryRepository.save(category);
		Product product = new Product();
		product.setName("product");
		product.setBrand("brand1");
		product.setPrice(123);
		product.setCategory(savedCategory);
		Product savedProduct = productRepository.save(product);
		Product product1 = new Product();
		product1.setName("product1");
		product1.setBrand("brand2");
		product1.setPrice(1234);
		product1.setCategory(savedCategory);
		Product savedProduct1 = productRepository.save(product1);
		savedCategory.addProductToCategory(savedProduct);
		savedCategory.addProductToCategory(savedProduct1);
		savedCategory = categoryRepository.save(savedCategory);
		List<Product> expected = Arrays.asList(savedProduct, savedProduct1);
		List<Product> actual = productRepository.findAllProductsInCategory(savedCategory.getId());
		assertEquals(expected, actual);
	}

}
