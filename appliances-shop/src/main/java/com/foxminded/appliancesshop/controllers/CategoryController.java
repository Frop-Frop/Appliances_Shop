package com.foxminded.appliancesshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.appliancesshop.model.CategoryDTO;
import com.foxminded.appliancesshop.model.CategoryListDTO;
import com.foxminded.appliancesshop.services.CategoryService;

@RestController
@RequestMapping("appliances/categories/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories() {
		return new ResponseEntity<CategoryListDTO>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	@GetMapping("subcategories/{id}")
	public ResponseEntity<CategoryListDTO> getAllSubCategories(@PathVariable Long id) {
		return new ResponseEntity<CategoryListDTO>(categoryService.getSubCategories(id), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
		return new ResponseEntity<CategoryDTO>(categoryService.getCategoryById(id), HttpStatus.OK);
	}

	@GetMapping("name/{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
		return new ResponseEntity<CategoryDTO>(categoryService.getCategoryByName(name), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> createNewCategory(@RequestBody CategoryDTO categoryDTO) {
		return new ResponseEntity<CategoryDTO>(categoryService.createNewCategory(categoryDTO), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
		return new ResponseEntity<CategoryDTO>(categoryService.saveCategoryByDTO(id, categoryDTO), HttpStatus.OK);
	}

	@PatchMapping("{id}")
	public ResponseEntity<CategoryDTO> patchCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
		return new ResponseEntity<CategoryDTO>(categoryService.patchCategory(id, categoryDTO), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
