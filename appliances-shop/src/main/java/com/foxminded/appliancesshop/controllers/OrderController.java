package com.foxminded.appliancesshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.appliancesshop.model.OrderDTO;
import com.foxminded.appliancesshop.model.OrderListDTO;
import com.foxminded.appliancesshop.services.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Order related operations")
@RestController
@RequestMapping("appliances/orders/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@ApiOperation(value = "Get list of all orders")
	@GetMapping()
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<OrderListDTO> getAllOrders() {
		return new ResponseEntity<OrderListDTO>(orderService.getAllOrders(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get order by id")
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
		return new ResponseEntity<OrderDTO>(orderService.getOrderById(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Make order by id")
	@GetMapping("make_order/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> makeOrder(@PathVariable Long id) {
		return new ResponseEntity<OrderDTO>(orderService.makeOrder(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Create new order by orderDTO")
	@PostMapping
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> createNewOrder(@RequestBody OrderDTO orderDTO) {
		return new ResponseEntity<OrderDTO>(orderService.createNewOrder(orderDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Update order by orderDTO")
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
		return new ResponseEntity<OrderDTO>(orderService.saveOrderByDTO(id, orderDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Patch order by orderDTO")
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> patchOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
		return new ResponseEntity<OrderDTO>(orderService.patchOrder(id, orderDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete order by id")
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		orderService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
