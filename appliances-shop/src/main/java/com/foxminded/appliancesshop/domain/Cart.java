package com.foxminded.appliancesshop.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "carts")
public class Cart {

	@Id
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@MapsId
	private Customer customer;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "cart", orphanRemoval = true)
	private Set<Item> items = new HashSet<>();

	public Cart(Long id, Set<Item> items) {
		this.id = id;
		this.items = items;
	}

	public SortedSet<Item> getItems() {
		SortedSet<Item> result = new TreeSet<>();
		items.stream().forEach(result::add);
		return result;
	}

	public List<Item> getItemsList() {
		List<Item> itemsList = new ArrayList<>();
		items.forEach(itemsList::add);
		return itemsList;
	}

	public int getSum() {
		Iterator<Item> iterator = items.iterator();
		int result = 0;
		while (iterator.hasNext()) {
			result += iterator.next().getCost();
		}
		return result;
	}

	public void addItem(Item item) {
		items.add(item);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.intValue();
		result = prime * result + ((customer == null) ? 0 : customer.getId().intValue());
		result = prime * result + ((items == null) ? 0 : items.size());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return Objects.equals(id, other.id) && Objects.equals(customer.getId(), other.customer.getId())
				&& Objects.equals(items, other.items);
	}

}
