package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListDTO {

	private List<ProductDTO> products;

	public ProductListDTO(List<ProductDTO> productList) {
		this.products = productList;
	}

}
