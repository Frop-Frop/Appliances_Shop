package com.foxminded.appliancesshop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.model.CartDTO;

@Mapper
public interface CartMapper {

	CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

	@Mapping(source = "id", target = "id")
	CartDTO cartToCartDTO(Cart cart);

	Cart cartDTOtoCart(CartDTO cartDTO);

}
