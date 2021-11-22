package com.foxminded.appliancesshop.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Order;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.mappers.OrderMapper;
import com.foxminded.appliancesshop.model.OrderDTO;
import com.foxminded.appliancesshop.model.OrderListDTO;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.repositories.OrderRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemMapper itemMapper;

	private static final Logger log = Logger.getLogger(OrderService.class.getName());

	public OrderListDTO getAllOrders() {
		log.debug("getAllOrders() called in OrdersService");
		return new OrderListDTO(
				orderRepository.findAll().stream().map(orderMapper::orderToOrderDTO).collect(Collectors.toList()));
	}

	public OrderDTO getOrderById(Long id) {
		log.debug("getOrderById() called in OrdersService with order id: " + id);
		return orderRepository.findById(id).map(orderMapper::orderToOrderDTO)
				.orElseThrow(ResourseNotFoundException::new);
	}

	public OrderDTO makeOrder(Long id) {
		log.debug("makeOrder() called in OrdersService with order id: " + id);
		Optional<Order> optionalOrder = orderRepository.findById(id);
		if (optionalOrder.isEmpty()) {
			throw new ResourseNotFoundException("Order with id: " + id + " not found");
		}
		Order order = optionalOrder.get();
		order.getItems().forEach(item -> {
			Product product = item.getProduct();
			int unitsLeftInWarehouse = product.getUnitsLeftInWarehouse() - item.getQuantity();
			if (unitsLeftInWarehouse < 0) {
				throw new RuntimeException("The number of ordered units of product: " + product.getName()
						+ " is higher then the number of ordered units of this product. The number of "
						+ product.getName() + " units left is: " + product.getUnitsLeftInWarehouse());
			}
			product.setUnitsLeftInWarehouse(unitsLeftInWarehouse);
			productRepository.save(product);
		});
		return orderMapper.orderToOrderDTO(order);
	}

	public OrderDTO createNewOrder(OrderDTO orderDTO) {
		log.debug("createNewOrder() called in OrdersService with orderDTO: " + orderDTO);
		Order order = orderMapper.orderDTOtoOrder(orderDTO);
		Order savedOrder = orderRepository.save(order);
		return orderMapper.orderToOrderDTO(savedOrder);
	}

	public OrderDTO saveOrderByDTO(Long id, OrderDTO orderDTO) {
		log.debug("saveOrderByDTO() called in OrdersService with orderDTO: " + orderDTO + " and order id: " + id);
		Optional<Order> optionalOrder = orderRepository.findById(id);
		if (optionalOrder == null) {
			throw new ResourseNotFoundException();
		}
		Order order = optionalOrder.get();
		order = orderMapper.orderDTOtoOrder(orderDTO);
		order.setId(id);
		return orderMapper.orderToOrderDTO(orderRepository.save(order));
	}

	public OrderDTO patchOrder(Long id, OrderDTO orderDTO) {
		log.debug("patchOrder() called in OrdersService with orderDTO: " + orderDTO + " and order id: " + id);
		Optional<Order> optionalOrder = orderRepository.findById(id);
		if (optionalOrder.isEmpty()) {
			throw new ResourseNotFoundException("Order with id: " + id + "not found");
		}
		Order order = optionalOrder.get();
		if (order.getCustomer() == null) {
			Optional<Customer> customer = customerRepository.findById(orderDTO.getCustomerId());
			if (customer.isEmpty()) {
				throw new ResourseNotFoundException("Customer with id: " + id + "not found");
			}
			order.setCustomer(customer.get());
		}
		if (order.getItemsList() == null) {
			order.setItems(
					orderDTO.getItems().getItems().stream().map(itemMapper::itemDTOtoItem).collect(Collectors.toSet()));
		}
		return orderMapper.orderToOrderDTO(orderRepository.save(order));
	}

	public void deleteById(Long id) {
		log.debug("deleteById() called in OrdersService with order id: " + id);
		Order order = orderRepository.getById(id);
		orderRepository.delete(order);
	}

}
