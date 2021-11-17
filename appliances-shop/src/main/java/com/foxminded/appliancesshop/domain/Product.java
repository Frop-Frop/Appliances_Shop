package com.foxminded.appliancesshop.domain;

import java.util.Objects;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(brand, other.brand) && Objects.equals(category.getId(), other.category.getId())
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(item, other.item) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price);
	}

}
