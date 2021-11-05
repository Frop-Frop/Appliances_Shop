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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.intValue();
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((houseNumber == null) ? 0 : houseNumber.hashCode());
		return result;
	}

	public AddressDTO(Long id, String country, String region, String city, String street, String houseNumber) {
		this.id = id;
		this.country = country;
		this.region = region;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
	}

}
