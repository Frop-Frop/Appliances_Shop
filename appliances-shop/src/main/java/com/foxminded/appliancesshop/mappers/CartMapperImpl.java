package com.foxminded.appliancesshop.mappers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.model.CustomerDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;

@Component
public class CartMapperImpl implements CartMapper {

	@Autowired
	private CustomerMapperImpl customerMapper;
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public CartDTO cartToCartDTO(Cart cart) {

		if (cart == null) {
			return null;
		}
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(cart.getCustomer());
		CartDTO cartDTO = new CartDTO(cart.getId(), customerDTO, new ItemListDTO(cart.getItemsList()));
		return cartDTO;
	}

	@Override
	public Cart cartDTOtoCart(CartDTO cartDTO) {

		if (cartDTO == null) {
			return null;
		}
		Customer customer = customerMapper.customerDTOtoCustomer(cartDTO.getCustomer());
		Set<Item> items = new HashSet<>();
		cartDTO.getItems().getItems().stream().map(itemMapper::itemDTOtoItem).forEach(items::add);
		Cart cart = new Cart(cartDTO.getId(), customer, items);
		return cart;
	}

}
