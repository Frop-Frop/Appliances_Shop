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

import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.model.ProductListDTO;
import com.foxminded.appliancesshop.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product controller", description = "Product related operations")
@RestController
@RequestMapping("appliances/products/")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Operation(summary = "Get list of all products")
	@GetMapping()
	public ResponseEntity<ProductListDTO> getAllProducts() {
		return new ResponseEntity<ProductListDTO>(productService.getAllProducts(), HttpStatus.OK);
	}

	@Operation(summary = "Get product by id")
	@GetMapping("{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
		return new ResponseEntity<ProductDTO>(productService.getProductById(id), HttpStatus.OK);
	}

	@Operation(summary = "Get product by name")
	@GetMapping("name/{name}")
	public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
		return new ResponseEntity<ProductDTO>(productService.getProductByName(name), HttpStatus.OK);
	}

	@Operation(summary = "Get all products by brand name")
	@GetMapping("brand/{name}")
	public ResponseEntity<ProductListDTO> getAllProductsByBrand(@PathVariable String name) {
		return new ResponseEntity<ProductListDTO>(productService.getAllProductsByBrand(name), HttpStatus.OK);
	}

	@Operation(summary = "Get all products in category by category id")
	@GetMapping("category/{id}")
	public ResponseEntity<ProductListDTO> getAllProductsInCategory(@PathVariable Long id) {
		return new ResponseEntity<ProductListDTO>(productService.getAllProductsInCategory(id), HttpStatus.OK);
	}

	@Operation(summary = "Get all products in all sub categories by category id")
	@GetMapping("super_category/{id}")
	public ResponseEntity<ProductListDTO> getAllProductsInAllSubCategories(@PathVariable Long id) {
		return new ResponseEntity<ProductListDTO>(productService.getAllProductsInAllSubCategories(id), HttpStatus.OK);
	}

	@Operation(summary = "Create new product by productDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PostMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<ProductDTO> createNewProduct(@RequestBody ProductDTO productDTO) {
		return new ResponseEntity<ProductDTO>(productService.createNewProduct(productDTO), HttpStatus.OK);
	}

	@Operation(summary = "Update product by productDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
		return new ResponseEntity<ProductDTO>(productService.saveProductByDTO(id, productDTO), HttpStatus.OK);
	}

	@Operation(summary = "Patch product by productDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<ProductDTO> patchProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
		return new ResponseEntity<ProductDTO>(productService.patchProduct(id, productDTO), HttpStatus.OK);
	}

	@Operation(summary = "Delete product by id", security = @SecurityRequirement(name = "basicAuth"))
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		productService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
