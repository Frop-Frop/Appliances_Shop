package com.foxminded.appliancesshop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.mappers.ProductMapper;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.model.ProductListDTO;
import com.foxminded.appliancesshop.repositories.CategoryRepository;
import com.foxminded.appliancesshop.repositories.ItemRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ItemRepository itemRepository;

	public ProductListDTO getAllProducts() {
		return new ProductListDTO(productRepository.findAll().stream().map(productMapper::productToProductDTO)
				.collect(Collectors.toList()));
	}

	public ProductDTO getProductById(Long id) {
		return productRepository.findById(id).map(productMapper::productToProductDTO)
				.orElseThrow(ResourseNotFoundException::new);
	}

	public ProductDTO getProductByName(String name) {
		return productMapper.productToProductDTO(productRepository.findByName(name).get());
	}

	public ProductListDTO getAllProductsByBrand(String brand) {
		return new ProductListDTO(productRepository.findAllProductsByBrand(brand).stream()
				.map(productMapper::productToProductDTO).collect(Collectors.toList()));
	}

	public ProductListDTO getAllProductsInCategory(Long id) {
		return new ProductListDTO(productRepository.findAllProductsInCategory(id).stream()
				.map(productMapper::productToProductDTO).collect(Collectors.toList()));
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
		List<ProductDTO> products = new ArrayList<>();
		allSubCategories.stream().forEach(subcategory -> {
			subcategory.getProducts().stream().map(product -> productMapper.productToProductDTO(product))
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
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isEmpty()) {
			throw new ResourseNotFoundException("Product with id: " + id + " not found");
		}
		Product product = optionalProduct.get();
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
			Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
			if (category.isEmpty()) {
				throw new ResourseNotFoundException("Category with id: " + productDTO.getId() + "not found");
			}
			product.setCategory(category.get());
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
		return productMapper.productToProductDTO(productRepository.save(product));
	}

	@Transactional
	public void deleteById(Long id) {
		itemRepository.deleteItemsByProduct(id);//
		Product product = productRepository.getById(id);
		productRepository.delete(product);
	}

}
