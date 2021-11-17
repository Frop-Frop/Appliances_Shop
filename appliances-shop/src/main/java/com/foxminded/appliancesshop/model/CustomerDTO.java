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
public class CustomerDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private CartDTO cart;
	private OrderDTO order;
	private ItemListDTO deferreds;
	private AddressDTO address;
	@Enumerated(value = EnumType.STRING)
	private Role role;
	@Enumerated(value = EnumType.STRING)
	private Status status;

	public CustomerDTO(Long id, String firstName, String lastName, String email, String password, CartDTO cart,
			OrderDTO order, ItemListDTO deferreds, Role role, Status status) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.deferreds = deferreds;
		this.role = role;
		this.status = status;
		this.order = order;
	}

}
