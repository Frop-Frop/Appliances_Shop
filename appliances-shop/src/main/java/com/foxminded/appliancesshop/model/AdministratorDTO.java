package com.foxminded.appliancesshop.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorDTO {

	private Long id;
	@ApiModelProperty(required = true)
	private String firstName;
	@ApiModelProperty(required = true)
	private String lastName;
	@ApiModelProperty(required = true)
	private String email;
	@ApiModelProperty(required = true)
	private String password;
	@Enumerated(value = EnumType.STRING)
	private Role role;
	@Enumerated(value = EnumType.STRING)
	private Status status;

}
