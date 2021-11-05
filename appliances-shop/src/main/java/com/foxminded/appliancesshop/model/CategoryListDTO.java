package com.foxminded.appliancesshop.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.mappers.CategoryMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryListDTO {

	@Autowired
	private CategoryMapper mapper;

	private List<CategoryDTO> categories;

	public CategoryListDTO(List<Category> categoryList) {
		this.categories = categoryList.stream().map(mapper::categoryToCategoryDTO).collect(Collectors.toList());
	}

}
