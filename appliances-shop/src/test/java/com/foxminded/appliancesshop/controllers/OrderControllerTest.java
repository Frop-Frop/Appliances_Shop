package com.foxminded.appliancesshop.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
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
import com.foxminded.appliancesshop.model.OrderDTO;
import com.foxminded.appliancesshop.model.OrderListDTO;
import com.foxminded.appliancesshop.services.OrderService;
import com.foxminded.appliancesshop.services.UserService;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

	@MockBean
	OrderService orderService;

	@Autowired
	OrderController orderController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean(name = "userDetailsService")
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "server_change")
	@Test
	void getAllOrdersTest() throws Exception {
		Date date1 = new Date();
		Date date2 = new Date();
		OrderDTO orderDTO = new OrderDTO(1L, 1L, null, date1);
		OrderDTO orderDTO1 = new OrderDTO(2L, 2L, null, date2);
		when(orderService.getAllOrders()).thenReturn(new OrderListDTO(List.of(orderDTO, orderDTO1)));
		mockMvc.perform(get("/appliances/orders/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void getOrderByIdTest() throws Exception {
		Date date1 = new Date();
		OrderDTO orderDTO = new OrderDTO(1L, 1L, null, date1);

		when(orderService.getOrderById(1L)).thenReturn(orderDTO);

		mockMvc.perform(get("/appliances/orders/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "act")

	@Test
	public void createNewOrderTest() throws Exception {
		Date date1 = new Date();

		OrderDTO orderDTO = new OrderDTO(null, 1L, null, date1);
		OrderDTO savedDTO = new OrderDTO(1L, 1L, null, date1);

		when(orderService.createNewOrder(orderDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(post("/appliances/orders/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "act")

	@Test
	public void pathOrderTest() throws Exception {
		Date date1 = new Date();

		OrderDTO orderDTO = new OrderDTO(1L, 1L, null, date1);
		OrderDTO savedDTO = new OrderDTO(1L, 1L, null, date1);

		when(orderService.createNewOrder(orderDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(patch("/appliances/orders/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

}
