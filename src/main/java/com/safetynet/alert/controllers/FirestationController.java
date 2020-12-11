package com.safetynet.alert.controllers;

import javax.inject.Singleton;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.models.Firestation;
import com.safetynet.alert.services.impl.FirestationServiceImpl;
import com.safetynet.alert.services.impl.InformationServiceImpl;

@RestController
@Singleton
public class FirestationController {

	private FirestationServiceImpl firestationService;
	
	private InformationServiceImpl informationService;

	private String messageStarting = "{\"message\" : \"";

	private String messageEnding = "\" }";

	public FirestationController(FirestationServiceImpl firestationService) {
		super();
		this.firestationService = firestationService;
	}
	@GetMapping("/firestations")
	public String get() {
		return informationService.getAllFirestations();
		
	}

	@PostMapping("/firestation")
	public String post(@RequestBody Firestation newFirestation) {
		return messageStarting + firestationService.httpPostFirestation(newFirestation) + messageEnding;
		

	}

	@PutMapping("/firestation")
	public String put(@RequestBody Firestation firestation) {
		return messageStarting + firestationService.httpPutFirestation(firestation) + messageEnding;

	}

	@DeleteMapping("/firestation/address/{address}")
	public String deleteMapping(@PathVariable String address) {

		return messageStarting + firestationService.httpDeleteMapping(address) + messageEnding;
	}

	@DeleteMapping("/firestation/firestationNumber/{number}")
	public String deleteFirestation(@PathVariable Integer number) {
		return messageStarting + firestationService.httpDeleteFirestation(number) + messageEnding;
	}

}
