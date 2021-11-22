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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Order controller", description = "Order related operations")
@RestController
@RequestMapping("appliances/orders/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Operation(summary = "Get list of all orders", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping()
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<OrderListDTO> getAllOrders() {
		return new ResponseEntity<OrderListDTO>(orderService.getAllOrders(), HttpStatus.OK);
	}

	@Operation(summary = "Get order by id", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
		return new ResponseEntity<OrderDTO>(orderService.getOrderById(id), HttpStatus.OK);
	}

	@Operation(summary = "Make order by id", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("make_order/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> makeOrder(@PathVariable Long id) {
		return new ResponseEntity<OrderDTO>(orderService.makeOrder(id), HttpStatus.OK);
	}

	@Operation(summary = "Create new order by orderDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PostMapping
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> createNewOrder(@RequestBody OrderDTO orderDTO) {
		return new ResponseEntity<OrderDTO>(orderService.createNewOrder(orderDTO), HttpStatus.OK);
	}

	@Operation(summary = "Update order by orderDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
		return new ResponseEntity<OrderDTO>(orderService.saveOrderByDTO(id, orderDTO), HttpStatus.OK);
	}

	@Operation(summary = "Patch order by orderDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<OrderDTO> patchOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
		return new ResponseEntity<OrderDTO>(orderService.patchOrder(id, orderDTO), HttpStatus.OK);
	}

	@Operation(summary = "Delete order by id", security = @SecurityRequirement(name = "basicAuth"))
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		orderService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
