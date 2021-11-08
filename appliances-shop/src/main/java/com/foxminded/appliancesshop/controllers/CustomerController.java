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

import com.foxminded.appliancesshop.model.CustomerDTO;
import com.foxminded.appliancesshop.model.CustomerListDTO;
import com.foxminded.appliancesshop.services.CustomerService;

@RestController
@RequestMapping("appliances/customers/")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<CustomerListDTO> getAllCustomers() {
		return new ResponseEntity<CustomerListDTO>(customerService.getAllCustomers(), HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
		return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		return new ResponseEntity<CustomerDTO>(customerService.createNewCustomer(customerDTO), HttpStatus.OK);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
		return new ResponseEntity<CustomerDTO>(customerService.saveCustomerByDTO(id, customerDTO), HttpStatus.OK);
	}

	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CustomerDTO> patchCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
		return new ResponseEntity<CustomerDTO>(customerService.patchCustomer(id, customerDTO), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
