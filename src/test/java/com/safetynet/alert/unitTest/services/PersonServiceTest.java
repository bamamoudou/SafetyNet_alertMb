package com.safetynet.alert.unitTest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alert.DAO.impl.MedicalRecordDAOImpl;
import com.safetynet.alert.DAO.impl.PersonDAOImpl;
import com.safetynet.alert.models.Person;
import com.safetynet.alert.services.impl.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	private Person person;
	private PersonServiceImpl personService;

	@Mock
	private static PersonDAOImpl personDAO;

	@Mock
	private static MedicalRecordDAOImpl medicalRecordDAO;

	@BeforeEach
	void setUp() {
		person = new Person(0, "", "", "", "", "", "", "");
		personService = new PersonServiceImpl(personDAO, medicalRecordDAO);
	}

	@Tag("PersonServiceTest")
	@Test
	public void httpPostIfPersonIsNull() {
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> personService.httpPostPerson(null));
		verify(personDAO, never()).addNewPerson(any(Person.class));
	}

	@Tag("PersonServiceTest")
	@Test
	void httpPostIfDAOIstrue() {
		when(personDAO.addNewPerson(person)).thenReturn(true);
		assertThat(personService.httpPostPerson(person)).isEqualTo("Person added");
		verify(personDAO, times(1)).addNewPerson(person);
	}

	@Tag("PersonServiceTest")
	@Test
	void httpPostIfDAOIsfalse() {
		when(personDAO.addNewPerson(person)).thenReturn(false);
		assertThat(personService.httpPostPerson(person)).isEqualTo("Error : This Person can't be added");
		verify(personDAO, times(1)).addNewPerson(person);
	}

	@Tag("PersonServiceTest")
	@Test
	void httpPutIfPersonIsNull() {
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> personService.httpPutPerson(null));
		verify(personDAO, never()).updatePerson(any(Person.class));
	}

	@Tag("PersonServiceTest")
	@Test
	void httpPutIfDAOIstrue() {
		when(personDAO.updatePerson(person)).thenReturn(true);
		assertThat(personService.httpPutPerson(person)).isEqualTo("Person updated");
		verify(personDAO, times(1)).updatePerson(person);
	}

	@Tag("PersonServiceTest")
	@Test
	void httpPutIfDAOIsfalse() {
		when(personDAO.updatePerson(person)).thenReturn(false);
		assertThat(personService.httpPutPerson(person)).isEqualTo("Error : This Person hasn't been updated");
		verify(personDAO, times(1)).updatePerson(person);
	}

	@Tag("PersonServiceTest")
	@Test
	void httpDeletePersonDAOIsTrueAndmedicalRecordDAOIsTrue() {
		when(personDAO.deletePerson(0)).thenReturn(true);
		when(medicalRecordDAO.deleteMedicalRecord(0)).thenReturn(true);
		assertThat(personService.httpDeletePerson(0)).isEqualTo("Medical record deleted / Person deleted");
		verify(personDAO, times(1)).deletePerson(0);
		verify(medicalRecordDAO, times(1)).deleteMedicalRecord(0);
	}

	@Tag("PersonServiceTest")
	@Test
	void httpDeletePersonDAOIsTrueAndmedicalRecordDAOIsfalse() {
		when(personDAO.deletePerson(0)).thenReturn(true);
		when(medicalRecordDAO.deleteMedicalRecord(0)).thenReturn(false);
		assertThat(personService.httpDeletePerson(0))
				.isEqualTo("Error : The medical record of this person hasn't be deleted / Person deleted");
		verify(personDAO, times(1)).deletePerson(0);
		verify(medicalRecordDAO, times(1)).deleteMedicalRecord(0);
	}

	@Tag("PersonServiceTest")
	@Test
	void httpDeletePersonDAOIsFalseAndMedicalRecordDAOIstrue() {
		when(personDAO.deletePerson(0)).thenReturn(false);
		when(medicalRecordDAO.deleteMedicalRecord(0)).thenReturn(true);
		assertThat(personService.httpDeletePerson(0))
				.isEqualTo("Medical record deleted / Error : This Person hasn't been deleted");
		verify(personDAO, times(1)).deletePerson(0);
		verify(medicalRecordDAO, times(1)).deleteMedicalRecord(0);
	}

	@Tag("PersonServiceTest")
	@Test
	void httpDeletePersonDAOIsFalseAndMedicalRecordDAOIsFalse() {
		when(personDAO.deletePerson(0)).thenReturn(false);
		when(medicalRecordDAO.deleteMedicalRecord(0)).thenReturn(false);
		assertThat(personService.httpDeletePerson(0)).isEqualTo(
				"Error : The medical record of this person hasn't be deleted / Error : This Person hasn't been deleted");
		verify(personDAO, times(1)).deletePerson(0);
		verify(medicalRecordDAO, times(1)).deleteMedicalRecord(0);
	}

	@AfterEach
	void tearDown() {
		personService = null;
		person = null;
	}

}
