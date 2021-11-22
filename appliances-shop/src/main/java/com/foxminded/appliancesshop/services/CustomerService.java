package com.foxminded.appliancesshop.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.mappers.AddressMapper;
import com.foxminded.appliancesshop.mappers.CartMapper;
import com.foxminded.appliancesshop.mappers.CustomerMapper;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.model.CustomerDTO;
import com.foxminded.appliancesshop.model.CustomerListDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartService cartService;

	private static final Logger log = Logger.getLogger(CustomerService.class.getName());

	public CustomerListDTO getAllCustomers() {
		log.debug("getAllCustomers() called in CustomerService");
		return new CustomerListDTO(customerRepository.findAll().stream().map(customerMapper::customerToCustomerDTO)
				.collect(Collectors.toList()));
	}

	public CustomerDTO getCustomerById(Long id) {
		log.debug("getCustomerById() called in CustomerService with customer id: " + id);
		return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO)
				.orElseThrow(ResourseNotFoundException::new);
	}

	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		log.debug("createNewCustomer() called in CustomerService with customerDTO: " + customerDTO);
		Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
		if (customer.getCart() == null) {
			Cart cart = cartRepository.save(new Cart());
			customer.setCart(cart);
		}
		Customer savedCustomer = customerRepository.save(customer);
		return customerMapper.customerToCustomerDTO(savedCustomer);
	}

	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		log.debug("saveCustomerByDTO() called in CustomerService with customerDTO: " + customerDTO
				+ " and customer id: " + id);
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if (optionalCustomer.isEmpty()) {
			throw new ResourseNotFoundException();
		}
		Customer customer = optionalCustomer.get();
		Cart cart = customer.getCart();
		cartService.saveCartByDTO(cart.getId(), customerDTO.getCart());
		customer = customerMapper.customerDTOtoCustomer(customerDTO);
		customer.setId(id);
		return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
	}

	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		log.debug("patchCustomer() called in CustomerService with customerDTO: " + customerDTO + " and customer id: "
				+ id);
		Customer customer = customerRepository.getById(id);
		if (customer == null) {
			throw new ResourseNotFoundException();
		}
		if (customer.getFirstName() == null) {
			customer.setFirstName(customerDTO.getFirstName());
		}
		if (customer.getLastName() == null) {
			customer.setLastName(customerDTO.getLastName());
		}
		if (customer.getEmail() == null) {
			customer.setEmail(customerDTO.getEmail());
		}
		if (customer.getPassword() == null) {
			customer.setPassword(customerDTO.getPassword());
		}
		if (customer.getCart() == null) {
			customer.setCart(cartMapper.cartDTOtoCart(customerDTO.getCart()));
		}
		if (customer.getDeferreds().isEmpty()) {
			customer.setDeferreds(customerDTO.getDeferreds().getItems().stream().map(itemMapper::itemDTOtoItem)
					.collect(Collectors.toSet()));
		}
		if (customer.getAddress() == null) {
			customer.setAddress(addressMapper.addressDTOtoAddress(customerDTO.getAddress()));
		}
		return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
	}

	public void deleteById(Long id) {
		log.debug("deleteById() called in CustomerService with customer id: " + id);
		Customer customer = customerRepository.getById(id);
		customerRepository.delete(customer);
	}

}
