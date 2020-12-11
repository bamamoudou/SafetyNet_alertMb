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

	@Test
	public void getMappingFirestationTest() throws Exception {

		mockMvc.perform(get("/firestation")).andExpect(status().is2xxSuccessful()).andReturn();
	}
	
	@Tag("FirestationControllerTest")
	@Test
	public void getTest() throws Exception {
		mockMvc.perform(get("/firestations")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Test
	public void getChildAlertTest() throws Exception {

		mockMvc.perform(get("/childAlert")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Test
	public void getPhoneAlertTest() throws Exception {

		mockMvc.perform(get("/phoneAlert")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Test
	public void getFireAlertTest() throws Exception {

		mockMvc.perform(get("/fire")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Test
	public void getFloodAlertTest() throws Exception {

		mockMvc.perform(get("/flood/stations")).andExpect(status().is2xxSuccessful()).andReturn();
	}

	@Test
	public void getPersonInfoTest() throws Exception {

		mockMvc.perform(get("/personInfo")).andExpect(status().is2xxSuccessful()).andReturn();

	}

	@Test
	public void getCommunityEmailTest() throws Exception {

		mockMvc.perform(get("/communityEmail")).andExpect(status().is2xxSuccessful()).andReturn();
	}

}
