package com.foxminded.appliancesshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.services.CartService;

@RestController
@RequestMapping("appliances/carts/")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("{id}")
	public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.getCartById(id), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<CartDTO> updateCart(@RequestBody CartDTO cartDTO, @PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.saveCartByDTO(id, cartDTO), HttpStatus.OK);
	}

	@PatchMapping("{id}")
	public ResponseEntity<CartDTO> patchCart(@RequestBody CartDTO cartDTO, @PathVariable Long id) {
		return new ResponseEntity<CartDTO>(cartService.patchCart(id, cartDTO), HttpStatus.OK);
	}

}
