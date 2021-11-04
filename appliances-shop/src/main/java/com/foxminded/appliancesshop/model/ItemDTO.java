package com.foxminded.appliancesshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

	private Long id;
	private Integer quantity;
	private ProductDTO product;
	private CartDTO cart;
	private CustomerDTO customer;
	private Integer cost;

}
