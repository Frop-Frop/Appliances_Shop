package com.foxminded.appliancesshop.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
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

	private static final Logger log = Logger.getLogger(CartService.class.getName());

	public CartDTO getCartById(Long id) {
		log.debug("getCartById() called in CartService with cart id: " + id);
		return cartRepository.findById(id).map(cartMapper::cartToCartDTO).orElseThrow(ResourseNotFoundException::new);
	}

	public CartDTO saveCartByDTO(Long id, CartDTO cartDTO) {
		log.debug("saveCartByDTO called in CartService with cartDTO: " + cartDTO + " and cart id: " + id);
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
		log.debug("patchCart called in CartService with cartDTO: " + cartDTO + " and cart id: " + id);
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
