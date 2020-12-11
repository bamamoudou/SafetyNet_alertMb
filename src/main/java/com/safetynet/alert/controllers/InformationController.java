package com.safetynet.alert.controllers;

import javax.inject.Singleton;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.services.impl.InformationServiceImpl;

@RestController
@Singleton
public class InformationController {

	private InformationServiceImpl informationService;

	public InformationController(InformationServiceImpl informationService) {
		super();
		this.informationService = informationService;
	}

	@GetMapping("/firestation")
	public String getMappingFirestation(@RequestParam(required = false) Integer firestationNumber) {

		return informationService.getAllPersonsServedByTheStationWithCount(firestationNumber);

	}

	@GetMapping("/firestations")
	public String get() {
		return informationService.getAllFirestations();

	}

	@GetMapping("/phoneAlert")
	public String getPhoneAler(@RequestParam(required = false) Integer firestation) {

		return informationService.getAllPersonsPhoneByStationNumber(firestation);

	}

	@GetMapping("/childAlert")
	public String getchildAlert(@RequestParam(required = false) String address) {

		return informationService.getAllChildByAddress(address);

	}

	@GetMapping("/fire")
	public String getFire(@RequestParam(required = false) String address) {

		return informationService.getAllPersonsLivingAtTheAddress(address);
	}

	@GetMapping("/flood/stations")
	public String getFlood(@RequestParam(required = false) String firestation) {

		return informationService.getAllPersonsServedByTheStations(firestation);

	}

	@GetMapping("/personInfo")
	public String getPersonInfo(@RequestParam(required = false) String firstName, String lastName) {

		return informationService.getAllCompleteProfileOfPersonsByName(firstName, lastName);
	}

	@GetMapping("/communityEmail")
	public String getCommunityEmail(@RequestParam(required = false) String city) {

		return informationService.getAllPersonsEmailByCity(city);
	}

}
