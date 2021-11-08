package com.foxminded.appliancesshop.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.model.AddressDTO;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.model.CustomerDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;

@Component
public class CustomerMapper {

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private ItemMapper itemMapper;

	public CustomerDTO customerToCustomerDTO(Customer customer) {
		if (customer == null) {
			return null;
		}
		CartDTO cartDTO = new CartDTO(customer.getCart().getId(),
				customer.getCart().getItemsList().stream().map(itemMapper::itemToItemDTO).collect(Collectors.toList()));
		AddressDTO addressDTO = addressMapper.addressToAddressDTOWithoutCustomer(customer.getAddress());
		CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(),
				customer.getEmail(), customer.getPassword(), cartDTO, new ItemListDTO(customer.getDeferredsList()
						.stream().map(itemMapper::itemToItemDTO).collect(Collectors.toList())),
				addressDTO, customer.getRole(), customer.getStatus());
		return customerDTO;
	}

	public Customer customerDTOtoCustomer(CustomerDTO customerDTO) {
		if (customerDTO == null) {
			return null;
		}
		Set<Item> cartItems = customerDTO.getCart().getItems().getItems().stream().map(itemMapper::itemDTOtoItem)
				.collect(Collectors.toSet());
		Cart cart = new Cart(customerDTO.getCart().getId(), cartItems);
		Address address = addressMapper.addressDTOtoAddressWithoutCustomer(customerDTO.getAddress());
		Set<Item> items = new HashSet<>();
		customerDTO.getDeferreds().getItems().stream().map(itemMapper::itemDTOtoItem).forEach(items::add);
		Customer customer = new Customer(customerDTO.getId(), customerDTO.getFirstName(), customerDTO.getFirstName(),
				customerDTO.getEmail(), customerDTO.getPassword(), cart, items, address, customerDTO.getRole(),
				customerDTO.getStatus());
		return customer;
	}

}
