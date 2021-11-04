package com.foxminded.appliancesshop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Comparable<Product> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne
	private Category category;
	private Integer price;
	private String brand;
	private String description;
	@OneToMany
	@Transient
	private Item item;

	@Override
	public int compareTo(Product o) {
		return id.compareTo(o.getId());
	}

	public Product(String name, Category category) {
		this.name = name;
		this.category = category;
	}

}
