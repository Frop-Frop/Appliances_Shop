package com.foxminded.appliancesshop.mappers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Order;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.model.OrderDTO;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.services.ResourseNotFoundException;

@Component
public class OrderMapper {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ItemMapper itemMapper;

	public OrderDTO orderToOrderDTO(Order order) {

		if (order == null) {
			return null;
		}
		OrderDTO orderDTO = new OrderDTO(order.getId(), order.getCustomer().getId(),
				new ItemListDTO(
						order.getItemsList().stream().map(itemMapper::itemToItemDTO).collect(Collectors.toList())),
				order.getData());
		return orderDTO;
	}

	public Order orderDTOtoOrder(OrderDTO orderDTO) {

		if (orderDTO == null) {
			return null;
		}
		Optional<Customer> customer = customerRepository.findById(orderDTO.getCustomerId());
		if (customer.isEmpty()) {
			throw new ResourseNotFoundException("Customer with id: " + orderDTO.getCustomerId() + " not found");
		}
		Set<Item> items = new HashSet<>();
		orderDTO.getItems().getItems().stream().map(itemMapper::itemDTOtoItem).forEach(items::add);
		Order order = new Order(orderDTO.getId(), customer.get(), orderDTO.getData(), items);
		return order;
	}

}
