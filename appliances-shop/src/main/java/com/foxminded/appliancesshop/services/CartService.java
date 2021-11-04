package com.foxminded.appliancesshop.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.mappers.CartMapper;
import com.foxminded.appliancesshop.mappers.CustomerMapperImpl;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private CustomerMapperImpl customerMapper;

	@Autowired
	private ItemMapper itemMapper;

	public CartDTO getCartById(Long id) {
		return cartRepository.findById(id).map(cartMapper::cartToCartDTO).orElseThrow(ResourseNotFoundException::new);
	}

	public CartDTO saveCartByDTO(Long id, CartDTO cartDTO) {
		Cart cart = cartRepository.findById(id).get();
		if (cart == null) {
			throw new ResourseNotFoundException();
		}
		cart = cartMapper.cartDTOtoCart(cartDTO);
		cart.setId(id);
		return cartMapper.cartToCartDTO(cartRepository.save(cart));
	}

	public CartDTO patchCart(Long id, CartDTO cartDTO) {
		Cart cart = cartRepository.getById(id);
		if (cart == null) {
			throw new ResourseNotFoundException();
		}
		if (cart.getCustomer() == null) {
			cart.setCustomer(customerMapper.customerDTOtoCustomer(cartDTO.getCustomer()));
		}
		if (cart.getItemsList() == null) {
			cart.setItems(
					cartDTO.getItems().getItems().stream().map(itemMapper::itemDTOtoItem).collect(Collectors.toSet()));
		}
		return cartMapper.cartToCartDTO(cartRepository.save(cart));
	}

}
