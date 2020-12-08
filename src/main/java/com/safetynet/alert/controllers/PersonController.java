package com.safetynet.alert.controllers;

import javax.inject.Singleton;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.models.Person;
import com.safetynet.alert.services.impl.PersonServiceImpl;

@RestController
@Singleton
public class PersonController {
	
	private PersonServiceImpl personService;
	
	private String messageStarting = "{\"message\" : \"";
	
	private String messageEnding = "\" }";

	public PersonController(PersonServiceImpl personService) {
		super();
		this.personService = personService;
	}
	
	@PostMapping("/person")
	public String post(@RequestBody Person newPerson) {
		return messageStarting + personService.httpPostPerson(newPerson) + messageEnding;
	}
	
	@PutMapping("/person")
	public String put(@RequestBody Person person) {
		
		return messageStarting + personService.httpPutPerson(person) + messageEnding;
		
	}
	
	@DeleteMapping("/person/{id}")
	public String delete(@PathVariable Integer id) {
		return messageStarting + personService.httpDeletePerson(id) + messageEnding;
	}
	
	
	

}
