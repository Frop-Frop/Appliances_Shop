package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderListDTO {

	private List<OrderDTO> orders;

	public OrderListDTO(List<OrderDTO> ordersList) {
		this.orders = ordersList;
	}

}
