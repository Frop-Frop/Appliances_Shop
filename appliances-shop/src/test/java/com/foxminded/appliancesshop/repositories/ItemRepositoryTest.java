package com.foxminded.appliancesshop.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Sql(scripts = { "/sql/delete_all_item.sql", "/sql/sample_data.sql" })
	@Test
	void shouldDeleteAddedToDeferredsItemByProductId() {
		Product product = new Product();
		product.setCategory(categoryRepository.findById(1l).get());
		product.setName("Product");
		product.setPrice(123);
		Product savedProduct = productRepository.save(product);
		Item item = new Item();
		item.setProduct(savedProduct);
		item.setQuantity(5);
		Item savedItem = itemRepository.save(item);
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Connor");
		customer.setEmail("john@connor.dev");
		customer.setPassword("s3cret");
		customer.setRole(Role.CUSTOMER);
		customer.setStatus(Status.ACTIVE);
		Customer savedCustomer = customerRepository.save(customer);
		savedCustomer.addItemToDeferreds(savedItem);
		savedCustomer = customerRepository.save(savedCustomer);
		savedItem.setCustomer(savedCustomer);
		savedItem = itemRepository.save(savedItem);
		itemRepository.deleteItemsByProduct(savedProduct.getId());

	}

	@Sql(scripts = { "/sql/delete_all_item.sql" })
	@Test
	void shouldDeleteItemByProductId() {
		Product product = new Product();
		product.setPrice(123);
		product.setName("Product");
		Product savedProduct = productRepository.save(product);
		Item item = new Item();
		item.setProduct(savedProduct);
		item.setQuantity(2);
		itemRepository.save(item);
		itemRepository.deleteItemsByProduct(savedProduct.getId());
	}

	@Sql(scripts = { "/sql/delete_all_item.sql" })
	@Test
	@Transactional
	void findCustomerDeferredsTest() {
		Product product = new Product();
		product.setName("Product");
		product.setPrice(123);
		Product savedProduct = productRepository.save(product);
		Product product1 = new Product();
		product1.setName("Product1");
		product1.setPrice(1234);
		Product savedProduct1 = productRepository.save(product1);
		Item item = new Item();
		item.setProduct(savedProduct);
		item.setQuantity(5);
		Item savedItem = itemRepository.save(item);
		Item item1 = new Item();
		item1.setProduct(savedProduct1);
		item1.setQuantity(3);
		Item savedItem1 = itemRepository.save(item1);
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Connor");
		customer.setEmail("john@connor.dev");
		customer.setPassword("s3cret");
		customer.setRole(Role.CUSTOMER);
		customer.setStatus(Status.ACTIVE);
		Customer savedCustomer = customerRepository.save(customer);
		savedCustomer.addItemToDeferreds(savedItem);
		savedCustomer.addItemToDeferreds(savedItem1);
		savedCustomer = customerRepository.save(savedCustomer);
		savedItem.setCustomer(savedCustomer);
		savedItem = itemRepository.save(savedItem);
		savedItem1.setCustomer(savedCustomer);
		savedItem1 = itemRepository.save(savedItem1);
		List<Item> expected = Arrays.asList(savedItem, savedItem1);
		List<Item> actual = itemRepository.findDistinctByCustomerId(savedCustomer.getId());
		assertEquals(expected, actual);
	}

}
