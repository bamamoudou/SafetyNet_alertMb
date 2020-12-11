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
public class FirestationControllerTest {
	String data = "{\"numberStation\" : 1, \"address\":\"644 Gershwin Cir\"}";
	String URL = "/firestation";

	@Autowired
	MockMvc mockMvc;

	@Tag("FirestationControllerTest")
	@Test
	public void postTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL).accept(MediaType.APPLICATION_JSON).content(data)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(result.getResponse().getContentAsString()).isInstanceOf(String.class);

	}

	@Tag("FirestationControllerTest")
	@Test
	public void putTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URL).accept(MediaType.APPLICATION_JSON).content(data)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(result.getResponse().getContentAsString()).isInstanceOf(String.class);

	}

	@Tag("FirestationControllerTest")
	@Test
	public void deleteMappingTest() throws Exception {
		mockMvc.perform(delete(URL + "/address/644 Gershwin Cir")).andExpect(status().is2xxSuccessful());

		mockMvc.perform(delete(URL + "/address")).andExpect(status().is4xxClientError());
	}

	@Tag("FirestationControllerTest")
	@Test
	public void deleteFirestationTest() throws Exception {
		mockMvc.perform(delete(URL + "/firestationNumber/1")).andExpect(status().is2xxSuccessful());

		mockMvc.perform(delete(URL + "/firestationNumber")).andExpect(status().is4xxClientError());
	}

}
