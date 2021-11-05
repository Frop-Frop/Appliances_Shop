package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartListDTO {

	private List<CartDTO> carts;

	public CartListDTO(List<CartDTO> cartsList) {
		this.carts = cartsList;
	}

}
