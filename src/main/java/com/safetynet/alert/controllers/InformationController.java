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

	@GetMapping("/persons")
	public String getPersons() {
		return informationService.getAllPersons();
	}

	@GetMapping("/person")
	public String getPersonById(@RequestParam Integer id) {
		return informationService.getPersonById(id);
	}

	@GetMapping("/medicalRecords")
	public String getMedicalRecords() {
		return informationService.getAllMedicalRecords();
	}

	@GetMapping("/firestation")
	public String getMappingFirestation(@RequestParam Integer stationNumber) {

		return informationService.getAllPersonsServedByTheStationWithCount(stationNumber);

	}

	@GetMapping("/firestations")
	public String getAllFirestation() {
		return informationService.getAllFirestations();

	}

	@GetMapping("/phoneAlert")
	public String getAllPersonsPhoneByStationNumbe(@RequestParam Integer firestation) {

		return informationService.getAllPersonsPhoneByStationNumber(firestation);

	}

	@GetMapping("/childAlert")
	public String getchildrenAtThisAddress(@RequestParam String address) {

		return informationService.getChildrenByAddress(address);

	}

	@GetMapping("/fire")
	public String getAllPersonsLivingAtThisAddressAndTheNumberStation(@RequestParam String address) {

		return informationService.getAllPersonsLivingAtTheAddressAndTheNumberStation(address);
	}

	@GetMapping("/flood/stations")
	public String getHouseholdListAndPersonsPerAddressByStationListWhenFlood(@RequestParam String stations) {

		return informationService.getHouseholdListAndPersonsPerAddressWhenFlood(stations);

	}

	@GetMapping("/personInfo")
	public String getPersonInfo(@RequestParam String firstName, String lastName) {

		return informationService.getAllCompleteProfileOfPersonsByName(firstName, lastName);
	}

	@GetMapping("/communityEmail")
	public String getCommunityEmail(@RequestParam String city) {

		return informationService.getAllPersonsEmailByCity(city);
	}

}
