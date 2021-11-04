package com.foxminded.appliancesshop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.mappers.CategoryMapper;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.mappers.ProductMapper;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.model.ProductListDTO;
import com.foxminded.appliancesshop.repositories.CategoryRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public ProductListDTO getAllProducts() {
		return new ProductListDTO(productRepository.findAll());
	}

	public ProductDTO getProductById(Long id) {
		return productRepository.findById(id).map(productMapper::productToProductDTO)
				.orElseThrow(ResourseNotFoundException::new);
	}

	public ProductDTO getProductByName(String name) {
		return productMapper.productToProductDTO(productRepository.findByName(name).get());
	}

	public ProductListDTO getAllProductsByBrand(String brand) {
		return new ProductListDTO(productRepository.findAllProductsByBrand(brand));
	}

	public ProductListDTO getAllProductsInCategory(Long id) {
		return new ProductListDTO(productRepository.findAllProductsInCategory(id));
	}

	public ProductListDTO getAllProductsInAllSubCategories(Long id) {
		List<Category> subCategories = categoryRepository.findSubCategories(id);
		List<Category> allSubCategories = new ArrayList<>();
		subCategories.stream().forEach(c -> {
			List<Category> localSubCategories = categoryRepository.findSubCategories(c.getId());
			if (!localSubCategories.isEmpty()) {
				localSubCategories.stream().forEach(allSubCategories::add);
			}
		});
		subCategories.stream().forEach(allSubCategories::add);
		List<Product> products = new ArrayList<>();
		allSubCategories.stream().forEach(subcategory -> {
			getAllProductsInCategory(subcategory.getId()).getProducts().stream().map(productMapper::productDTOtoProduct)
					.forEach(products::add);
		});
		return new ProductListDTO(products);
	}

	public ProductDTO createNewProduct(ProductDTO productDTO) {
		Product product = productMapper.productDTOtoProduct(productDTO);
		Product savedProduct = productRepository.save(product);
		return productMapper.productToProductDTO(savedProduct);
	}

	public ProductDTO saveProductByDTO(Long id, ProductDTO productDTO) {
		Product product = productRepository.findById(id).get();
		if (product == null) {
			throw new ResourseNotFoundException();
		}
		product = productMapper.productDTOtoProduct(productDTO);
		product.setId(id);
		return productMapper.productToProductDTO(productRepository.save(product));
	}

	public ProductDTO patchProduct(Long id, ProductDTO productDTO) {
		Product product = productRepository.getById(id);
		if (product == null) {
			throw new ResourseNotFoundException();
		}
		if (product.getName() == null) {
			product.setName(productDTO.getName());
		}
		if (product.getCategory() == null) {
			product.setCategory(categoryMapper.categoryDTOtoCategory(productDTO.getCategory()));
		}
		if (product.getPrice() == null) {
			product.setPrice(productDTO.getPrice());
		}
		if (product.getBrand() == null) {
			product.setBrand(productDTO.getBrand());
		}
		if (product.getDescription() == null) {
			product.setDescription(productDTO.getDescription());
		}
		if (product.getItem() == null) {
			product.setItem(itemMapper.itemDTOtoItem(productDTO.getItem()));
		}
		return productMapper.productToProductDTO(productRepository.save(product));
	}

	public void deleteById(Long id) {
		Product product = productRepository.getById(id);
		productRepository.delete(product);
	}

}
