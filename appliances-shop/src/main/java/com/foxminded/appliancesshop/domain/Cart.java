package com.foxminded.appliancesshop.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @PrimaryKeyJoinColumn
	@OneToOne // (mappedBy = "cart")
	private Customer customer;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "cart")
	private Set<Item> items = new HashSet<>();

	public SortedSet<Item> getItems() {
		SortedSet<Item> result = new TreeSet<>();
		items.stream().forEach(result::add);
		return result;
	}

	public List<Item> getItemsList() {
		List<Item> items = new ArrayList<>();
		items.forEach(items::add);
		return items;
	}

	public int getSum() {
		Iterator<Item> iterator = items.iterator();
		int result = 0;
		while (iterator.hasNext()) {
			result += iterator.next().getCost();
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.intValue();
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((items.isEmpty()) ? 0 : items.size());
		return result;
	}

}
