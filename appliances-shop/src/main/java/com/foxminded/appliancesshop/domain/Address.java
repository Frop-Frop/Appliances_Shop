package com.foxminded.appliancesshop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Customer customer;
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
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((houseNumber == null) ? 0 : houseNumber.hashCode());
		return result;
	}

	public Address(Long id, String country, String region, String city, String street, String houseNumber) {
		this.id = id;
		this.country = country;
		this.region = region;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
	}

}
