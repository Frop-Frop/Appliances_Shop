package com.foxminded.appliancesshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Department related operations")
@RestController
@RequestMapping("appliances/categories/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@ApiOperation(value = "Get list of all categories")
	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories() {
		return new ResponseEntity<CategoryListDTO>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of all subcategories")
	@GetMapping("subcategories/{id}")
	public ResponseEntity<CategoryListDTO> getAllSubCategories(@PathVariable Long id) {
		return new ResponseEntity<CategoryListDTO>(categoryService.getSubCategories(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Get category by id")
	@GetMapping("{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
		return new ResponseEntity<CategoryDTO>(categoryService.getCategoryById(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Get category by name")
	@GetMapping("name/{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
		return new ResponseEntity<CategoryDTO>(categoryService.getCategoryByName(name), HttpStatus.OK);
	}

	@ApiOperation(value = "Create new category by categoryDTO")
	@PostMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<CategoryDTO> createNewCategory(@RequestBody CategoryDTO categoryDTO) {
		return new ResponseEntity<CategoryDTO>(categoryService.createNewCategory(categoryDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Update new category by categoryDTO")
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
		return new ResponseEntity<CategoryDTO>(categoryService.saveCategoryByDTO(id, categoryDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Patch new category by categoryDTO")
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<CategoryDTO> patchCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
		return new ResponseEntity<CategoryDTO>(categoryService.patchCategory(id, categoryDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete category by id")
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
