package com.safetynet.alert.unitTest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alert.services.impl.InformationServiceImpl;

@WebMvcTest
@RunWith(SpringRunner.class)
@ComponentScan({ "com.safetynet.alert.configuration", "com.safetynet.alert.controller" })
public class InformationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	InformationServiceImpl informationService;

	@Tag("InformationControllerTest")
	@Test
	public void getMappingFirestationTest() throws Exception {

		mockMvc.perform(get("/firestation").param("stationNumber", "1")).andExpect(status().is2xxSuccessful())
				.andReturn();
	}

	@Tag("InformationControllerTest")
	@Test
	public void getFirestationsTest() throws Exception {
		mockMvc.perform(get("/firestations")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Tag("InformationControllerTest")
	@Test
	public void getPersonsTest() throws Exception {
		mockMvc.perform(get("/persons")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Tag("InformationControllerTest")
	@Test
	public void getMedicalRecordsTest() throws Exception {
		mockMvc.perform(get("/medicalRecords")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Tag("InformationControllerTest")
	@Test
	public void getChildrenAtThisAddressTest() throws Exception {

		mockMvc.perform(get("/childAlert").param("address", "1509 Culver St")).andExpect(status().is2xxSuccessful())
				.andReturn();

	}

	@Tag("InformationControllerTest")
	@Test
	public void getPhoneAlertTest() throws Exception {

		mockMvc.perform(get("/phoneAlert").param("firestation", "1")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Tag("InformationControllerTest")
	@Test
	public void getAllPersonsLivingAtThisAddressAndTheNumberStationTest() throws Exception {

		mockMvc.perform(get("/fire").param("address", "908 73rd St")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Tag("InformationControllerTest")
	@Test
	public void getHouseholdListAndPersonsPerAddressByStationListWhenFlood() throws Exception {

		mockMvc.perform(get("/flood/stations").param("stations", "1,2")).andExpect(status().is2xxSuccessful()).andReturn();
	}

	@Tag("InformationControllerTest")
	@Test
	public void getPersonInfoTest() throws Exception {

		mockMvc.perform(get("/personInfo").param("firstName", "John" ).param("lastName", "Boyd")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Tag("InformationControllerTest")
	@Test
	public void getCommunityEmailTest() throws Exception {

		mockMvc.perform(get("/communityEmail")).andExpect(status().is2xxSuccessful()).andReturn();
	}

}
