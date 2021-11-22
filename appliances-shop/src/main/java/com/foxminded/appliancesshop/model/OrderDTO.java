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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.intValue();
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

}
