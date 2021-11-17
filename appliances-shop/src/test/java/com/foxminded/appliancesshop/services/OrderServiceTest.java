package com.foxminded.appliancesshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Order;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.mappers.OrderMapper;
import com.foxminded.appliancesshop.mappers.ProductMapper;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.model.OrderDTO;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.repositories.OrderRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

@SpringBootTest(classes = { OrderService.class, OrderMapper.class, ItemMapper.class })
class OrderServiceTest {

	@MockBean
	private OrderRepository orderRepository;
	@MockBean
	private CustomerRepository customerRepository;
	@MockBean
	private ItemMapper itemMapper;
	@MockBean
	private ProductMapper productMapper;
	@MockBean
	private ProductRepository productRepository;
	@MockBean
	private OrderMapper orderMapper;
	@Autowired
	private OrderService orderService;

	@Test
	void getAllOrdersTest() {
		Order order = new Order();
		order.setId(1L);
		Order order1 = new Order();
		order1.setId(2L);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		OrderDTO orderDTO1 = new OrderDTO();
		orderDTO1.setId(2L);
		when(orderMapper.orderToOrderDTO(order)).thenReturn(orderDTO);
		when(orderMapper.orderToOrderDTO(order1)).thenReturn(orderDTO1);
		when(orderRepository.findAll()).thenReturn(Arrays.asList(order, order1));
		orderService.getAllOrders();
		verify(orderRepository, times(1)).findAll();
		verify(orderMapper, times(1)).orderToOrderDTO(order);
		verify(orderMapper, times(1)).orderToOrderDTO(order1);
	}

	@Test
	void getOrderByIdTest() {
		Order order = new Order();
		order.setCustomer(customerRepository.getById(1L));
		order.setId(10L);
		Optional<Order> orderOptional = Optional.of(order);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(10L);
		orderDTO.setCustomerId(1L);

		when(orderRepository.findById(anyLong())).thenReturn(orderOptional);
		when(orderMapper.orderToOrderDTO(order)).thenReturn(orderDTO);

		OrderDTO orderReturned = orderService.getOrderById(50l);
		assertNotNull(orderReturned);
		verify(orderRepository, times(1)).findById(50L);
		verify(orderRepository, never()).findAll();
	}

	@Test
	void createNewOrderTest() {
		Order order = new Order();
		order.setId(1L);
		Order orderWithoutId = new Order();
		OrderDTO orderDTO = new OrderDTO();
		when(orderMapper.orderDTOtoOrder(orderDTO)).thenReturn(orderWithoutId);
		when(orderRepository.save(orderWithoutId)).thenReturn(order);
		OrderDTO orderDTO1 = new OrderDTO();
		orderDTO1.setId(1L);
		when(orderMapper.orderToOrderDTO(order)).thenReturn(orderDTO1);
		orderService.createNewOrder(orderDTO);
		verify(orderRepository, times(1)).save(orderWithoutId);
		verify(orderMapper, times(1)).orderDTOtoOrder(orderDTO);
		verify(orderMapper, times(1)).orderToOrderDTO(order);
	}

	@Test
	void makeOrderTest() {
		Product product = new Product();
		product.setId(1L);
		product.setName("Product");
		product.setUnitsLeftInWarehouse(10);
		Product product1 = new Product();
		product1.setId(2L);
		product1.setName("Product1");
		product1.setUnitsLeftInWarehouse(20);
		Item item = new Item();
		item.setId(1L);
		item.setProduct(product);
		item.setQuantity(5);
		Item item1 = new Item();
		item1.setId(2L);
		item1.setProduct(product1);
		item1.setQuantity(10);
		Order order = new Order();
		order.setCustomer(customerRepository.getById(1L));
		order.setId(10L);
		order.setItems(Set.of(item, item1));
		Optional<Order> orderOptional = Optional.of(order);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(10L);
		orderDTO.setCustomerId(1L);
		when(orderRepository.findById(1L)).thenReturn(orderOptional);
		when(orderMapper.orderToOrderDTO(order)).thenReturn(orderDTO);

		OrderDTO orderReturned = orderService.makeOrder(1l);
		assertNotNull(orderReturned);
		verify(orderRepository, times(1)).findById(1L);
		verify(orderRepository, never()).findAll();
		assertEquals(product.getUnitsLeftInWarehouse(), 5);
		assertEquals(product1.getUnitsLeftInWarehouse(), 10);
	}

	@Test
	void pathOrderTest() {
		Order order = new Order();
		order.setCustomer(customerRepository.getById(1L));
		order.setId(1L);
		Optional<Order> optionalOrder = Optional.of(order);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCustomerId(1L);

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(1L);
		itemDTO.setQuantity(5);
		ItemDTO itemDTO1 = new ItemDTO();
		itemDTO1.setId(2L);
		itemDTO1.setQuantity(6);
		ItemListDTO itemListDTO = new ItemListDTO(Arrays.asList(itemDTO, itemDTO1));
		orderDTO.setItems(itemListDTO);

		Customer customer = new Customer();
		customer.setFirstName("first");
		customer.setLastName("last");
		customer.setId(1L);
		Optional<Customer> optionalCustomer = Optional.of(customer);
		when(orderRepository.findById(anyLong())).thenReturn(optionalOrder);
		when(orderMapper.orderToOrderDTO(order)).thenReturn(orderDTO);
		when(customerRepository.findById(1L)).thenReturn(optionalCustomer);
		orderService.patchOrder(order.getId(), orderDTO);
		verify(orderRepository, times(1)).findById(order.getId());
		verify(orderRepository, times(1)).save(order);
	}

	@Test
	void saveOrderByDTOtest() {
		Order order = new Order();
		order.setCustomer(customerRepository.getById(1L));
		order.setId(1L);
		Optional<Order> optionalOrder = Optional.of(order);
		when(orderRepository.findById(anyLong())).thenReturn(optionalOrder);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCustomerId(1L);
		when(orderMapper.orderToOrderDTO(order)).thenReturn(orderDTO);
		when(orderMapper.orderDTOtoOrder(orderDTO)).thenReturn(order);
		orderService.saveOrderByDTO(order.getId(), orderDTO);
		verify(orderRepository, times(1)).findById(order.getId());
		verify(orderRepository, times(1)).save(order);
	}

}
