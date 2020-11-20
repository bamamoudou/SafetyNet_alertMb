package com.safetynet.alert.unitTest.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.alert.models.Firestation;

public class FirestationTest {

	private Firestation station;

	@BeforeEach
	public void initTest() {
		station = new Firestation(1, "908 73rd St");
	}

	@Tag("StationTest")
	@Test
	public void getterTest() {
		assertThat(station.getNumberStation()).isEqualTo(1);
		assertThat(station.getAddress()).isEqualTo("908 73rd St");

	}

	@Tag("StationTest")
	@Test
	public void getterAndSetterTest() {
		station.setNumberStation(4);
		station.setAddress("112 Steppes Pl");

		assertThat(station.getNumberStation()).isEqualTo(4);
		assertThat(station.getAddress()).isEqualTo("112 Steppes Pl");

	}

	@Tag("StationTest")
	@Test
	public void setStationAsNull() {
		station.setAddress("");
		station.setNumberStation(0);

		assertThat(station.getAddress()).isEmpty();
		assertThat(station.getNumberStation()).isEqualTo(0);

	}

	@AfterEach
	public void undefinedTest() {
		station = null;

	}

}
