package com.foxminded.appliancesshop.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorDTO {

	private Long id;
	private String firstName;
	private String lastName;

	private String email;

	private String password;
	@Enumerated(value = EnumType.STRING)
	private Role role;
	@Enumerated(value = EnumType.STRING)
	private Status status;

}
