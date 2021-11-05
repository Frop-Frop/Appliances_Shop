package com.foxminded.appliancesshop.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.mappers.CartMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartListDTO {

	@Autowired
	private CartMapper mapper;

	private List<CartDTO> carts;

	public CartListDTO(List<Cart> cartsList) {
		this.carts = cartsList.stream().map(mapper::cartToCartDTO).collect(Collectors.toList());
	}

}
