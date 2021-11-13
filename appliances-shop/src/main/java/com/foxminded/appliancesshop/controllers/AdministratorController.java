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

@RestController
@RequestMapping("appliances/administrators/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	@GetMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorListDTO> getAllAdministrators() {
		return new ResponseEntity<AdministratorListDTO>(administratorService.getAllAdministrators(), HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable Long id) {
		return new ResponseEntity<AdministratorDTO>(administratorService.getAdministratorById(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorDTO> createNewAdministrator(@RequestBody AdministratorDTO administratorDTO) {
		return new ResponseEntity<AdministratorDTO>(administratorService.createNewAdministrator(administratorDTO),
				HttpStatus.OK);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorDTO> updateAdministrator(@RequestBody AdministratorDTO administratorDTO,
			@PathVariable Long id) {
		return new ResponseEntity<AdministratorDTO>(administratorService.saveAdministratorByDTO(id, administratorDTO),
				HttpStatus.OK);
	}

	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<AdministratorDTO> patchAdministrator(@RequestBody AdministratorDTO administratorDTO,
			@PathVariable Long id) {
		return new ResponseEntity<AdministratorDTO>(administratorService.patchAdministrator(id, administratorDTO),
				HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('server_change')")
	public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
		administratorService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
