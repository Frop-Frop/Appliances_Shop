package com.foxminded.appliancesshop.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private Long id;
	private Long customerId;
	private ItemListDTO items;
	private Date data;

	public OrderDTO(Long id, List<ItemDTO> items, Date data) {
		this.id = id;
		this.items = new ItemListDTO(items);
		this.data = data;
	}

}
