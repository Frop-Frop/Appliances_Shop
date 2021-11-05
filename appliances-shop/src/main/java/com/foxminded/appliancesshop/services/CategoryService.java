package com.foxminded.appliancesshop.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.mappers.CategoryMapper;
import com.foxminded.appliancesshop.mappers.ProductMapper;
import com.foxminded.appliancesshop.model.CategoryDTO;
import com.foxminded.appliancesshop.model.CategoryListDTO;
import com.foxminded.appliancesshop.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryListDTO getAllCategories() {
		return new CategoryListDTO(categoryRepository.findAll());
	}

	public CategoryDTO getCategoryById(Long id) {
		return categoryMapper.categoryToCategoryDTO(categoryRepository.findById(id).get());
	}

	public CategoryDTO getCategoryByName(String name) {
		return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name).get());
	}

	public CategoryListDTO getSubCategories(Long id) {
		return new CategoryListDTO(categoryRepository.findSubCategories(id));
	}

	public CategoryDTO createNewCategory(CategoryDTO categoryDTO) {
		Category category = categoryMapper.categoryDTOtoCategory(categoryDTO);
		Category savedCategory = categoryRepository.save(category);
		return categoryMapper.categoryToCategoryDTO(savedCategory);
	}

	public CategoryDTO saveCategoryByDTO(Long id, CategoryDTO categoryDTO) {
		Category category = categoryRepository.findById(id).get();
		if (category == null) {
			throw new ResourseNotFoundException();
		}
		category = categoryMapper.categoryDTOtoCategory(categoryDTO);
		category.setId(id);
		return categoryMapper.categoryToCategoryDTO(categoryRepository.save(category));
	}

	public CategoryDTO patchCategory(Long id, CategoryDTO categoryDTO) {
		Category category = categoryRepository.getById(id);
		if (category == null) {
			throw new ResourseNotFoundException();
		}
		if (category.getName() == null) {
			category.setName(categoryDTO.getName());
		}
		if (category.getProducts() == null) {
			category.setProducts(categoryDTO.getProducts().getProducts().stream()
					.map(productMapper::productDTOtoProduct).collect(Collectors.toSet()));
		}
		if (category.getSuperCategory() == null) {
			category.setSuperCategory(categoryRepository.getById(categoryDTO.getSuperCategory().getId()));
		}
		return categoryMapper.categoryToCategoryDTO(categoryRepository.save(category));
	}

	public void deleteById(Long id) {
		Category category = categoryRepository.getById(id);
		categoryRepository.delete(category);
	}

}
