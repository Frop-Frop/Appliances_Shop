package com.foxminded.appliancesshop.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.model.CategoryDTO;
import com.foxminded.appliancesshop.model.ProductListDTO;

@Component
public class CategoryMapper {

	@Autowired
	private ProductMapper productMapper;

	public CategoryDTO categoryToCategoryDTO(Category category) {

		if (category == null) {
			return null;
		}
		CategoryDTO superCategory = categoryToCategoryDTO(category.getSuperCategory());
		CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName(), new ProductListDTO(category
				.getProductsList().stream().map(productMapper::productToProductDTO).collect(Collectors.toList())),
				superCategory);
		return categoryDTO;
	}

	public Category categoryDTOtoCategory(CategoryDTO categoryDTO) {

		if (categoryDTO == null) {
			return null;
		}
		Category superCategory = categoryDTOtoCategory(categoryDTO.getSuperCategory());
		Set<Product> products = new HashSet<>();
		categoryDTO.getProducts().getProducts().stream().map(productMapper::productDTOtoProduct).forEach(products::add);
		Category category = new Category(categoryDTO.getId(), categoryDTO.getName(), products, superCategory);
		return category;
	}

}
