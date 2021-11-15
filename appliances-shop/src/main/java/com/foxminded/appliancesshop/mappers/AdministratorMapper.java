package com.foxminded.appliancesshop.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.model.AdministratorDTO;

@Component
public class AdministratorMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AdministratorDTO administratorToAdministratorDTO(Administrator administrator) {
		if (administrator == null) {
			return null;
		}
		AdministratorDTO administratorDTO = new AdministratorDTO(administrator.getId(), administrator.getFirstName(),
				administrator.getLastName(), administrator.getEmail(), "password is not displayed",
				administrator.getRole(), administrator.getStatus());
		return administratorDTO;
	}

	public Administrator administratorDTOtoAdministrator(AdministratorDTO administratorDTO) {
		if (administratorDTO == null) {
			return null;
		}
		Administrator administrator = new Administrator(administratorDTO.getId(), administratorDTO.getFirstName(),
				administratorDTO.getLastName(), administratorDTO.getEmail(),
				passwordEncoder.encode(administratorDTO.getPassword()), administratorDTO.getRole(),
				administratorDTO.getStatus());
		return administrator;
	}

}
