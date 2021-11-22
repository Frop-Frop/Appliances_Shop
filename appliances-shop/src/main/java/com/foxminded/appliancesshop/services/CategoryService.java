package com.foxminded.appliancesshop.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
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

	private static final Logger log = Logger.getLogger(CategoryService.class.getName());

	public CategoryListDTO getAllCategories() {
		log.debug("getAllCategories() called in CategoryService");
		return new CategoryListDTO(categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDTO)
				.collect(Collectors.toList()));
	}

	public CategoryDTO getCategoryById(Long id) {
		log.debug("getCategoryById() called in CategoryService with category id: " + id);
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isEmpty()) {
			throw new ResourseNotFoundException("Category with id: " + id + " not found");
		}
		return categoryMapper.categoryToCategoryDTO(category.get());
	}

	public CategoryDTO getCategoryByName(String name) {
		log.debug("getCategoryByName() called in CategoryService with category name: " + name);
		Optional<Category> category = categoryRepository.findByName(name);
		if (category.isEmpty()) {
			throw new ResourseNotFoundException("Category with name: " + name + " not found");
		}
		return categoryMapper.categoryToCategoryDTO(category.get());
	}

	public CategoryListDTO getSubCategories(Long id) {
		log.debug("getSubCategories() called in CategoryService with supercategory id: " + id);
		return new CategoryListDTO(categoryRepository.findSubCategories(id).stream()
				.map(categoryMapper::categoryToCategoryDTO).collect(Collectors.toList()));
	}

	public CategoryDTO createNewCategory(CategoryDTO categoryDTO) {
		log.debug("createNewCategory() called in CategoryService with categoryDTO: " + categoryDTO);
		Category category = categoryMapper.categoryDTOtoCategory(categoryDTO);
		Category savedCategory = categoryRepository.save(category);
		return categoryMapper.categoryToCategoryDTO(savedCategory);
	}

	public CategoryDTO saveCategoryByDTO(Long id, CategoryDTO categoryDTO) {
		log.debug("saveCategoryByDTO() called in CategoryService with categoryDTO: " + categoryDTO
				+ " and category id: " + id);
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if (optionalCategory.isEmpty()) {
			throw new ResourseNotFoundException("Category with id: " + id + " not found");
		}
		Category category = optionalCategory.get();
		category = categoryMapper.categoryDTOtoCategory(categoryDTO);
		category.setId(id);
		return categoryMapper.categoryToCategoryDTO(categoryRepository.save(category));
	}

	public CategoryDTO patchCategory(Long id, CategoryDTO categoryDTO) {
		log.debug("patchCategoryByDTO() called in CategoryService with categoryDTO: " + categoryDTO
				+ " and category id: " + id);
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
		log.debug("deleteById() called in CategoryService with category id: " + id);
		Category category = categoryRepository.getById(id);
		categoryRepository.delete(category);
	}

}
