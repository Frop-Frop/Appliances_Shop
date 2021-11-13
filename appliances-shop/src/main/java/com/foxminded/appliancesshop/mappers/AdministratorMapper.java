package com.foxminded.appliancesshop.mappers;

import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.model.AdministratorDTO;

@Component
public class AdministratorMapper {

	public AdministratorDTO administratorToAdministratorDTO(Administrator administrator) {
		if (administrator == null) {
			return null;
		}
		AdministratorDTO administratorDTO = new AdministratorDTO(administrator.getId(), administrator.getFirstName(),
				administrator.getLastName(), administrator.getEmail(), administrator.getPassword(),
				administrator.getRole(), administrator.getStatus());
		return administratorDTO;
	}

	public Administrator administratorDTOtoAdministrator(AdministratorDTO administratorDTO) {
		if (administratorDTO == null) {
			return null;
		}
		Administrator administrator = new Administrator(administratorDTO.getId(), administratorDTO.getFirstName(),
				administratorDTO.getLastName(), administratorDTO.getEmail(), administratorDTO.getPassword(),
				administratorDTO.getRole(), administratorDTO.getStatus());
		return administrator;
	}

}
