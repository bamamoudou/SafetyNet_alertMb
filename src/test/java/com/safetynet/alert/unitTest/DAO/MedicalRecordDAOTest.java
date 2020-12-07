package com.safetynet.alert.unitTest.DAO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alert.DAO.impl.MedicalRecordDAOImpl;
import com.safetynet.alert.DAO.impl.PersonDAOImpl;
import com.safetynet.alert.configuration.DatabaseConfigImpl;
import com.safetynet.alert.models.MedicalRecord;
import com.safetynet.alert.models.Person;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordDAOTest {

	private MedicalRecordDAOImpl medicalRecordDAO;

	private static PersonDAOImpl personDAO;
	private static StringBuilder data;

	@Mock
	private static DatabaseConfigImpl databaseConfig;

	@BeforeAll
	public static void initClassTest() {
		data = new StringBuilder();
		data.append("{\"persons\": [");
		data.append(
				"{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },");
		data.append(
				"{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" },");
		data.append(
				"{ \"firstName\":\"Tenley\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"tenz@email.com\" },],");
		data.append("\"firestations\": [");
		data.append("{ \"address\":\"1509 Culver St\", \"station\":\"3\" },");
		data.append("{ \"address\":\"29 15th St\", \"station\":\"2\" },");
		data.append("{ \"address\":\"834 Binoc Ave\", \"station\":\"3\" },");
		data.append("{ \"address\":\"644 Gershwin Cir\", \"station\":\"1\" },");
		data.append("{ \"address\":\"748 Townings Dr\", \"station\":\"3\" }],");
		data.append("\"medicalrecords\": [");
		data.append(
				"{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] },");
		data.append(
				"{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1989\", \"medications\":[\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\":[] },");
		data.append(
				"{ \"firstName\":\"Tenley\", \"lastName\":\"Boyd\", \"birthdate\":\"02/18/2012\", \"medications\":[], \"allergies\":[\"peanut\"] },]}");
	}

	@BeforeEach
	public void initTest() throws IOException, ParseException {
		when(databaseConfig.openConnection()).thenReturn((JSONObject) new JSONParser().parse(data.toString()));
		personDAO = new PersonDAOImpl(databaseConfig);
		medicalRecordDAO = new MedicalRecordDAOImpl(databaseConfig, personDAO);
	}

	@Tag("MedicalRecordTest")
	@Test
	public void loadDataInDAOConstructorTest() {
		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(3);
	}

	@Tag("MedicalRecordDAOTest")
	@Test
	public void getMedicalRecordTest() {
		assertThat(medicalRecordDAO.getMedicalRecord(1)).isInstanceOf(MedicalRecord.class);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getBirthdate()).isEqualTo("03/06/1984");
		assertThat(medicalRecordDAO.getMedicalRecord(1).getMedications().size()).isEqualTo(2);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getMedications().get(0)).isEqualTo("aznol:350mg");
		assertThat(medicalRecordDAO.getMedicalRecord(1).getMedications().get(1)).isEqualTo("hydrapermazol:100mg");
		assertThat(medicalRecordDAO.getMedicalRecord(1).getAllergies().size()).isEqualTo(1);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getAllergies().get(0)).isEqualTo("nillacilan");

		assertThat(medicalRecordDAO.getMedicalRecord(1)).isInstanceOf(MedicalRecord.class);
		assertThat(medicalRecordDAO.getMedicalRecord(2).getBirthdate()).isEqualTo("03/06/1989");
		assertThat(medicalRecordDAO.getMedicalRecord(2).getMedications().size()).isEqualTo(3);
		assertThat(medicalRecordDAO.getMedicalRecord(2).getMedications().get(0)).isEqualTo("pharmacol:5000mg");
		assertThat(medicalRecordDAO.getMedicalRecord(2).getMedications().get(1)).isEqualTo("terazine:10mg");
		assertThat(medicalRecordDAO.getMedicalRecord(2).getMedications().get(2)).isEqualTo("noznazol:250mg");
		assertThat(medicalRecordDAO.getMedicalRecord(2).getAllergies().size()).isEqualTo(0);

		assertThat(medicalRecordDAO.getMedicalRecord(1)).isInstanceOf(MedicalRecord.class);
		assertThat(medicalRecordDAO.getMedicalRecord(3).getBirthdate()).isEqualTo("02/18/2012");
		assertThat(medicalRecordDAO.getMedicalRecord(3).getMedications().size()).isEqualTo(0);
		assertThat(medicalRecordDAO.getMedicalRecord(3).getAllergies().size()).isEqualTo(1);
		assertThat(medicalRecordDAO.getMedicalRecord(3).getAllergies().get(0)).isEqualTo("peanut");

	}

	@Tag("MedicalRecordDAOTest")
	@Test
	public void getAllMedicalRecordsTest() {
		assertThat(medicalRecordDAO.getAllMedicalRecords()).isInstanceOf(List.class);
		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(3);
		assertThat(medicalRecordDAO.getAllMedicalRecords().get(0).getId()).isEqualTo(1);
		assertThat(medicalRecordDAO.getAllMedicalRecords().get(0).getBirthdate()).isEqualTo("03/06/1984");
		assertThat(medicalRecordDAO.getAllMedicalRecords().get(1).getId()).isEqualTo(2);
		assertThat(medicalRecordDAO.getAllMedicalRecords().get(1).getBirthdate()).isEqualTo("03/06/1989");
		assertThat(medicalRecordDAO.getAllMedicalRecords().get(2).getId()).isEqualTo(3);
		assertThat(medicalRecordDAO.getAllMedicalRecords().get(2).getBirthdate()).isEqualTo("02/18/2012");

		assertThatExceptionOfType(IndexOutOfBoundsException.class)
				.isThrownBy(() -> medicalRecordDAO.getAllMedicalRecords().get(-1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class)
				.isThrownBy(() -> medicalRecordDAO.getAllMedicalRecords().get(3));
	}

	@Tag("MedicalRecordDAOTest")
	@Test
	public void addNewMedicalRecordTest() {
		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(3);
		List<String> newMedicalRecordMedication = new ArrayList<>();
		List<String> newMedicalRecordAllergies = new ArrayList<>();
		MedicalRecord newMedicalRecord = new MedicalRecord(personDAO.getAllPersons().size() + 1, "01/01/1920",
				newMedicalRecordMedication, newMedicalRecordAllergies);
		assertThat(medicalRecordDAO.addNewMedicalRecord(newMedicalRecord)).isFalse();
		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(3);

		Person newPerson = new Person(personDAO.getAllPersons().size() + 1, "Tessa", "Carman", "834 Binoc Ave", "Culver",
				"97451", "841-874-6512", "tenz@email.com");
		personDAO.addNewPerson(newPerson);

		assertThat(medicalRecordDAO.addNewMedicalRecord(newMedicalRecord)).isTrue();
		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(4);
	}

	@Tag("MedicalRecordDAOTest")
	@Test
	public void updateMedicalRecordTest() {
		assertThat(medicalRecordDAO.getMedicalRecord(1)).isInstanceOf(MedicalRecord.class);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getBirthdate()).isEqualTo("03/06/1984");
		assertThat(medicalRecordDAO.getMedicalRecord(1).getMedications().size()).isEqualTo(2);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getMedications().get(0)).isEqualTo("aznol:350mg");
		assertThat(medicalRecordDAO.getMedicalRecord(1).getMedications().get(1)).isEqualTo("hydrapermazol:100mg");
		assertThat(medicalRecordDAO.getMedicalRecord(1).getAllergies().size()).isEqualTo(1);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getAllergies().get(0)).isEqualTo("nillacilan");

		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(3);

		List<String> newMedicationOfMedicalRecord = new ArrayList<>();
		newMedicationOfMedicalRecord.add("thradox:700mg");
		List<String> newAllergiesOfMedicalRecord = new ArrayList<>();
		newAllergiesOfMedicalRecord.add("illisoxian");
		MedicalRecord newInformationsOfMedicalRecord = new MedicalRecord(1, "08/30/1979", newMedicationOfMedicalRecord,
				newAllergiesOfMedicalRecord);
		assertThat(medicalRecordDAO.updateMedicalRecord(newInformationsOfMedicalRecord)).isTrue();

		assertThat(medicalRecordDAO.getMedicalRecord(1)).isInstanceOf(MedicalRecord.class);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getBirthdate()).isEqualTo("08/30/1979");
		assertThat(medicalRecordDAO.getMedicalRecord(1).getMedications().size()).isEqualTo(1);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getMedications().get(0)).isEqualTo("thradox:700mg");
		assertThat(medicalRecordDAO.getMedicalRecord(1).getAllergies().size()).isEqualTo(1);
		assertThat(medicalRecordDAO.getMedicalRecord(1).getAllergies().get(0)).isEqualTo("illisoxian");

		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(3);

		newInformationsOfMedicalRecord.setId(10);
		assertThat(medicalRecordDAO.updateMedicalRecord(newInformationsOfMedicalRecord)).isFalse();

		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(3);
	}

	@Tag("MedicalRecordDAOTest")
	@Test
	void deleteMedicalRecord() {
		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(3);
		medicalRecordDAO.deleteMedicalRecord(1);
		assertThat(medicalRecordDAO.getAllMedicalRecords().size()).isEqualTo(2);
		assertThat(medicalRecordDAO.deleteMedicalRecord(1)).isFalse();
	}

}
