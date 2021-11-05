package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemListDTO {

	private List<ItemDTO> items;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items.isEmpty()) ? 0 : items.stream().mapToInt(i -> i.getId().intValue()).sum());
		return result;
	}

	public ItemListDTO(List<ItemDTO> itemList) {
		this.items = itemList;
	}

}
