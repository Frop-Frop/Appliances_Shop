package com.foxminded.appliancesshop.mappers;

import java.util.HashSet;
import java.util.Set;

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
public class CustomerMapperImpl /* implements CustomerMapper */ {

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private ItemMapper itemMapper;

	// @Override
	public CustomerDTO customerToCustomerDTO(Customer customer) {
		if (customer == null) {
			return null;
		}
		CartDTO cartDTO = cartMapper.cartToCartDTO(customer.getCart());
		AddressDTO addressDTO = addressMapper.addressToAddressDTO(customer.getAddress());
		CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(),
				customer.getEmail(), customer.getPassword(), cartDTO, new ItemListDTO(customer.getDeferredsList()),
				addressDTO);
		return customerDTO;
	}

	// @Override
	public Customer customerDTOtoCustomer(CustomerDTO customerDTO) {
		if (customerDTO == null) {
			return null;
		}
		Cart cart = cartMapper.cartDTOtoCart(customerDTO.getCart());
		Address address = addressMapper.addressDTOtoAddress(customerDTO.getAddress());
		Set<Item> items = new HashSet<>();
		customerDTO.getDeferreds().getItems().stream().map(itemMapper::itemDTOtoItem).forEach(items::add);
		Customer customer = new Customer(customerDTO.getId(), customerDTO.getFirstName(), customerDTO.getFirstName(),
				customerDTO.getEmail(), customerDTO.getPassword(), cart, items, address);
		return customer;
	}

}
