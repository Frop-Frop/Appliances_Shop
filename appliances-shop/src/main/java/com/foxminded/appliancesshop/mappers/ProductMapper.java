package com.foxminded.appliancesshop.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.repositories.CategoryRepository;
import com.foxminded.appliancesshop.services.ResourseNotFoundException;

@Component
public class ProductMapper {

	@Autowired
	private CategoryRepository categoryRepository;

	public ProductDTO productToProductDTO(Product product) {
		if (product == null) {
			return null;
		}
		ProductDTO productDTO = new ProductDTO(product.getId(), product.getName(), product.getCategory().getId(),
				product.getPrice(), product.getBrand(), product.getDescription());
		return productDTO;
	}

	public Product productDTOtoProduct(ProductDTO productDTO) {
		if (productDTO == null) {
			return null;
		}
		Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
		if (category.isEmpty()) {
			throw new ResourseNotFoundException("Category with id: " + productDTO.getCategoryId() + " not found");
		}
		Product product = new Product(productDTO.getId(), productDTO.getName(), category.get(), productDTO.getPrice(),
				productDTO.getBrand(), productDTO.getDescription());
		return product;
	}

}
