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
	private Long cartId;
	private Long orderId;
	private Long customerId;
	private Integer cost;

}
