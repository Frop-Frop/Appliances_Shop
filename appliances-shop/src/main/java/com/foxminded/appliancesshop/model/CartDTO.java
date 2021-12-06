package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

	private Long id;
	private Long customerId;
	private ItemListDTO items;

	public CartDTO(Long id, List<ItemDTO> items) {
		this.id = id;
		this.items = new ItemListDTO(items);
	}

}
