package com.foxminded.appliancesshop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Comparable<Item> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantity;
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@ManyToOne
	private Cart cart;

	@ManyToOne
	@JoinTable(name = "deferreds", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Customer customer;

	public Integer getCost() {
		return product.getPrice() * quantity;
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

	public Item(Integer quantity, Product product, Cart cart, Customer customer) {
		this.quantity = quantity;
		this.product = product;
		this.cart = cart;
		this.customer = customer;
	}

}
