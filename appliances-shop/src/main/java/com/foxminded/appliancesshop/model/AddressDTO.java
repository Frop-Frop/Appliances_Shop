package com.foxminded.appliancesshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

	private Long id;

	private Long customerId;

	private String country;

	private String region;

	private String city;

	private String street;
	private String houseNumber;

}
