package com.foxminded.appliancesshop.domain.security;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
public class User {

	private Long id;
	private String email;
	private String password;
	@Enumerated(value = EnumType.STRING)
	private Role role;
	@Enumerated(value = EnumType.STRING)
	private Status status;

}
