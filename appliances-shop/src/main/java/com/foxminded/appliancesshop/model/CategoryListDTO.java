package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryListDTO {

	private List<CategoryDTO> categories;

	public CategoryListDTO(List<CategoryDTO> categoryList) {
		this.categories = categoryList;
	}

}
