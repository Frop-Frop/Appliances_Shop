package com.foxminded.appliancesshop.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
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
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
	@MapsId
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
