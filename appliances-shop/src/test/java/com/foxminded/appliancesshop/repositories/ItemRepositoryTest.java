package com.foxminded.appliancesshop.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Product;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class ItemRepositoryTest {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void shouldDeleteItemByProductIdButThrowsException() {
		Product product = new Product();
		product.setCategory(categoryRepository.findById(1l).get());
		product.setName("Product");
		Product savedProduct = productRepository.save(product);
		System.out.println("Saved product");
		Item item = new Item();
		item.setProduct(savedProduct);
		Item savedItem = itemRepository.save(item);
		System.out.println("Saved item");
		Customer customer = new Customer();
		customer.setFirstName("John");
		Customer savedCustomer = customerRepository.save(customer);
		System.out.println("Saved customer");
		savedCustomer.addItemToDeferreds(savedItem);
		savedCustomer = customerRepository.save(savedCustomer);
		System.out.println("Added item to customer deferreds");
		savedItem.setCustomer(savedCustomer);
		savedItem = itemRepository.save(savedItem);
		System.out.println("Saved item again");
		itemRepository.deleteItemsByProduct(savedProduct.getId());
		System.out.println("Delete item");
	}

	@Test
	void ShouldDeleteItemByProductId() {
		Product product = new Product();
		product.setCategory(categoryRepository.findById(1l).get());
		product.setName("Product");
		Product savedProduct = productRepository.save(product);
		System.out.println("Saved product");
		Item item = new Item();
		item.setProduct(savedProduct);
		Item savedItem = itemRepository.save(item);
		System.out.println("Saved item");
		itemRepository.deleteItemsByProduct(savedProduct.getId());
		System.out.println("Delete item");
	}

}
