package com.foxminded.appliancesshop.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.mappers.AdministratorMapper;
import com.foxminded.appliancesshop.model.AdministratorDTO;
import com.foxminded.appliancesshop.model.AdministratorListDTO;
import com.foxminded.appliancesshop.repositories.AdministratorRepository;

@Service
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private AdministratorMapper administratorMapper;

	public AdministratorListDTO getAllAdministrators() {
		return new AdministratorListDTO(administratorRepository.findAll().stream()
				.map(administratorMapper::administratorToAdministratorDTO).collect(Collectors.toList()));
	}

	public AdministratorDTO getAdministratorById(Long id) {
		return administratorRepository.findById(id).map(administratorMapper::administratorToAdministratorDTO)
				.orElseThrow(ResourseNotFoundException::new);
	}

	public AdministratorDTO createNewAdministrator(AdministratorDTO administratorDTO) {
		Administrator administrator = administratorMapper.administratorDTOtoAdministrator(administratorDTO);
		Administrator savedAdministrator = administratorRepository.save(administrator);
		return administratorMapper.administratorToAdministratorDTO(savedAdministrator);
	}

	public AdministratorDTO saveAdministratorByDTO(Long id, AdministratorDTO administratorDTO) {
		Optional<Administrator> optionalAdministrator = administratorRepository.findById(id);
		if (optionalAdministrator.isEmpty()) {
			throw new ResourseNotFoundException();
		}
		Administrator administrator = optionalAdministrator.get();
		administrator = administratorMapper.administratorDTOtoAdministrator(administratorDTO);
		administrator.setId(id);
		return administratorMapper.administratorToAdministratorDTO(administratorRepository.save(administrator));
	}

	public AdministratorDTO patchAdministrator(Long id, AdministratorDTO administratorDTO) {
		Administrator administrator = administratorRepository.getById(id);
		if (administrator == null) {
			throw new ResourseNotFoundException();
		}
		if (administrator.getFirstName() == null) {
			administrator.setFirstName(administratorDTO.getFirstName());
		}
		if (administrator.getLastName() == null) {
			administrator.setLastName(administratorDTO.getLastName());
		}
		if (administrator.getEmail() == null) {
			administrator.setEmail(administratorDTO.getEmail());
		}
		if (administrator.getPassword() == null) {
			administrator.setPassword(administratorDTO.getPassword());
		}
		if (administrator.getRole() == null) {
			administrator.setRole(administratorDTO.getRole());
		}
		if (administrator.getStatus() == null) {
			administrator.setStatus(administratorDTO.getStatus());
		}
		return administratorMapper.administratorToAdministratorDTO(administratorRepository.save(administrator));
	}

	public void deleteById(Long id) {
		Administrator administrator = administratorRepository.getById(id);
		administratorRepository.delete(administrator);
	}

}
