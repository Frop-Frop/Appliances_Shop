package com.foxminded.appliancesshop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	public ProductListDTO getAllProducts() {
		log.debug("getAllProducts() called in ProductService");
		return new ProductListDTO(productRepository.findAll().stream().map(productMapper::productToProductDTO)
				.collect(Collectors.toList()));
	}

	public ProductDTO getProductById(Long id) {
		log.debug("getProductById() called in ProductService with id: " + id);
		return productRepository.findById(id).map(productMapper::productToProductDTO)
				.orElseThrow(ResourseNotFoundException::new);
	}

	public ProductDTO getProductByName(String name) {
		log.debug("getProductByName() called in ProductService with name: " + name);
		return productMapper.productToProductDTO(productRepository.findByName(name).get());
	}

	public ProductListDTO getAllProductsByBrand(String brand) {
		log.debug("getAllProductsByBrand() called in ProductService with brand: " + brand);
		return new ProductListDTO(productRepository.findAllProductsByBrand(brand).stream()
				.map(productMapper::productToProductDTO).collect(Collectors.toList()));
	}

	public ProductListDTO getAllProductsInCategory(Long id) {
		log.debug("getAllProductsInCategory() called in ProductService with category id: " + id);
		return new ProductListDTO(productRepository.findAllProductsInCategory(id).stream()
				.map(productMapper::productToProductDTO).collect(Collectors.toList()));
	}

	public ProductListDTO getAllProductsInAllSubCategories(Long id) {
		log.debug("getAllProductsInAllSubCategories() called in ProductService with superCategory id: " + id);
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
		log.debug("createNewProduct() called in ProductService with productDTO: " + productDTO);
		Product product = productMapper.productDTOtoProduct(productDTO);
		Product savedProduct = productRepository.save(product);
		return productMapper.productToProductDTO(savedProduct);
	}

	public ProductDTO saveProductByDTO(Long id, ProductDTO productDTO) {
		log.debug("saveProductByDTO() called in ProductService with productDTO: " + productDTO + " and id" + id);
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
		log.debug("patchProduct() called in ProductService with productDTO: " + productDTO + " and id" + id);
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
		log.debug("deleteById() called in ProductService with product id: " + id);
		itemRepository.deleteItemsByProduct(id);
		Product product = productRepository.getById(id);
		productRepository.delete(product);
	}

}
