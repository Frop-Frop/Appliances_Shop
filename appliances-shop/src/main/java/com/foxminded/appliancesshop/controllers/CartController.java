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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Cart related operations")
@RestController
@RequestMapping("appliances/carts/")
public class CartController {

	@Autowired
	private CartService cartService;

	@ApiOperation(value = "Get cart by id")
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.getCartById(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Update cart by cartDTO")
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CartDTO> updateCart(@RequestBody CartDTO cartDTO, @PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.saveCartByDTO(id, cartDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Patch cart by cartDTO")
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CartDTO> patchCart(@RequestBody CartDTO cartDTO, @PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.patchCart(id, cartDTO), HttpStatus.OK);
	}

}
