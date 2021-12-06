package com.foxminded.appliancesshop.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product implements Comparable<Product> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne(cascade = CascadeType.MERGE)
	private Category category;
	private Integer price;
	private String brand;
	private String description;
	@OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
	@Transient
	private Item item;
	private int unitsLeftInWarehouse;

	@Override
	public int compareTo(Product o) {
		return id.compareTo(o.getId());
	}

	public Product(String name, Category category) {
		this.name = name;
		this.category = category;
	}

	public Product(Long id, String name, Category category, Integer price, String brand, String description,
			int unitsLeftInWarehouse) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.brand = brand;
		this.description = description;
		this.unitsLeftInWarehouse = unitsLeftInWarehouse;
	}

}
