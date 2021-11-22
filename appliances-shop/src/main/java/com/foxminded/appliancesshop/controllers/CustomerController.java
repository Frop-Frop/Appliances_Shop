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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Custoemr controller", description = "Customer related operations")
@RestController
@RequestMapping("appliances/customers/")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Operation(summary = "Get list of all customer", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<CustomerListDTO> getAllCustomers() {
		return new ResponseEntity<CustomerListDTO>(customerService.getAllCustomers(), HttpStatus.OK);
	}

	@Operation(summary = "Get customer by id", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
		return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create new customer by customerDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PostMapping
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		return new ResponseEntity<CustomerDTO>(customerService.createNewCustomer(customerDTO), HttpStatus.OK);
	}

	@Operation(summary = "Update customer by customerDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
		return new ResponseEntity<CustomerDTO>(customerService.saveCustomerByDTO(id, customerDTO), HttpStatus.OK);
	}

	@Operation(summary = "Patch customer by customerDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<CustomerDTO> patchCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
		return new ResponseEntity<CustomerDTO>(customerService.patchCustomer(id, customerDTO), HttpStatus.OK);
	}

	@Operation(summary = "Delete customer by id", security = @SecurityRequirement(name = "basicAuth"))
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
