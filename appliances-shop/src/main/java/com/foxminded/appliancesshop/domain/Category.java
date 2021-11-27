package com.foxminded.appliancesshop.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "category")
	private Set<Product> products = new HashSet<>();
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "super_category_id", referencedColumnName = "id")
	private Category superCategory;

	public Category(String name) {
		this.name = name;
	}

	public SortedSet<Product> getProducts() {
		SortedSet<Product> result = new TreeSet<>();
		products.stream().forEach(result::add);
		return result;
	}

	public List<Product> getProductsList() {
		List<Product> productsList = new ArrayList<>();
		products.forEach(productsList::add);
		return productsList;
	}

	public Category(String name, Category superCategory) {
		this.name = name;
		this.superCategory = superCategory;
	}

	public void addProductToCategory(Product product) {
		products.add(product);
	}

}
