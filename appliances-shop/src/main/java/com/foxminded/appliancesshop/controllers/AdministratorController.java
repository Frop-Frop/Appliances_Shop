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

import com.foxminded.appliancesshop.model.AdministratorDTO;
import com.foxminded.appliancesshop.model.AdministratorListDTO;
import com.foxminded.appliancesshop.services.AdministratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Administrator controller", description = "Administrator related operations")
@RestController
@RequestMapping("appliances/administrators/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	@Operation(summary = "Get list of all administrators", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorListDTO> getAllAdministrators() {
		return new ResponseEntity<AdministratorListDTO>(administratorService.getAllAdministrators(), HttpStatus.OK);
	}

	@Operation(summary = "Get administrator by id", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable Long id) {
		return new ResponseEntity<AdministratorDTO>(administratorService.getAdministratorById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create new administrator by administratorDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PostMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorDTO> createNewAdministrator(@RequestBody AdministratorDTO administratorDTO) {
		return new ResponseEntity<AdministratorDTO>(administratorService.createNewAdministrator(administratorDTO),
				HttpStatus.OK);
	}

	@Operation(summary = "Update administrator by administratorDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorDTO> updateAdministrator(@RequestBody AdministratorDTO administratorDTO,
			@PathVariable Long id) {
		return new ResponseEntity<AdministratorDTO>(administratorService.saveAdministratorByDTO(id, administratorDTO),
				HttpStatus.OK);
	}

	@Operation(summary = "Patch administrator by administratorDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorDTO> patchAdministrator(@RequestBody AdministratorDTO administratorDTO,
			@PathVariable Long id) {
		return new ResponseEntity<AdministratorDTO>(administratorService.patchAdministrator(id, administratorDTO),
				HttpStatus.OK);
	}

	@Operation(summary = "Delete administrator by id", security = @SecurityRequirement(name = "basicAuth"))
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
		administratorService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
