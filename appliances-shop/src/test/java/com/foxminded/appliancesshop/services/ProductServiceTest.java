package com.foxminded.appliancesshop.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.mappers.ProductMapper;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.repositories.CategoryRepository;
import com.foxminded.appliancesshop.repositories.ItemRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

@SpringBootTest(classes = { ProductService.class, ProductMapper.class })
class ProductServiceTest {

	@MockBean
	private ItemRepository itemRepository;

	@MockBean
	private CategoryRepository categoryRepository;

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private ProductMapper productMapper;

	@Autowired
	private ProductService productService;

	@Test
	void getAllProductsTest() {
		Category category = new Category();
		category.setId(1L);
		Product product = new Product();
		product.setId(1L);
		product.setName("Product");
		product.setCategory(category);
		Product product1 = new Product();
		product1.setId(2L);
		product1.setName("Product1");
		product1.setCategory(category);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Product");
		productDTO.setCategoryId(1L);
		ProductDTO productDTO1 = new ProductDTO();
		productDTO1.setId(2L);
		productDTO1.setName("Product1");
		productDTO1.setCategoryId(1L);
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
		when(productMapper.productToProductDTO(product1)).thenReturn(productDTO1);
		when(productRepository.findAll()).thenReturn(Arrays.asList(product, product1));
		productService.getAllProducts();
		verify(productRepository, times(1)).findAll();
		verify(productMapper, times(1)).productToProductDTO(product);
		verify(productMapper, times(1)).productToProductDTO(product1);
	}

	@Test
	void getProductByIdTest() {
		Product product = new Product();
		product.setId(1L);
		product.setName("Product");
		Optional<Product> optionalProduct = Optional.of(product);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Product");
		when(productRepository.findById(1L)).thenReturn(optionalProduct);
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
		productService.getProductById(1L);
		verify(productRepository, times(1)).findById(1L);
	}

	@Test
	void getProductByNameTest() {
		Category category = new Category();
		category.setId(1L);
		Product product = new Product();
		product.setId(1L);
		product.setName("Product");
		product.setCategory(category);
		Optional<Product> optionalProduct = Optional.of(product);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Product");
		productDTO.setCategoryId(1L);
		when(productRepository.findByName("Product")).thenReturn(optionalProduct);
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
		productService.getProductByName("Product");
		verify(productRepository, times(1)).findByName("Product");
	}

	@Test
	void getAllProductsByBrandTest() {
		Category category = new Category();
		category.setId(1L);
		Product product = new Product();
		product.setId(1L);
		product.setName("Product");
		product.setCategory(category);
		product.setBrand("brand");
		Product product1 = new Product();
		product1.setId(2L);
		product1.setName("Product1");
		product1.setCategory(category);
		product1.setBrand("brand");
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Product");
		productDTO.setCategoryId(1L);
		productDTO.setBrand("brand");
		ProductDTO productDTO1 = new ProductDTO();
		productDTO1.setId(2L);
		productDTO1.setName("Product1");
		productDTO1.setCategoryId(1L);
		productDTO1.setBrand("brand");
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
		when(productMapper.productToProductDTO(product1)).thenReturn(productDTO1);
		when(productRepository.findAllProductsByBrand("brand")).thenReturn(Arrays.asList(product, product1));
		productService.getAllProductsByBrand("brand");
		verify(productRepository, times(1)).findAllProductsByBrand("brand");
		verify(productMapper, times(1)).productToProductDTO(product);
		verify(productMapper, times(1)).productToProductDTO(product1);
	}

	@Test
	void getAllProductsInCategoryTest() {
		Category category = new Category();
		category.setId(1L);
		Product product = new Product();
		product.setId(1L);
		product.setName("Product");
		product.setCategory(category);
		product.setBrand("brand");
		Product product1 = new Product();
		product1.setId(2L);
		product1.setName("Product1");
		product1.setCategory(category);
		product1.setBrand("brand");
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Product");
		productDTO.setCategoryId(1L);
		productDTO.setBrand("brand");
		ProductDTO productDTO1 = new ProductDTO();
		productDTO1.setId(2L);
		productDTO1.setName("Product1");
		productDTO1.setCategoryId(1L);
		productDTO1.setBrand("brand");
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
		when(productMapper.productToProductDTO(product1)).thenReturn(productDTO1);
		when(productRepository.findAllProductsInCategory(1L)).thenReturn(Arrays.asList(product, product1));
		productService.getAllProductsInCategory(1L);
		verify(productRepository, times(1)).findAllProductsInCategory(1L);
		verify(productMapper, times(1)).productToProductDTO(product);
		verify(productMapper, times(1)).productToProductDTO(product1);
	}

