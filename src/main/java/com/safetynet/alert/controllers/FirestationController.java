package com.safetynet.alert.controllers;

import javax.inject.Singleton;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.models.Firestation;
import com.safetynet.alert.services.impl.FirestationServiceImpl;

@RestController
@Singleton
public class FirestationController {

	private FirestationServiceImpl firestationService;

	public FirestationController(FirestationServiceImpl firestationService) {
		super();
		this.firestationService = firestationService;
	}

	@PostMapping("/firestation")
	public String post(@RequestBody Firestation newFirestation) {
		return firestationService.httpPostFirestation(newFirestation);

	}

	@PutMapping("/firestation")
	public String put(@RequestBody Firestation firestation) {
		return firestationService.httpPutFirestation(firestation);

	}

	@DeleteMapping("/firestation/address/{address}")
	public String deleteMapping(@PathVariable String address) {

		return firestationService.httpDeleteMapping(address);
	}

	@DeleteMapping("/firestation/firestationNumber/{number}")
	public String deleteFirestation(@PathVariable Integer number) {
		return firestationService.httpDeleteFirestation(number);
	}

}
