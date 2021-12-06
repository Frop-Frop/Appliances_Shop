package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemListDTO {

	private List<ItemDTO> items;

	public ItemListDTO(List<ItemDTO> itemList) {
		this.items = itemList;
	}

}