	@Test
	void getAllProductsInAllSubCategoriesTest() {
		Product product = new Product();
		product.setId(1L);
		product.setName("Product");
		product.setBrand("brand");
		Product product1 = new Product();
		product1.setId(2L);
		product1.setName("Product1");
		product1.setBrand("brand");
		Product product2 = new Product();
		product2.setId(1L);
		product2.setName("Product");
		product2.setBrand("brand");
		Product product3 = new Product();
		product3.setId(2L);
		product3.setName("Product1");
		product3.setBrand("brand");
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Product");
		productDTO.setCategoryId(1L);
		productDTO.setBrand("brand");
		ProductDTO productDTO1 = new ProductDTO();
		productDTO1.setId(2L);
		productDTO1.setName("Product1");
		productDTO1.setCategoryId(2L);
		productDTO1.setBrand("brand");
		ProductDTO productDTO2 = new ProductDTO();
		productDTO2.setId(3L);
		productDTO2.setName("Product2");
		productDTO2.setCategoryId(2L);
		productDTO2.setBrand("brand");
		ProductDTO productDTO3 = new ProductDTO();
		productDTO3.setId(4L);
		productDTO3.setName("Product2");
		productDTO3.setCategoryId(3L);
		productDTO3.setBrand("brand");
		Category superCategory = new Category();
		superCategory.setId(4L);
		Category category = new Category();
		category.setId(1L);
		category.setSuperCategory(superCategory);
		category.addProductToCategory(product);
		Category category1 = new Category();
		category1.setId(2L);
		category1.setSuperCategory(superCategory);
		category1.addProductToCategory(product1);
		category1.addProductToCategory(product2);
		Category subCategory = new Category();
		subCategory.setId(3L);
		subCategory.setSuperCategory(category1);
		subCategory.addProductToCategory(product3);
		product.setCategory(category);
		product1.setCategory(category1);
		product2.setCategory(category1);
		product3.setCategory(subCategory);
		when(categoryRepository.findSubCategories(4L)).thenReturn(List.of(category, category1));
		when(categoryRepository.findSubCategories(2L)).thenReturn(List.of(subCategory));
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
		when(productMapper.productToProductDTO(product1)).thenReturn(productDTO1);
		when(productMapper.productToProductDTO(product2)).thenReturn(productDTO2);
		when(productMapper.productToProductDTO(product3)).thenReturn(productDTO3);
		productService.getAllProductsInAllSubCategories(4L);
		verify(categoryRepository, times(1)).findSubCategories(4L);
		verify(categoryRepository, times(1)).findSubCategories(1L);
		verify(categoryRepository, times(1)).findSubCategories(2L);
		verify(productMapper, times(1)).productToProductDTO(product);
		verify(productMapper, times(1)).productToProductDTO(product1);
		verify(productMapper, times(1)).productToProductDTO(product2);
		verify(productMapper, times(1)).productToProductDTO(product3);
	}

	@Test
	void createNewProductTest() {
		Product product = new Product();
		product.setId(1L);
		product.setName("John");
		Product productWithoutId = new Product();
		productWithoutId.setName("John");
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("John");
		when(productMapper.productDTOtoProduct(productDTO)).thenReturn(productWithoutId);
		when(productRepository.save(productWithoutId)).thenReturn(product);
		ProductDTO productDTO1 = new ProductDTO();
		productDTO1.setId(1L);
		productDTO1.setName("John");
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO1);
		productService.createNewProduct(productDTO);
		verify(productRepository, times(1)).save(productWithoutId);
		verify(productMapper, times(1)).productDTOtoProduct(productDTO);
		verify(productMapper, times(1)).productToProductDTO(product);
	}

	@Test
	void saveProductByDTOTest() {
		Product product = new Product();
		product.setId(1L);
		product.setName("Product");
		Optional<Product> optionalProduct = Optional.of(product);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Product");
		ProductDTO productDTO1 = new ProductDTO();
		productDTO1.setName("Product");
		productDTO1.setId(1l);
		Product productWithoutId = new Product();
		productWithoutId.setName("Product");
		when(productMapper.productDTOtoProduct(productDTO)).thenReturn(productWithoutId);
		when(productRepository.findById(1L)).thenReturn(optionalProduct);
		when(productRepository.save(productWithoutId)).thenReturn(product);
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO1);
		productService.saveProductByDTO(1L, productDTO);
		verify(productRepository, times(1)).findById(1L);
		verify(productRepository, times(1)).save(productWithoutId);
		verify(productMapper, times(1)).productDTOtoProduct(productDTO);
		verify(productMapper, times(1)).productToProductDTO(product);
	}

	@Test
	void patchProduct() {
		Category category = new Category();
		category.setId(1l);
		Product productWithoutId = new Product();
		ProductDTO productDTO = new ProductDTO(1L, "Product", 1L, 123, "Brand", "Description", 100);
		Product product = new Product(1L, "Product", category, 123, "Brand", "Description", 100);
		when(productRepository.getById(1L)).thenReturn(productWithoutId);
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
		when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
		productService.patchProduct(1L, productDTO);
		verify(productRepository, times(1)).getById(1L);
		verify(productRepository, times(1)).save(productWithoutId);
	}

}
