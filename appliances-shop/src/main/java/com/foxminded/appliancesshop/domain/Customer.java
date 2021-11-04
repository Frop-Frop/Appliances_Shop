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
import javax.persistence.JoinTable;
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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	@OneToOne
	private Cart cart;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "deferreds", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
	private Set<Item> deferreds = new HashSet<>();
	@OneToOne
	private Address address;

	public void addItemToDeferreds(Item item) {
		deferreds.add(item);
	}

	public SortedSet<Item> getDeferreds() {
		SortedSet<Item> result = new TreeSet<>();
		deferreds.stream().forEach(result::add);
		return result;
	}

	public List<Item> getDeferredsList() {
		List<Item> deferredsList = new ArrayList<>();
		deferreds.forEach(deferreds::add);
		return deferredsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + id);
		result = prime * result + ((cart == null) ? 0 : cart.getId().intValue());
		result = prime * result + ((address == null) ? 0 : address.getId().intValue());
		result = prime * result + ((deferreds.isEmpty()) ? 0 : deferreds.size());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

}