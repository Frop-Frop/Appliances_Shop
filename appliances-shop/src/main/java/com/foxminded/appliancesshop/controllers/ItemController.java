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

import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.services.ItemService;

@RestController
@RequestMapping("appliances/items/")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@GetMapping("cart/{cartId}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<ItemListDTO> getAllItemsInCart(@PathVariable Long cartId) {
		return new ResponseEntity<ItemListDTO>(itemService.getAllItemsInCart(cartId), HttpStatus.OK);
	}

	@GetMapping("order/{orderId}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<ItemListDTO> getAllItemsInOrder(@PathVariable Long orderId) {
		return new ResponseEntity<ItemListDTO>(itemService.getAllItemsInOrder(orderId), HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
		return new ResponseEntity<ItemDTO>(itemService.getItemById(id), HttpStatus.OK);
	}

	@GetMapping("customer/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<ItemListDTO> getCustomerDeferreds(Long id) {
		return new ResponseEntity<ItemListDTO>(itemService.getCustomerDeferreds(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<ItemDTO> createNewItem(@RequestBody ItemDTO itemDTO) {
		return new ResponseEntity<ItemDTO>(itemService.createNewItem(itemDTO), HttpStatus.OK);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO itemDTO, @PathVariable Long id) {
		return new ResponseEntity<ItemDTO>(itemService.saveItemByDTO(id, itemDTO), HttpStatus.OK);
	}

	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<ItemDTO> patchItem(@RequestBody ItemDTO itemDTO, @PathVariable Long id) {
		return new ResponseEntity<ItemDTO>(itemService.patchItem(id, itemDTO), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		itemService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
