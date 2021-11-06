package com.foxminded.appliancesshop.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.services.ResourseNotFoundException;

@Component
public class ItemMapper {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CustomerRepository customerRepository;

	public ItemDTO itemToItemDTO(Item item) {
		if (item == null) {
			return null;
		}
		Long customerId = null;
		if (item.getCustomer() != null) {
			customerId = item.getCustomer().getId();
		}
		ProductDTO productDTO = productMapper.productToProductDTO(item.getProduct());
		Long cartId = null;
		if (item.getCart() != null) {
			cartId = item.getCart().getId();
		}
		ItemDTO itemDTO = new ItemDTO(item.getId(), item.getQuantity(), productDTO, cartId, customerId, item.getCost());
		return itemDTO;
	}

	public Item itemDTOtoItem(ItemDTO itemDTO) {
		if (itemDTO == null) {
			return null;
		}
		Product product = productMapper.productDTOtoProduct(itemDTO.getProduct());
		Optional<Cart> cart = cartRepository.findById(itemDTO.getCartId());
		if (cart.isEmpty()) {
			throw new ResourseNotFoundException("Cart with id: " + itemDTO.getCartId() + "not found");
		}
		Optional<Customer> customer = customerRepository.findById(itemDTO.getCustomerId());
		if (customer.isEmpty()) {
			throw new ResourseNotFoundException("Customer with id: " + itemDTO.getCustomerId() + "not found");
		}
		Item item = new Item(itemDTO.getId(), itemDTO.getQuantity(), product, cart.get(), customer.get());
		return item;
	}
}
