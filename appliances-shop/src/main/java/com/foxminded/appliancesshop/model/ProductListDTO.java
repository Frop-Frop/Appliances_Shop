package com.foxminded.appliancesshop.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.mappers.ProductMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListDTO {

	@Autowired
	private ProductMapper mapper;

	private List<ProductDTO> products;

	public ProductListDTO(List<Product> productList) {
		this.products = productList.stream().map(mapper::productToProductDTO).collect(Collectors.toList());
	}

}
