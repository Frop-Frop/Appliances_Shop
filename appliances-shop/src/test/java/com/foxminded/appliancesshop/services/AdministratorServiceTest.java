package com.foxminded.appliancesshop.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.mappers.AdministratorMapper;
import com.foxminded.appliancesshop.model.AdministratorDTO;
import com.foxminded.appliancesshop.repositories.AdministratorRepository;

@SpringBootTest(classes = { AdministratorService.class, AdministratorMapper.class })
class AdministratorServiceTest {

	@Autowired
	private AdministratorService administratorService;

	@MockBean
	private AdministratorMapper administratorMapper;

	@MockBean
	private AdministratorRepository administratorRepository;

	@Test
	void getAllAdministratorsTest() {
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		administrator.setFirstName("John");
		Administrator administrator1 = new Administrator();
		administrator1.setId(2L);
		administrator1.setFirstName("Jane");
		AdministratorDTO administratorDTO = new AdministratorDTO();
		administratorDTO.setId(1L);
		administratorDTO.setFirstName("John");
		AdministratorDTO administratorDTO1 = new AdministratorDTO();
		administratorDTO1.setId(2L);
		administratorDTO1.setFirstName("Jane");
		when(administratorMapper.administratorToAdministratorDTO(administrator)).thenReturn(administratorDTO);
		when(administratorMapper.administratorToAdministratorDTO(administrator1)).thenReturn(administratorDTO1);
		when(administratorRepository.findAll()).thenReturn(Arrays.asList(administrator, administrator1));
		administratorService.getAllAdministrators();
		verify(administratorRepository, times(1)).findAll();
		verify(administratorMapper, times(1)).administratorToAdministratorDTO(administrator);
		verify(administratorMapper, times(1)).administratorToAdministratorDTO(administrator1);
	}

	@Test
	void getAdministratorByIdTest() {
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		administrator.setFirstName("John");
		Optional<Administrator> optionalAdministrator = Optional.of(administrator);
		AdministratorDTO administratorDTO = new AdministratorDTO();
		administratorDTO.setId(1L);
		administratorDTO.setFirstName("John");
		when(administratorRepository.findById(1L)).thenReturn(optionalAdministrator);
		when(administratorMapper.administratorToAdministratorDTO(administrator)).thenReturn(administratorDTO);
		administratorService.getAdministratorById(1L);
		verify(administratorRepository, times(1)).findById(1L);
	}

	@Test
	void createNewAdministratorTest() {
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		administrator.setFirstName("John");
		Administrator administratorWithoutId = new Administrator();
		administratorWithoutId.setFirstName("John");
		AdministratorDTO administratorDTO = new AdministratorDTO();
		administratorDTO.setFirstName("John");
		when(administratorMapper.administratorDTOtoAdministrator(administratorDTO)).thenReturn(administratorWithoutId);
		when(administratorRepository.save(administratorWithoutId)).thenReturn(administrator);
		AdministratorDTO customerDTO1 = new AdministratorDTO();
		customerDTO1.setId(1L);
		customerDTO1.setFirstName("John");
		when(administratorMapper.administratorToAdministratorDTO(administrator)).thenReturn(customerDTO1);
		administratorService.createNewAdministrator(administratorDTO);
		verify(administratorRepository, times(1)).save(administratorWithoutId);
		verify(administratorMapper, times(1)).administratorDTOtoAdministrator(administratorDTO);
		verify(administratorMapper, times(1)).administratorToAdministratorDTO(administrator);
	}

	@Test
	void saveAdministratorByDTOTest() {
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		administrator.setFirstName("John");
		Optional<Administrator> optionalAdministrator = Optional.of(administrator);
		AdministratorDTO administratorDTO = new AdministratorDTO();
		administratorDTO.setFirstName("John");
		AdministratorDTO administratorDTO1 = new AdministratorDTO();
		administratorDTO1.setFirstName("John");
		administratorDTO1.setId(1l);
		Administrator administratorWithoutId = new Administrator();
		administratorWithoutId.setFirstName("John");
		when(administratorRepository.findById(1L)).thenReturn(optionalAdministrator);
		when(administratorMapper.administratorDTOtoAdministrator(administratorDTO)).thenReturn(administratorWithoutId);
		when(administratorRepository.save(administratorWithoutId)).thenReturn(administrator);
		when(administratorMapper.administratorToAdministratorDTO(administrator)).thenReturn(administratorDTO1);
		administratorService.saveAdministratorByDTO(1L, administratorDTO);
		verify(administratorRepository, times(1)).findById(1L);
		verify(administratorRepository, times(1)).save(administratorWithoutId);
		verify(administratorMapper, times(1)).administratorDTOtoAdministrator(administratorDTO);
		verify(administratorMapper, times(1)).administratorToAdministratorDTO(administrator);
	}

	@Test
	void patchAdministrator() {
		Administrator administrator = new Administrator();
		Administrator administratorWithId = new Administrator();
		administrator.setFirstName("John");
		administrator.setLastName("Johns");
		administrator.setEmail("email");
		administrator.setPassword("password");
		administrator.setId(1L);
		when(administratorRepository.getById(1L)).thenReturn(administrator);
		AdministratorDTO administratorDTO = new AdministratorDTO();
		administratorDTO.setFirstName("John");
		administratorDTO.setLastName("Johns");
		administratorDTO.setEmail("email");
		administratorDTO.setPassword("password");
		administratorDTO.setId(1L);
		when(administratorMapper.administratorToAdministratorDTO(administrator)).thenReturn(administratorDTO);
		when(administratorRepository.save(administrator)).thenReturn(administratorWithId);
		administratorService.patchAdministrator(1L, administratorDTO);
		verify(administratorRepository, times(1)).getById(1L);
		verify(administratorRepository, times(1)).save(administrator);
	}

}
