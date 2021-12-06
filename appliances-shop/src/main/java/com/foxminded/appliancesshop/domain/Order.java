package com.foxminded.appliancesshop.domain;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Customer customer;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date data;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "order", orphanRemoval = true)
	private Set<Item> items = new HashSet<>();

	public Order(Long id, Set<Item> items) {
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

}
