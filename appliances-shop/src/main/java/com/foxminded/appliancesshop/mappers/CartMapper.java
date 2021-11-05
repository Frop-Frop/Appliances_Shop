package com.foxminded.appliancesshop.mappers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.services.ResourseNotFoundException;

@Component
public class CartMapper {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ItemMapper itemMapper;

	public CartDTO cartToCartDTO(Cart cart) {

		if (cart == null) {
			return null;
		}
		CartDTO cartDTO = new CartDTO(cart.getId(), cart.getCustomer().getId(), new ItemListDTO(
				cart.getItemsList().stream().map(itemMapper::itemToItemDTO).collect(Collectors.toList())));
		return cartDTO;
	}

	public Cart cartDTOtoCart(CartDTO cartDTO) {

		if (cartDTO == null) {
			return null;
		}
		Optional<Customer> customer = customerRepository.findById(cartDTO.getCustomerId());
		if (customer.isEmpty()) {
			throw new ResourseNotFoundException("Customer with id: " + cartDTO.getCustomerId() + " not found");
		}
		Set<Item> items = new HashSet<>();
		cartDTO.getItems().getItems().stream().map(itemMapper::itemDTOtoItem).forEach(items::add);
		Cart cart = new Cart(cartDTO.getId(), customer.get(), items);
		return cart;
	}

}
