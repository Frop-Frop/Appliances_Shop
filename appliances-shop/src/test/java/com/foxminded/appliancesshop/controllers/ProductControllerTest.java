package com.foxminded.appliancesshop.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.model.ProductListDTO;
import com.foxminded.appliancesshop.services.ProductService;
import com.foxminded.appliancesshop.services.UserService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@MockBean
	ProductService productService;

	@Autowired
	ProductController productController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean(name = "userDetailsService")
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getAllProductsTest() throws Exception {
		ProductDTO productDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);
		ProductDTO productDTO1 = new ProductDTO(1L, "Product1", 1l, 150, "Brand", "Description1", 20);
		when(productService.getAllProducts()).thenReturn(new ProductListDTO(List.of(productDTO, productDTO1)));
		mockMvc.perform(get("/appliances/products/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getProductByIdTest() throws Exception {

		ProductDTO productDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);

		when(productService.getProductById(1L)).thenReturn(productDTO);

		mockMvc.perform(get("/appliances/products/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getProductByNameTest() throws Exception {

		ProductDTO productDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);

		when(productService.getProductByName("Product")).thenReturn(productDTO);

		mockMvc.perform(get("/appliances/products/name/Product").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getAllProductsByBrandTest() throws Exception {
		ProductDTO productDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);
		ProductDTO productDTO1 = new ProductDTO(1L, "Product1", 1l, 150, "Brand", "Description1", 20);
		when(productService.getAllProductsByBrand("Brand"))
				.thenReturn(new ProductListDTO(List.of(productDTO, productDTO1)));
		mockMvc.perform(get("/appliances/products/brand/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getAllProductsInCategoryTest() throws Exception {
		ProductDTO productDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);
		ProductDTO productDTO1 = new ProductDTO(1L, "Product1", 1l, 150, "Brand", "Description1", 20);
		when(productService.getAllProductsInCategory(1L))
				.thenReturn(new ProductListDTO(List.of(productDTO, productDTO1)));
		mockMvc.perform(get("/appliances/products/category/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getAllProductsInAllSubCategoriesTest() throws Exception {
		ProductDTO productDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);
		ProductDTO productDTO1 = new ProductDTO(1L, "Product1", 1l, 150, "Brand", "Description1", 20);
		when(productService.getAllProductsInAllSubCategories(1L))
				.thenReturn(new ProductListDTO(List.of(productDTO, productDTO1)));
		mockMvc.perform(get("/appliances/products/super_category/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void createNewItemTest() throws Exception {

		ProductDTO productDTO = new ProductDTO(null, "Product", 1l, 100, "Brand", "Description", 17);
		ProductDTO savedDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);

		when(productService.createNewProduct(productDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(post("/appliances/products/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void updateItemTest() throws Exception {

		ProductDTO productDTO = new ProductDTO(null, "Product", 1l, 100, "Brand", "Description", 17);
		ProductDTO savedDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);

		when(productService.saveProductByDTO(1L, productDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(put("/appliances/products/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void pathItemTest() throws Exception {

		ProductDTO productDTO = new ProductDTO(null, "Product", 1l, 100, "Brand", "Description", 17);
		ProductDTO savedDTO = new ProductDTO(1L, "Product", 1l, 100, "Brand", "Description", 17);

		when(productService.patchProduct(1L, productDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(patch("/appliances/products/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

}
