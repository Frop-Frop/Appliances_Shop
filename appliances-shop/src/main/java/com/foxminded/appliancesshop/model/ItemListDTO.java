package com.foxminded.appliancesshop.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.mappers.ItemMapperImpl;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemListDTO {

	@Autowired
	private ItemMapperImpl mapper;

	private List<ItemDTO> items;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items.isEmpty()) ? 0 : items.stream().mapToInt(i -> i.getId().intValue()).sum());
		return result;
	}

	public ItemListDTO(List<Item> itemList) {
		this.items = itemList.stream().map(mapper::itemToItemDTO).collect(Collectors.toList());
	}

}
