package com.foxminded.appliancesshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

	private Long id;
	private String name;
	private ProductListDTO products;
	private CategoryDTO superCategory;

}
