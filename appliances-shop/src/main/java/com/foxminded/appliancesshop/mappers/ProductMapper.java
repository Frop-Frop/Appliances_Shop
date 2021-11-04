package com.foxminded.appliancesshop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.model.ProductDTO;

@Mapper
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@Mapping(source = "id", target = "id")
	ProductDTO productToProductDTO(Product product);

	Product productDTOtoProduct(ProductDTO productDTO);

}
