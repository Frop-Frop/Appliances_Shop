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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Product related operations")
@RestController
@RequestMapping("appliances/products/")
public class ProductController {

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "Get list of all products")
	@GetMapping()
	public ResponseEntity<ProductListDTO> getAllProducts() {
		return new ResponseEntity<ProductListDTO>(productService.getAllProducts(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get product by id")
	@GetMapping("{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
		return new ResponseEntity<ProductDTO>(productService.getProductById(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Get product by name")
	@GetMapping("name/{name}")
	public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
		return new ResponseEntity<ProductDTO>(productService.getProductByName(name), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all products by brand name")
	@GetMapping("brand/{name}")
	public ResponseEntity<ProductListDTO> getAllProductsByBrand(@PathVariable String name) {
		return new ResponseEntity<ProductListDTO>(productService.getAllProductsByBrand(name), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all products in category by category id")
	@GetMapping("category/{id}")
	public ResponseEntity<ProductListDTO> getAllProductsInCategory(@PathVariable Long id) {
		return new ResponseEntity<ProductListDTO>(productService.getAllProductsInCategory(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all products in all sub categories by category id")
	@GetMapping("super_category/{id}")
	public ResponseEntity<ProductListDTO> getAllProductsInAllSubCategories(@PathVariable Long id) {
		return new ResponseEntity<ProductListDTO>(productService.getAllProductsInAllSubCategories(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Create new product by productDTO")
	@PostMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<ProductDTO> createNewProduct(@RequestBody ProductDTO productDTO) {
		return new ResponseEntity<ProductDTO>(productService.createNewProduct(productDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Update product by productDTO")
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
		return new ResponseEntity<ProductDTO>(productService.saveProductByDTO(id, productDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Patch product by productDTO")
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<ProductDTO> patchProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
		return new ResponseEntity<ProductDTO>(productService.patchProduct(id, productDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete product by id")
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		productService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
