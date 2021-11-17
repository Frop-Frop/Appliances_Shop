package com.foxminded.appliancesshop.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Order;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.repositories.OrderRepository;

@Component
public class ItemMapper {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private OrderRepository orderRepository;

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
		Long orderId = null;
		if (item.getCart() != null) {
			orderId = item.getOrder().getId();
		}
		ItemDTO itemDTO = new ItemDTO(item.getId(), item.getQuantity(), productDTO, cartId, customerId, orderId,
				item.getCost());
		return itemDTO;
	}

	public Item itemDTOtoItem(ItemDTO itemDTO) {
		if (itemDTO == null) {
			return null;
		}
		Product product = productMapper.productDTOtoProduct(itemDTO.getProduct());
		Optional<Cart> optionalCart = cartRepository.findById(itemDTO.getCartId());
		Cart cart = null;
		if (optionalCart.isPresent()) {
			cart = optionalCart.get();
		}
		Optional<Order> optionalOrder = orderRepository.findById(itemDTO.getOrderId());
		Order order = null;
		if (optionalOrder.isPresent()) {
			order = optionalOrder.get();
		}
		Optional<Customer> optionalCustomer = customerRepository.findById(itemDTO.getCustomerId());
		Customer customer = null;
		if (optionalCustomer.isEmpty()) {
			customer = optionalCustomer.get();
		}
		Item item = new Item(itemDTO.getId(), itemDTO.getQuantity(), product, cart, order, customer);
		return item;
	}
}
