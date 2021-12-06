package com.foxminded.appliancesshop.domain;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item implements Comparable<Item> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantity;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Cart cart;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Order order;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Customer customer;

	public Integer getCost() {
		return product.getPrice() * quantity;
	}

	public Item(Integer quantity, Product product, Cart cart, Customer customer) {
		this.quantity = quantity;
		this.product = product;
		this.cart = cart;
		this.customer = customer;
	}

	@Override
	public int compareTo(Item o) {
		return id.compareTo(o.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.intValue();
		result = prime * result + ((customer == null) ? 0 : customer.getId().intValue());
		result = prime * result + ((cart == null) ? 0 : cart.getId().intValue());
		result = prime * result + ((product == null) ? 0 : product.getId().intValue());
		result = prime * result + ((quantity == null) ? 0 : quantity);
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
		Item other = (Item) obj;
		return Objects.equals(id, other.id) && Objects.equals(quantity, other.quantity)
				&& Objects.equals(product, other.product) && Objects.equals(cart.getId(), other.cart.getId())
				&& Objects.equals(order, other.order) && Objects.equals(customer, other.customer);
	}

}
