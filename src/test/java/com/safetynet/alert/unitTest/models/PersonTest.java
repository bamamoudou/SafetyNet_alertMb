package com.safetynet.alert.unitTest.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.alert.models.Person;

public class PersonTest {
	private Person person;

	@BeforeEach
	void initTest() {
		person = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
	}

	@Tag("PersonTest")
	@Test
	public void getterTest() {
		// Arrange

		// Act
		// Assert
		assertThat(person.getFirstName()).isEqualTo("John");
		assertThat(person.getLastName()).isEqualTo("Boyd");
		assertThat(person.getAddress()).isEqualTo("1509 Culver St");
		assertThat(person.getCity()).isEqualTo("Culver");
		assertThat(person.getZip()).isEqualTo("97451");
		assertThat(person.getPhone()).isEqualTo("841-874-6512");
		assertThat(person.getEmail()).isEqualTo("jaboyd@email.com");

	}

	@Tag("PersonTest")
	@Test
	public void gettersAndSettersTest() {
		person.setFirstName("Jacob");
		person.setLastName("Boyd");
		person.setAddress("1509 Culver St");
		person.setCity("Culver");
		person.setZip("97451");
		person.setPhone("841-874-6513");
		person.setEmail("drk@email.com");

		assertThat(person.getFirstName()).isEqualTo("Jacob");
		assertThat(person.getLastName()).isEqualTo("Boyd");
		assertThat(person.getAddress()).isEqualTo("1509 Culver St");
		assertThat(person.getCity()).isEqualTo("Culver");
		assertThat(person.getZip()).isEqualTo("97451");
		assertThat(person.getPhone()).isEqualTo("841-874-6513");
		assertThat(person.getEmail()).isEqualTo("drk@email.com");

	}

	@Tag("PersonTest")
	@Test
	public void SetPersonAsNull() {
		person.setFirstName("");
		person.setLastName("");
		person.setAddress("");
		person.setCity("");
		person.setZip("");
		person.setPhone("");
		person.setEmail("");

		assertThat(person.getFirstName()).isEmpty();
		assertThat(person.getLastName()).isEmpty();
		assertThat(person.getAddress()).isEmpty();
		assertThat(person.getCity()).isEmpty();
		assertThat(person.getZip()).isEmpty();
		assertThat(person.getPhone()).isEmpty();
		assertThat(person.getEmail()).isEmpty();

	}

	@AfterEach
	public void undefineTest() {
		person = null;
	}

}
