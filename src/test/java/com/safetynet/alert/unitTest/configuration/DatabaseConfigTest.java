package com.safetynet.alert.unitTest.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.alert.configuration.DatabaseConfigImpl;

public class DatabaseConfigTest {

	private DatabaseConfigImpl databaseConfig;

	@BeforeEach
	void initTests() {
		databaseConfig = new DatabaseConfigImpl();
	}

	@Tag("DatabaseConfigTest")
	@Test
	void openConnection() {
		JSONObject data = databaseConfig.openConnection();
		assertThat(data).isNotEmpty();

		JSONArray persons = (JSONArray) data.get("persons");
		JSONArray stations = (JSONArray) data.get("firestations");
		JSONArray medicalRecords = (JSONArray) data.get("medicalrecords");

		assertThat(persons.size()).isPositive();
		assertThat(stations.size()).isPositive();
		assertThat(medicalRecords.size()).isPositive();

		data = null;
		persons = null;
		stations = null;
		medicalRecords = null;

		data = databaseConfig.openConnection("src/main/resources/static/data.json");
		assertThat(data).isNotEmpty();

		persons = (JSONArray) data.get("persons");
		stations = (JSONArray) data.get("firestations");
		medicalRecords = (JSONArray) data.get("medicalrecords");

		assertThat(persons.size()).isPositive();
		assertThat(stations.size()).isPositive();
		assertThat(medicalRecords.size()).isPositive();

		assertThat(databaseConfig.openConnection("")).isNullOrEmpty();
	}

	@Tag("DatabaseConfigTest")
	@Test
	void getData() {
		assertThat(databaseConfig.getData()).isNullOrEmpty();
		JSONObject data = databaseConfig.openConnection();
		assertThat(databaseConfig.getData()).isNotEmpty();
		assertThat(databaseConfig.getData()).isEqualTo(data);
	}

	@Tag("DatabaseConfigTest")
	@Test
	void closeConnection() {
		assertThat(databaseConfig.getData()).isNullOrEmpty();
		assertThat(databaseConfig.openConnection()).isNotEmpty();
		databaseConfig.closeConnection();
		assertThat(databaseConfig.getData()).isNullOrEmpty();
	}

	@AfterEach
	void undefTests() {
		databaseConfig = null;
	}

}
