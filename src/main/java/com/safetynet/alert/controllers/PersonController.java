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
	

	public PersonController(PersonServiceImpl personService) {
		super();
		this.personService = personService;
	}
	
	@PostMapping("/person")
	public String post(@RequestBody Person newPerson) {
		return  personService.httpPostPerson(newPerson);
	}
	
	@PutMapping("/person")
	public String put(@RequestBody Person person) {
		
		return personService.httpPutPerson(person);
		
	}
	
	@DeleteMapping("/person/{id}")
	public String delete(@PathVariable Integer id) {
		return personService.httpDeletePerson(id);
	}
	
	
	

}
