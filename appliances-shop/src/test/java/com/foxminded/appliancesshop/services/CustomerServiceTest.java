package com.foxminded.appliancesshop.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.mappers.AddressMapper;
import com.foxminded.appliancesshop.mappers.CartMapper;
import com.foxminded.appliancesshop.mappers.CustomerMapper;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.model.AddressDTO;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.model.CustomerDTO;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;

@SpringBootTest(classes = { CustomerService.class, CartService.class, CustomerMapper.class, CartMapper.class,
		ItemMapper.class, AddressMapper.class })
class CustomerServiceTest {

	@MockBean
	private CustomerRepository customerRepository;

	@MockBean
	private CartRepository cartRepository;

	@MockBean
	private CustomerMapper customerMapper;

	@MockBean
	private CartMapper cartMapper;

	@MockBean
	private ItemMapper itemMapper;

	@MockBean
	private AddressMapper addressMapper;

	@MockBean
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@Test
	void getAllCustomersTest() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("John");
		Customer customer1 = new Customer();
		customer1.setId(2L);
		customer1.setFirstName("Jane");
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setFirstName("John");
		CustomerDTO customerDTO1 = new CustomerDTO();
		customerDTO1.setId(2L);
		customerDTO1.setFirstName("Jane");
		when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDTO);
		when(customerMapper.customerToCustomerDTO(customer1)).thenReturn(customerDTO1);
		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer, customer1));
		customerService.getAllCustomers();
		verify(customerRepository, times(1)).findAll();
		verify(customerMapper, times(1)).customerToCustomerDTO(customer);
		verify(customerMapper, times(1)).customerToCustomerDTO(customer1);
	}

	@Test
	void getCustomerByIdTest() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("John");
		Optional<Customer> optionalCustomer = Optional.of(customer);
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setFirstName("John");
		when(customerRepository.findById(1L)).thenReturn(optionalCustomer);
		when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDTO);
		customerService.getCustomerById(1L);
		verify(customerRepository, times(1)).findById(1L);
	}

	@Test
	void getCustomerObjectByIdTest() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("John");
		Optional<Customer> optionalCustomer = Optional.of(customer);
		when(customerRepository.findById(1L)).thenReturn(optionalCustomer);
		customerService.getCustomerOjectById(1L);
		verify(customerRepository, times(1)).findById(1L);
	}

	@Test
	void createNewCustomerTest() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("John");
		Customer customerWithoutId = new Customer();
		customerWithoutId.setFirstName("John");
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("John");
		when(customerMapper.customerDTOtoCustomer(customerDTO)).thenReturn(customerWithoutId);
		when(customerRepository.save(customerWithoutId)).thenReturn(customer);
		CustomerDTO customerDTO1 = new CustomerDTO();
		customerDTO1.setId(1L);
		customerDTO1.setFirstName("John");
		when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDTO1);
		customerService.createNewCustomer(customerDTO);
		verify(customerRepository, times(1)).save(customerWithoutId);
		verify(customerMapper, times(1)).customerDTOtoCustomer(customerDTO);
		verify(customerMapper, times(1)).customerToCustomerDTO(customer);
	}

	@Test
	void saveCustomerByDTOTest() {
		Cart cart = new Cart();
		cart.setId(1L);
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("John");
		customer.setCart(cart);
		Optional<Customer> optionalCustomer = Optional.of(customer);
		CartDTO cartDTO = new CartDTO();
		cartDTO.setId(1L);
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("John");
		customerDTO.setCart(cartDTO);
		CustomerDTO customerDTO1 = new CustomerDTO();
		customerDTO1.setFirstName("John");
		customerDTO1.setId(1l);
		Customer customerWithoutId = new Customer();
		customerWithoutId.setFirstName("John");
		when(customerRepository.findById(1L)).thenReturn(optionalCustomer);
		when(customerMapper.customerDTOtoCustomer(customerDTO)).thenReturn(customerWithoutId);
		when(customerRepository.save(customerWithoutId)).thenReturn(customer);
		when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDTO1);
		customerService.saveCustomerByDTO(1L, customerDTO);
		verify(customerRepository, times(1)).findById(1L);
		verify(customerRepository, times(1)).save(customerWithoutId);
		verify(customerMapper, times(1)).customerDTOtoCustomer(customerDTO);
		verify(customerMapper, times(1)).customerToCustomerDTO(customer);
	}

	@Test
	void patchCustomer() {
		Cart cart = new Cart();
		cart.setId(1l);
		Address address = new Address();
		address.setCountry("Country");
		address.setId(1L);
		Product product1 = new Product(1L, "Product", null, 123, "Brand", "Description", 100);
		Product product2 = new Product(2L, "Product 1", null, 1234, "Brand", "Description1", 100);
		Item item1 = new Item();
		item1.setProduct(product1);
		item1.setId(1L);
		Item item2 = new Item();
		item2.setProduct(product2);
		item2.setId(2L);
		Customer customer = new Customer();
		Customer customerWithId = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Johns");
		customer.setEmail("email");
		customer.setPassword("password");
		customer.setCart(cart);
		customer.setDeferreds(Set.of(item1, item2));
		customer.setAddress(address);
		customer.setId(1L);
		when(customerRepository.getById(1L)).thenReturn(customer);
		ProductDTO productDTO1 = new ProductDTO(1L, "Product", 1L, 123, "Brand", "Description", 100);
		ProductDTO productDTO2 = new ProductDTO(2L, "Product 1", 1L, 1234, "Brand", "Description1", 100);
		List<ProductDTO> productDTOlist = Arrays.asList(productDTO1, productDTO2);
		CartDTO cartDTO = new CartDTO();
		cartDTO.setId(1l);
		ItemDTO itemDTO1 = new ItemDTO();
		itemDTO1.setProduct(productDTO1);
		itemDTO1.setId(1L);
		ItemDTO itemDTO2 = new ItemDTO();
		itemDTO2.setProduct(productDTO2);
		itemDTO2.setId(2L);
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("John");
		customerDTO.setLastName("Johns");
		customerDTO.setEmail("email");
		customerDTO.setPassword("password");
		customerDTO.setCart(cartDTO);
		customerDTO.setDeferreds(new ItemListDTO(List.of(itemDTO1, itemDTO2)));
		customerDTO.setAddress(new AddressDTO());
		customerDTO.setId(1L);
		when(itemMapper.itemDTOtoItem(itemDTO1)).thenReturn(item1);
		when(itemMapper.itemDTOtoItem(itemDTO2)).thenReturn(item2);
		when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDTO);
		when(customerRepository.save(customer)).thenReturn(customerWithId);
		customerService.patchCustomer(1L, customerDTO);
		verify(customerRepository, times(1)).getById(1L);
		verify(customerRepository, times(1)).save(customer);
	}

}
