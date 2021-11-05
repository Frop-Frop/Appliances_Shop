package com.foxminded.appliancesshop.model;

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
	private ItemListDTO deferreds;
	private AddressDTO address;

	public CustomerDTO(Long id, String firstName, String lastName, String email, String password, CartDTO cart,
			ItemListDTO deferreds) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.deferreds = deferreds;
	}

}
