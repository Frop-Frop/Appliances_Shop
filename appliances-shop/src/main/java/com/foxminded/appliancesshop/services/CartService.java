package com.foxminded.appliancesshop.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.mappers.CartMapper;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemMapper itemMapper;

	public CartDTO getCartById(Long id) {
		return cartRepository.findById(id).map(cartMapper::cartToCartDTO).orElseThrow(ResourseNotFoundException::new);
	}

	public CartDTO saveCartByDTO(Long id, CartDTO cartDTO) {
		Optional<Cart> optionalCart = cartRepository.findById(id);
		if (optionalCart == null) {
			throw new ResourseNotFoundException();
		}
		Cart cart = optionalCart.get();
		cart = cartMapper.cartDTOtoCart(cartDTO);
		cart.setId(id);
		return cartMapper.cartToCartDTO(cartRepository.save(cart));
	}

	public CartDTO patchCart(Long id, CartDTO cartDTO) {
		Optional<Cart> optionalCart = cartRepository.findById(id);
		if (optionalCart.isEmpty()) {
			throw new ResourseNotFoundException("Cart with id: " + id + "not found");
		}
		Cart cart = optionalCart.get();
		if (cart.getCustomer() == null) {
			Optional<Customer> customer = customerRepository.findById(cartDTO.getCustomerId());
			if (customer.isEmpty()) {
				throw new ResourseNotFoundException("Customer with id: " + id + "not found");
			}
			cart.setCustomer(customer.get());
		}
		if (cart.getItemsList() == null) {
			cart.setItems(
					cartDTO.getItems().getItems().stream().map(itemMapper::itemDTOtoItem).collect(Collectors.toSet()));
		}
		return cartMapper.cartToCartDTO(cartRepository.save(cart));
	}

}
