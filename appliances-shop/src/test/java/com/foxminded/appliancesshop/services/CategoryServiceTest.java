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
import com.foxminded.appliancesshop.mappers.CategoryMapper;
import com.foxminded.appliancesshop.mappers.ProductMapper;
import com.foxminded.appliancesshop.model.CategoryDTO;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.model.ProductListDTO;
import com.foxminded.appliancesshop.repositories.CategoryRepository;

@SpringBootTest(classes = { CategoryService.class, CategoryMapper.class, ProductMapper.class })
class CategoryServiceTest {

	@MockBean
	private CategoryMapper categoryMapper;

	@MockBean
	private ProductMapper productMapper;

	@MockBean
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Test
	void getAllCategoriesTest() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Category1");
		Category category1 = new Category();
		category1.setId(2L);
		category1.setName("Category2");
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("Category1");
		CategoryDTO categoryDTO1 = new CategoryDTO();
		categoryDTO1.setId(2L);
		categoryDTO1.setName("Category2");
		when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDTO);
		when(categoryMapper.categoryToCategoryDTO(category1)).thenReturn(categoryDTO1);
		when(categoryRepository.findAll()).thenReturn(Arrays.asList(category, category1));
		categoryService.getAllCategories();
		verify(categoryRepository, times(1)).findAll();
		verify(categoryMapper, times(1)).categoryToCategoryDTO(category);
		verify(categoryMapper, times(1)).categoryToCategoryDTO(category1);
	}

	@Test
	void getAllCategoryByIdTest() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Category");
		Optional<Category> optionalCategory = Optional.of(category);
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("Category");
		when(categoryRepository.findById(1L)).thenReturn(optionalCategory);
		when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDTO);
		categoryService.getCategoryById(1L);
		verify(categoryRepository, times(1)).findById(1L);
	}

	@Test
	void getAllCategoryByNameTest() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Category");
		Optional<Category> optionalCategory = Optional.of(category);
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("Category");
		when(categoryRepository.findByName("Category")).thenReturn(optionalCategory);
		when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDTO);
		categoryService.getCategoryByName("Category");
		verify(categoryRepository, times(1)).findByName("Category");
	}

	@Test
	void getSubCategoriesTest() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Category1");
		Category category1 = new Category();
		category1.setId(2L);
		category1.setName("Category2");
		Category superCategory = new Category();
		superCategory.setId(3L);
		superCategory.setName("Super category");
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("Category1");
		CategoryDTO categoryDTO1 = new CategoryDTO();
		categoryDTO1.setId(2L);
		categoryDTO1.setName("Category2");
		when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDTO);
		when(categoryMapper.categoryToCategoryDTO(category1)).thenReturn(categoryDTO1);
		when(categoryRepository.findSubCategories(superCategory.getId()))
				.thenReturn(Arrays.asList(category, category1));
		categoryService.getSubCategories(superCategory.getId());
		verify(categoryRepository, times(1)).findSubCategories(superCategory.getId());
		verify(categoryMapper, times(1)).categoryToCategoryDTO(category);
		verify(categoryMapper, times(1)).categoryToCategoryDTO(category1);
	}

	@Test
	void createNewCategoryTest() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Category1");
		Category categoryWithoutId = new Category();
		categoryWithoutId.setName("Category1");
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName("Category1");
		when(categoryMapper.categoryDTOtoCategory(categoryDTO)).thenReturn(categoryWithoutId);
		when(categoryRepository.save(categoryWithoutId)).thenReturn(category);
		CategoryDTO categoryDTO1 = new CategoryDTO();
		categoryDTO1.setId(1L);
		categoryDTO1.setName("Category1");
		when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDTO1);
		categoryService.createNewCategory(categoryDTO);
		verify(categoryRepository, times(1)).save(categoryWithoutId);
		verify(categoryMapper, times(1)).categoryDTOtoCategory(categoryDTO);
		verify(categoryMapper, times(1)).categoryToCategoryDTO(category);
	}

	@Test
	void saveCategoryByDTOTest() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Category1");
		Optional<Category> optionalCategory = Optional.of(category);
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName("Category1");
		CategoryDTO categoryDTO1 = new CategoryDTO();
		categoryDTO1.setName("Category1");
		categoryDTO1.setId(1l);
		Category categoryWithoutId = new Category();
		categoryWithoutId.setName("Category1");
		when(categoryMapper.categoryDTOtoCategory(categoryDTO)).thenReturn(categoryWithoutId);
		when(categoryRepository.findById(1L)).thenReturn(optionalCategory);
		when(categoryRepository.save(categoryWithoutId)).thenReturn(category);
		when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDTO1);
		categoryService.saveCategoryByDTO(1L, categoryDTO);
		verify(categoryRepository, times(1)).findById(1L);
		verify(categoryRepository, times(1)).save(categoryWithoutId);
		verify(categoryMapper, times(1)).categoryDTOtoCategory(categoryDTO);
		verify(categoryMapper, times(1)).categoryToCategoryDTO(category);
	}

	@Test
	void patchCategory() {
		Category category = new Category();
		category.setName("Category");
		Category categoryWithId = new Category();
		category.setName("Category");
		category.setId(1L);
		Category superCategory = new Category();
		superCategory.setId(3L);
		when(categoryRepository.getById(1L)).thenReturn(category);
		ProductDTO productDTO1 = new ProductDTO(1L, "Product", 1L, 123, "Brand", "Description");
		ProductDTO productDTO2 = new ProductDTO(2L, "Product 1", 1L, 1234, "Brand", "Description1");
		Product product = new Product(1L, "Product", category, 123, "Brand", "Description");
		Product product1 = new Product(2L, "Product 1", category, 1234, "Brand", "Description1");
		List<ProductDTO> productDTOlist = Arrays.asList(productDTO1, productDTO2);
		CategoryDTO superCategoryDTO = new CategoryDTO();
		superCategoryDTO.setId(3L);
		superCategoryDTO.setName("Super category");
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName("Category1");
		categoryDTO.setProducts(new ProductListDTO(productDTOlist));
		categoryDTO.setSuperCategory(superCategoryDTO);
		when(productMapper.productDTOtoProduct(productDTO1)).thenReturn(product);
		when(productMapper.productDTOtoProduct(productDTO2)).thenReturn(product1);
		when(categoryRepository.getById(3L)).thenReturn(superCategory);
		when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDTO);
		when(categoryRepository.save(category)).thenReturn(categoryWithId);
		categoryService.patchCategory(1L, categoryDTO);
		verify(categoryRepository, times(1)).getById(1L);
		verify(categoryRepository, times(1)).getById(3L);
		verify(categoryRepository, times(1)).save(category);
	}

}
