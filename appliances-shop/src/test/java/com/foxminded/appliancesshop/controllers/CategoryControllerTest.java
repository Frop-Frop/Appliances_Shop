package com.foxminded.appliancesshop.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.foxminded.appliancesshop.model.CategoryDTO;
import com.foxminded.appliancesshop.model.CategoryListDTO;
import com.foxminded.appliancesshop.services.CategoryService;
import com.foxminded.appliancesshop.services.UserService;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

	@MockBean
	CategoryService categoryService;

	@Autowired
	CategoryController categoryController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean(name = "userDetailsService")
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	void getAllCategoriesTest() throws Exception {
		CategoryDTO categoryDTO = new CategoryDTO(1L, "Category 1", null, null);
		CategoryDTO categoryDTO1 = new CategoryDTO(2L, "Category 2", null, null);
		when(categoryService.getAllCategories()).thenReturn(new CategoryListDTO(List.of(categoryDTO, categoryDTO1)));
		mockMvc.perform(get("/appliances/categories/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	void getAllSubCategoriesTest() throws Exception {
		CategoryDTO superCategory = new CategoryDTO(3L, "SuperCategory", null, null);
		CategoryDTO categoryDTO = new CategoryDTO(1L, "Category 1", null, superCategory);
		CategoryDTO categoryDTO1 = new CategoryDTO(2L, "Category 2", null, superCategory);
		when(categoryService.getSubCategories(3L)).thenReturn(new CategoryListDTO(List.of(categoryDTO, categoryDTO1)));
		mockMvc.perform(get("/appliances/categories/subcategories/3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void getCategoryByIdTest() throws Exception {

		CategoryDTO categoryDTO = new CategoryDTO(1L, "Category", null, null);

		when(categoryService.getCategoryById(1L)).thenReturn(categoryDTO);

		mockMvc.perform(get("/appliances/categories/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void getCategoryByNameTest() throws Exception {

		CategoryDTO categoryDTO = new CategoryDTO(1L, "Category", null, null);

		when(categoryService.getCategoryByName("Category")).thenReturn(categoryDTO);

		mockMvc.perform(get("/appliances/categories/name/Category").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void createNewCategoryTest() throws Exception {

		CategoryDTO categoryDTO = new CategoryDTO(null, "Category", null, null);
		CategoryDTO savedDTO = new CategoryDTO(1L, "Category", null, null);

		when(categoryService.createNewCategory(categoryDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(post("/appliances/categories/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void updateCategoryTest() throws Exception {

		CategoryDTO categoryDTO = new CategoryDTO(1L, null, null, null);
		CategoryDTO savedDTO = new CategoryDTO(1L, "Patched Category", null, null);

		when(categoryService.saveCategoryByDTO(1L, categoryDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);
		mockMvc.perform(put("/appliances/categories/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void pathCategoryTest() throws Exception {

		CategoryDTO categoryDTO = new CategoryDTO(1L, null, null, null);
		CategoryDTO savedDTO = new CategoryDTO(1L, "Patched Category", null, null);

		when(categoryService.createNewCategory(categoryDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);
		mockMvc.perform(post("/appliances/categories/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

}
