package com.foxminded.appliancesshop.domain;

import javax.persistence.CascadeType;
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

	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
	private Customer customer;
	private String country;
	private String region;
	private String city;
	private String street;
	private String houseNumber;

	public Address(Long id, String country, String region, String city, String street, String houseNumber) {
		this.id = id;
		this.country = country;
		this.region = region;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
	}

}
