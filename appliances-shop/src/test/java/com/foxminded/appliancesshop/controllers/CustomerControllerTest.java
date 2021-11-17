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
import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;
import com.foxminded.appliancesshop.mappers.CustomerMapper;
import com.foxminded.appliancesshop.model.CustomerDTO;
import com.foxminded.appliancesshop.model.CustomerListDTO;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.services.CustomerService;
import com.foxminded.appliancesshop.services.ResourseNotFoundException;
import com.foxminded.appliancesshop.services.UserService;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@MockBean
	CustomerService customerService;

	@MockBean
	CustomerMapper customerMapper;

	@MockBean
	CustomerRepository customerRepository;

	@Autowired
	CustomerController customerController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean(name = "userDetailsService")
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	void getAllCustomersTest() throws Exception {

		CustomerDTO customerDTO = new CustomerDTO(1L, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);
		CustomerDTO customerDTO1 = new CustomerDTO(2L, "Jane", "Johnson", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);
		when(customerService.getAllCustomers()).thenReturn(new CustomerListDTO(List.of(customerDTO, customerDTO1)));
		mockMvc.perform(get("/appliances/customers/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	void getAllCustomersShouldThrowAccessForbidden() throws Exception {

		CustomerDTO customerDTO = new CustomerDTO(1L, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);
		CustomerDTO customerDTO1 = new CustomerDTO(2L, "Jane", "Johnson", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);
		when(customerService.getAllCustomers()).thenReturn(new CustomerListDTO(List.of(customerDTO, customerDTO1)));
		mockMvc.perform(get("/appliances/customers/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(403));

	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void getCustomerByIdTest() throws Exception {

		CustomerDTO customerDTO = new CustomerDTO(1L, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);

		when(customerService.getCustomerById(1L)).thenReturn(customerDTO);

		mockMvc.perform(get("/appliances/customers/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void createNewCustomerTest() throws Exception {

		CustomerDTO customerDTO = new CustomerDTO(null, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);

		CustomerDTO savedDTO = new CustomerDTO(1L, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);
		String content = objectMapper.writeValueAsString(savedDTO);

		when(customerService.createNewCustomer(customerDTO)).thenReturn(savedDTO);
		mockMvc.perform(post("/appliances/customers/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void updateCustomerTest() throws Exception {

		CustomerDTO customerDTO = new CustomerDTO(null, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);

		CustomerDTO savedDTO = new CustomerDTO(1L, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);
		String content = objectMapper.writeValueAsString(savedDTO);

		when(customerService.saveCustomerByDTO(1L, customerDTO)).thenReturn(savedDTO);
		mockMvc.perform(put("/appliances/customers/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void pathCustomerTest() throws Exception {

		CustomerDTO customerDTO = new CustomerDTO(null, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);

		CustomerDTO savedDTO = new CustomerDTO(1L, "John", "Johns", "email", "password", null, null, null,
				Role.CUSTOMER, Status.ACTIVE);
		String content = objectMapper.writeValueAsString(savedDTO);

		when(customerService.patchCustomer(3L, customerDTO)).thenReturn(savedDTO);
		mockMvc.perform(patch("/appliances/customers/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	public void testGetCustomerResourseNotFoundExceptionException() throws Exception {

		when(customerService.getCustomerById(1L)).thenThrow(ResourseNotFoundException.class);

		mockMvc.perform(get("/university/students/show/1")).andExpect(status().is(404));
	}

}
