package com.foxminded.appliancesshop.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.foxminded.appliancesshop.domain.security.Role;
import com.foxminded.appliancesshop.domain.security.Status;
import com.foxminded.appliancesshop.domain.security.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
	private Cart cart;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
	private Order order;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "customer", orphanRemoval = true)
	private Set<Item> deferreds = new HashSet<>();
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
	private Address address;

	@Enumerated(value = EnumType.STRING)
	private Role role;
	@Enumerated(value = EnumType.STRING)
	private Status status;

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
		deferreds.forEach(deferredsList::add);
		return deferredsList;
	}

	public Customer(Long id) {
		this.id = id;
	}

	public Customer(Long id, String firstName, String lastName, String email, String password, Cart cart, Order order,
			Set<Item> deferreds, Role role, Status status) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.cart = cart;
		this.deferreds = deferreds;
		this.role = role;
		this.status = status;
		this.order = order;
	}

}
