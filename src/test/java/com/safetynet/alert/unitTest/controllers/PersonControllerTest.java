package com.safetynet.alert.unitTest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
import org.springframework.web.util.NestedServletException;

@WebMvcTest
@RunWith(SpringRunner.class)
@ComponentScan({ "com.safetynet.alert" })
public class PersonControllerTest {

	String data = "{\"id\" : 1,\"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\"}";
	String URL = "/person";

	@Autowired
	MockMvc mockMvc;

	@Tag("PersonControllerTest")
	@Test
	public void postTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL).accept(MediaType.APPLICATION_JSON).content(data)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(result.getResponse().getContentAsString()).isInstanceOf(String.class);

	}

	@Tag("PersonControllerTest")
	@Test
	public void postEmptyBodyTest() {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL).accept(MediaType.APPLICATION_JSON).content("{}")
				.contentType(MediaType.APPLICATION_JSON);

		assertThatExceptionOfType(NestedServletException.class)
				.isThrownBy(() -> mockMvc.perform(requestBuilder).andReturn());
	}

	@Tag("PersonControllerTest")
	@Test
	public void putTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URL).accept(MediaType.APPLICATION_JSON).content(data)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(result.getResponse().getContentAsString()).isInstanceOf(String.class);

	}

//	@Tag("PersonControllerTest")
//	@Test
//	public void putEmptyBodyTest() {
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URL).accept(MediaType.APPLICATION_JSON).content("{}")
//				.contentType(MediaType.APPLICATION_JSON);
//
//		assertThatExceptionOfType(NestedServletException.class)
//				.isThrownBy(() -> mockMvc.perform(requestBuilder).andReturn());
//	}

	@Tag("PersonControllerTest")
	@Test
	public void deleteTest() throws Exception {
		mockMvc.perform(delete(URL + "/1")).andExpect(status().is2xxSuccessful());

		mockMvc.perform(delete(URL)).andExpect(status().is4xxClientError());
	}

}
