package com.foxminded.appliancesshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	private Long id;
	private String name;
	private Long categoryId;
	private Integer price;
	private String brand;
	private String description;
	private int unitsLeftInWarehouse;

}
