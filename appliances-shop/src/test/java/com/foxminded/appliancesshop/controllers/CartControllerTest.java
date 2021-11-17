package com.foxminded.appliancesshop.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.services.CartService;
import com.foxminded.appliancesshop.services.UserService;

@WebMvcTest(CartController.class)
class CartControllerTest {

	@MockBean
	CartService cartService;

	@Autowired
	CartController cartController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean(name = "userDetailsService")
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void testShowById() throws Exception {

		CartDTO cartDTO = new CartDTO(1L, 1L, null);

		when(cartService.getCartById(1L)).thenReturn(cartDTO);

		mockMvc.perform(get("/appliances/carts/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void testUpdateCart() throws Exception {
		CartDTO cartDTO = new CartDTO(1L, 1L, null);

		when(cartService.saveCartByDTO(1L, cartDTO)).thenReturn(cartDTO);
		String content = objectMapper.writeValueAsString(cartDTO);

		mockMvc.perform(put("/appliances/carts/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());

	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void testPatchCart() throws Exception {
		CartDTO cartDTO = new CartDTO(1L, 1L, null);

		when(cartService.patchCart(1L, cartDTO)).thenReturn(cartDTO);
		String content = objectMapper.writeValueAsString(cartDTO);

		mockMvc.perform(patch("/appliances/carts/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());

	}

}
