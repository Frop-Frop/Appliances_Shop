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
import com.foxminded.appliancesshop.model.AdministratorDTO;
import com.foxminded.appliancesshop.model.AdministratorListDTO;
import com.foxminded.appliancesshop.services.AdministratorService;
import com.foxminded.appliancesshop.services.UserService;

@WebMvcTest(AdministratorController.class)
class AdministratorControllerTest {

	@MockBean
	AdministratorService administratorService;

	@Autowired
	AdministratorController administratorController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean(name = "userDetailsService")
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	void getAllAdministratorsTest() throws Exception {

		AdministratorDTO administratorDTO = new AdministratorDTO(1L, "John", "Johns", "email", "password",
				Role.ADMINISTRATOR, Status.ACTIVE);
		AdministratorDTO administratorDTO1 = new AdministratorDTO(2L, "Jane", "Johnson", "email", "password",
				Role.ADMINISTRATOR, Status.ACTIVE);
		when(administratorService.getAllAdministrators())
				.thenReturn(new AdministratorListDTO(List.of(administratorDTO, administratorDTO1)));
		mockMvc.perform(get("/appliances/administrators/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@WithMockUser(username = "bfarfalameev0@gizmodo.com", authorities = "act")
	@Test
	void getAllAdministratorsShouldThrowAccessForbidden() throws Exception {

		AdministratorDTO administratorDTO = new AdministratorDTO(1L, "John", "Johns", "email", "password",
				Role.ADMINISTRATOR, Status.ACTIVE);
		AdministratorDTO administratorDTO1 = new AdministratorDTO(2L, "Jane", "Johnson", "email", "password",
				Role.ADMINISTRATOR, Status.ACTIVE);
		when(administratorService.getAllAdministrators())
				.thenReturn(new AdministratorListDTO(List.of(administratorDTO, administratorDTO1)));
		mockMvc.perform(get("/appliances/administrators/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(403));

	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void testShowById() throws Exception {

		AdministratorDTO administratorDTO = new AdministratorDTO(1L, "John", "Johns", "email", "password",
				Role.ADMINISTRATOR, Status.ACTIVE);

		when(administratorService.getAdministratorById(1L)).thenReturn(administratorDTO);

		mockMvc.perform(get("/appliances/administrators/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void createNewAdministratorTest() throws Exception {

		AdministratorDTO administratorDTO = new AdministratorDTO(null, "John", "Johns", "email", "password",
				Role.ADMINISTRATOR, Status.ACTIVE);
		AdministratorDTO savedDTO = new AdministratorDTO(3L, "John", "Johns", "email", "password is not displayed",
				Role.ADMINISTRATOR, Status.ACTIVE);

		when(administratorService.createNewAdministrator(administratorDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(post("/appliances/administrators/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void updateAdministratorTest() throws Exception {

		AdministratorDTO administratorDTO = new AdministratorDTO(null, "John", "Johns", "email", "password",
				Role.ADMINISTRATOR, Status.ACTIVE);
		AdministratorDTO savedDTO = new AdministratorDTO(3L, "John", "Johns", "email", "password is not displayed",
				Role.ADMINISTRATOR, Status.ACTIVE);

		when(administratorService.saveAdministratorByDTO(3L, administratorDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(put("/appliances/administrators/3").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "tminchindon0@mozilla.org", authorities = "server_change")
	@Test
	public void putchAdministratorTest() throws Exception {

		AdministratorDTO administratorDTO = new AdministratorDTO(null, "John", "Johns", "email", "password",
				Role.ADMINISTRATOR, Status.ACTIVE);
		AdministratorDTO savedDTO = new AdministratorDTO(3L, "John", "Johns", "email", "password is not displayed",
				Role.ADMINISTRATOR, Status.ACTIVE);

		when(administratorService.patchAdministrator(3L, administratorDTO)).thenReturn(savedDTO);
		String content = objectMapper.writeValueAsString(savedDTO);

		mockMvc.perform(patch("/appliances/administrators/3").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

}
