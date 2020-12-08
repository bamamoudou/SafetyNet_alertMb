package com.safetynet.alert.unitTest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
@RunWith(SpringRunner.class)
@ComponentScan({ "com.safetynet.alert" })
public class MedicalRecordTest {
	String data = "{\"id\" : 0, \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"]}";
	String URL = "/medicalRecord";

	@Autowired
	MockMvc mockMvc;

	@Tag("MedicalRecordControllerTest")
	@Test
	public void postTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL).accept(MediaType.APPLICATION_JSON).content(data)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(result.getResponse().getContentAsString()).isInstanceOf(String.class);

	}

	@Tag("MedicalRecordControllerTest")
	@Test
	public void putTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URL).accept(MediaType.APPLICATION_JSON).content(data)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(result.getResponse().getContentAsString()).isInstanceOf(String.class);

	}

	@Tag("PersonControllerTest")
	@Test
	void deleteTest() throws Exception {
		mockMvc.perform(delete(URL + "/1")).andExpect(status().is2xxSuccessful());

		mockMvc.perform(delete(URL)).andExpect(status().is4xxClientError());
	}

}
