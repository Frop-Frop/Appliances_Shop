package com.foxminded.appliancesshop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.model.ItemDTO;

@Mapper
public interface ItemMapper {

	ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

	@Mapping(source = "id", target = "id")
	ItemDTO itemToItemDTO(Item item);

	Item itemDTOtoItem(ItemDTO itemDTO);

}
