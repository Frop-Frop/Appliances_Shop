package com.foxminded.appliancesshop.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Order;
import com.foxminded.appliancesshop.model.AddressDTO;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.model.CustomerDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.model.OrderDTO;

@Component
public class CustomerMapper {

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public CustomerDTO customerToCustomerDTO(Customer customer) {
		if (customer == null) {
			return null;
		}
		CartDTO cartDTO = null;
		if (customer.getCart() != null) {
			cartDTO = new CartDTO(customer.getCart().getId(), customer.getCart().getItemsList().stream()
					.map(itemMapper::itemToItemDTO).collect(Collectors.toList()));
		}

		OrderDTO orderDTO = null;
		if (customer.getOrder() != null) {
			orderDTO = new OrderDTO(
					customer.getOrder().getId(), customer.getOrder().getItemsList().stream()
							.map(itemMapper::itemToItemDTO).collect(Collectors.toList()),
					customer.getOrder().getData());
		}

		AddressDTO addressDTO = null;
		if (customer.getAddress() != null) {
			addressDTO = addressMapper.addressToAddressDTOWithoutCustomer(customer.getAddress());
		}
		CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(),
				customer.getEmail(), "password is not displayed", cartDTO, orderDTO, new ItemListDTO(customer
						.getDeferredsList().stream().map(itemMapper::itemToItemDTO).collect(Collectors.toList())),
				addressDTO, customer.getRole(), customer.getStatus());
		return customerDTO;
	}

	public Customer customerDTOtoCustomer(CustomerDTO customerDTO) {
		if (customerDTO == null) {
			return null;
		}
		Set<Item> cartItems = customerDTO.getCart().getItems().getItems().stream().map(itemMapper::itemDTOtoItem)
				.collect(Collectors.toSet());
		Set<Item> orderItems = customerDTO.getOrder().getItems().getItems().stream().map(itemMapper::itemDTOtoItem)
				.collect(Collectors.toSet());
		Cart cart = null;
		if (customerDTO.getCart() != null) {
			cart = new Cart(customerDTO.getCart().getId(), cartItems);
		}
		Order order = null;
		if (customerDTO.getOrder() != null) {
			order = new Order(customerDTO.getOrder().getId(), orderItems);
		}
		Address address = null;
		if (customerDTO.getAddress() != null) {
			address = addressMapper.addressDTOtoAddressWithoutCustomer(customerDTO.getAddress());
		}
		Set<Item> items = new HashSet<>();
		customerDTO.getDeferreds().getItems().stream().map(itemMapper::itemDTOtoItem).forEach(items::add);
		Customer customer = new Customer(customerDTO.getId(), customerDTO.getFirstName(), customerDTO.getLastName(),
				customerDTO.getEmail(), passwordEncoder.encode(customerDTO.getPassword()), cart, order, items, address,
				customerDTO.getRole(), customerDTO.getStatus());
		return customer;
	}

}
