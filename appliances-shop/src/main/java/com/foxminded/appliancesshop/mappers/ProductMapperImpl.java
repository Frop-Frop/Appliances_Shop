package com.foxminded.appliancesshop.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.model.CategoryDTO;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ProductDTO;

@Component
public class ProductMapperImpl implements ProductMapper {

	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public ProductDTO productToProductDTO(Product product) {
		if (product == null) {
			return null;
		}
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(product.getCategory());
		ItemDTO itemDTO = itemMapper.itemToItemDTO(product.getItem());
		ProductDTO productDTO = new ProductDTO(product.getId(), product.getName(), categoryDTO, product.getPrice(),
				product.getBrand(), product.getDescription(), itemDTO);
		return productDTO;
	}

	@Override
	public Product productDTOtoProduct(ProductDTO productDTO) {
		if (productDTO == null) {
			return null;
		}
		Category category = categoryMapper.categoryDTOtoCategory(productDTO.getCategory());
		Item item = itemMapper.itemDTOtoItem(productDTO.getItem());
		Product product = new Product(productDTO.getId(), productDTO.getName(), category, productDTO.getPrice(),
				productDTO.getBrand(), productDTO.getDescription(), item);
		return product;
	}

}
