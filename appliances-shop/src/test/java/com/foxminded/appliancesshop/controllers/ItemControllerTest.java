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
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.services.ItemService;
import com.foxminded.appliancesshop.services.UserService;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

	@MockBean
	ItemService itemService;

	@Autowired
	ItemController itemController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean(name = "userDetailsService")
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	void getAllItemsInCartTest() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		ItemDTO itemDTO = new ItemDTO(1L, 10, productDTO, 1l, null, null, 100);
		ItemDTO itemDTO1 = new ItemDTO(2L, 15, productDTO, 1l, null, null, 150);
		when(itemService.getAllItemsInCart(1L)).thenReturn(new ItemListDTO(List.of(itemDTO, itemDTO1)));
		mockMvc.perform(get("/appliances/items/cart/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	void getAllItemsInOrderTest() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		ItemDTO itemDTO = new ItemDTO(1L, 10, productDTO, null, 1l, null, 100);
		ItemDTO itemDTO1 = new ItemDTO(2L, 15, productDTO, null, 1L, null, 150);
		when(itemService.getAllItemsInOrder(1L)).thenReturn(new ItemListDTO(List.of(itemDTO, itemDTO1)));
		mockMvc.perform(get("/appliances/items/order/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void getItemByIdTest() throws Exception {
		ProductDTO productDTO = new ProductDTO();

		ItemDTO itemDTO = new ItemDTO(1L, 10, productDTO, 1l, null, null, 100);

		when(itemService.getItemById(1L)).thenReturn(itemDTO);

		mockMvc.perform(get("/appliances/items/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	void getCustomerDeferredsTest() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		ItemDTO itemDTO = new ItemDTO(1L, 10, productDTO, null, null, 1L, 100);
		ItemDTO itemDTO1 = new ItemDTO(2L, 15, productDTO, null, null, 1L, 150);
		when(itemService.getCustomerDeferreds(1L)).thenReturn(new ItemListDTO(List.of(itemDTO, itemDTO1)));
		mockMvc.perform(get("/appliances/items/customer/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "act")
	@Test
	public void createNewItemTest() throws Exception {
		ProductDTO productDTO = new ProductDTO();

		ItemDTO itemDTO = new ItemDTO(null, 10, productDTO, null, null, 1L, 100);
		ItemDTO savedDTO = new ItemDTO(1L, 10, productDTO, null, null, 1L, 100);

		when(itemService.createNewItem(itemDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(post("/appliances/items/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "act")
	@Test
	public void updateItemTest() throws Exception {
		ProductDTO productDTO = new ProductDTO();

		ItemDTO itemDTO = new ItemDTO(null, 10, productDTO, null, null, 1L, 100);
		ItemDTO savedDTO = new ItemDTO(1L, 10, productDTO, null, null, 1L, 100);

		when(itemService.saveItemByDTO(1L, itemDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(put("/appliances/items/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "act")
	@Test
	public void pathItemTest() throws Exception {

		ProductDTO productDTO = new ProductDTO();

		ItemDTO itemDTO = new ItemDTO(null, 10, productDTO, null, null, 1L, 100);
		ItemDTO savedDTO = new ItemDTO(1L, 10, productDTO, null, null, 1L, 100);

		when(itemService.patchItem(1L, itemDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(patch("/appliances/items/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

}
