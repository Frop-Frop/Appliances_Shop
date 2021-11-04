package com.foxminded.appliancesshop.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.repositories.AddressRepository;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CategoryRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.repositories.ItemRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

//@Component
public class DataGeneration /* implements CommandLineRunner */ {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ProductRepository productRepository;

	// @Override
	public void run(String... args) throws Exception {

		Cart cart = new Cart();
		Cart savedCart = cartRepository.save(cart);

		Address address = new Address();
		address.setCountry("Kazahia");
		address.setRegion("Karagandan");
		address.setCity("Nursultan");
		address.setStreet("Kakayato");
		address.setHouseNumber("4/5");
		Address savedAddress = addressRepository.save(address);

		Customer customer = new Customer();
		customer.setFirstName("John Johns");
		customer.setLastName("Johns");
		customer.setPassword("password");
		customer.setEmail("email@mail.com");
		customer.setAddress(savedAddress);
		customer.setCart(savedCart);
		Customer savedCustomer = customerRepository.save(customer);

		savedAddress.setCustomer(savedCustomer);
		savedAddress = addressRepository.save(savedAddress);

		savedCart.setCustomer(savedCustomer);
		savedCart = cartRepository.save(savedCart);

		savedCustomer.setAddress(savedAddress);
		savedCustomer.setCart(savedCart);

		savedCustomer = customerRepository.save(savedCustomer);

		Product product = new Product();
		product.setName("Drill");
		product.setDescription("My drill was made to pierce the heaven!");
		Product savedProduct = productRepository.save(product);

		Product product1 = new Product();
		product1.setName("Drill1");
		product1.setDescription("My drill also was made to pierce the heaven!");
		Product savedProduct1 = productRepository.save(product1);

		Category superCategory = new Category();
		superCategory.setName("Building tools");
		Category savedSupCategory = categoryRepository.save(superCategory);

		Category category = new Category();
		category.setName("Drills");
		category.getProducts().add(savedProduct);
		category.getProducts().add(savedProduct1);
		category.setSuperCategory(savedSupCategory);
		Category savedCategory = categoryRepository.save(category);

		savedProduct.setCategory(savedCategory);
		savedProduct = productRepository.save(savedProduct);

		savedProduct1.setCategory(savedCategory);
		savedProduct1 = productRepository.save(savedProduct1);

		Item item = new Item();
		item.setProduct(savedProduct);
		item.setQuantity(3);
		Item savedItem = itemRepository.save(item);

		Item item1 = new Item();
		item1.setProduct(savedProduct1);
		item1.setQuantity(5);
		Item savedItem1 = itemRepository.save(item1);

		savedItem.setCart(savedCart);
		savedItem1.setCart(savedCart);
		savedItem = itemRepository.save(savedItem);
		savedItem1 = itemRepository.save(savedItem1);

		savedCart.getItems().add(savedItem);
		savedCart.getItems().add(savedItem1);
		savedCart = cartRepository.save(savedCart);

		Item item2 = new Item();
		item2.setProduct(savedProduct1);
		item2.setQuantity(5);
		Item savedItem2 = itemRepository.save(item2);

		savedCustomer.addItemToDeferreds(savedItem2);
		savedCustomer = customerRepository.save(savedCustomer);

		savedItem2.setCustomer(savedCustomer);
		savedItem2 = itemRepository.save(savedItem2);

		Category fetchedCategory = categoryRepository.findById(2L).get();
		System.out.println("Fetched category: " + fetchedCategory.getName());
		System.out.println("Fetched super category: " + fetchedCategory.getSuperCategory().getName());
		fetchedCategory.getProducts().iterator()
				.forEachRemaining(p -> System.out.println(p.getName() + " : " + p.getDescription()));

		Customer fetchedCustomer = customerRepository.findById(1L).get();
		Address fetchedAddress = fetchedCustomer.getAddress();
		System.out.println(fetchedAddress.getCountry() + " " + fetchedAddress.getRegion() + " "
				+ fetchedAddress.getCity() + " " + fetchedAddress.getStreet());

		System.out.println(fetchedCustomer.getDeferreds().size() + " - deferreds size");

		fetchedCustomer.getDeferreds().iterator().forEachRemaining(
				d -> System.out.println("Deferred: " + d.getProduct().getName() + " " + d.getQuantity()));

		System.out.println("////////////////");

		Product cup = new Product();
		cup.setName("Cup");
		cup.setDescription("It can contain liquid");
		cup.setPrice(20);
		cup.setCategory(savedSupCategory);
		Product savedCup = productRepository.save(cup);

		System.out.println("Cup saved");

		Item itemCup = new Item();
		itemCup.setProduct(savedCup);
		itemCup.setQuantity(10);
		Item savedItemCup = itemRepository.save(itemCup);

		System.out.println("Cup item saved, id: " + savedItemCup.getId());

		Product cup1 = new Product();
		cup1.setName("Cup1");
		cup1.setDescription("It can contain liquid as well");
		cup1.setPrice(10);
		cup1.setCategory(savedSupCategory);
		Product savedCup1 = productRepository.save(cup1);

		System.out.println("Cup1 saved");

		Item itemCup1 = new Item();
		itemCup1.setProduct(savedCup1);
		itemCup1.setQuantity(15);
		Item savedItemCup1 = itemRepository.save(itemCup1);

		System.out.println("Cup1 item saved, id: " + savedItemCup1.getId());

		fetchedCustomer.addItemToDeferreds(savedItemCup);
		fetchedCustomer.addItemToDeferreds(savedItemCup1);

		fetchedCustomer = customerRepository.save(fetchedCustomer);

		System.out.println("Fetched customer saved");

		savedItemCup.setCustomer(fetchedCustomer);
		savedItemCup1.setCustomer(fetchedCustomer);

		savedItemCup = itemRepository.save(savedItemCup);
		savedItemCup = itemRepository.save(savedItemCup1);

		System.out.println("Items saved once again");

		fetchedCustomer.getDeferreds().stream()
				.forEach(d -> System.out.println(d.getProduct().getName() + " quantity: " + d.getQuantity()));
	}

}
