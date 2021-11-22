package com.foxminded.appliancesshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.services.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cart controller", description = "Cart related operations")
@RestController
@RequestMapping("appliances/carts/")
public class CartController {

	@Autowired
	private CartService cartService;

	@Operation(summary = "Get cart by id", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.getCartById(id), HttpStatus.OK);
	}

	@Operation(summary = "Update cart by cartDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CartDTO> updateCart(@RequestBody CartDTO cartDTO, @PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.saveCartByDTO(id, cartDTO), HttpStatus.OK);
	}

	@Operation(summary = "Patch cart by cartDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CartDTO> patchCart(@RequestBody CartDTO cartDTO, @PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.patchCart(id, cartDTO), HttpStatus.OK);
	}

}
