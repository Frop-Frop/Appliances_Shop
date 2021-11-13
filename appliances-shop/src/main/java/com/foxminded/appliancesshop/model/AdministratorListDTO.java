package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdministratorListDTO {

	private List<AdministratorDTO> administrator;

	public AdministratorListDTO(List<AdministratorDTO> administratorList) {

		this.administrator = administratorList;

	}

}
