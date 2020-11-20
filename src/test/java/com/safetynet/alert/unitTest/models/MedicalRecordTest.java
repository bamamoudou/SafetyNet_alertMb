package com.safetynet.alert.unitTest.models;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.alert.models.MedicalRecord;

public class MedicalRecordTest {

	private MedicalRecord medicalRecord;
	List<String> medications;
	List<String> allergies;

	@BeforeEach
	void initTest() {
		medications = new ArrayList<>();
		allergies = new ArrayList<>();

		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		allergies.add("nillacilan");

		medicalRecord = new MedicalRecord(1, "03/06/1984", medications, allergies);
	}

	@Tag("MedicalRecordTest")
	@Test
	void getterTest() {
		assertThat(medicalRecord.getId()).isEqualTo(1);
		assertThat(medicalRecord.getBirthdate()).isEqualTo("03/06/1984");
		assertThat(medicalRecord.getMedications()).isEqualTo(medications);
		assertThat(medicalRecord.getAllergies()).isEqualTo(allergies);
	}

	@Tag("MedicalRecordTest")
	@Test
	void getterAndSetterTest() {
		List<String> newMedication = new ArrayList<>();
		newMedication.add("dodoxadin:30mg");
		List<String> newAllergies = new ArrayList<>();
		newAllergies.add("shellfish");

		medicalRecord.setId(2);
		medicalRecord.setBirthdate("03/06/1989");
		medicalRecord.setMedications(newMedication);
		medicalRecord.setAllergies(newAllergies);

		assertThat(medicalRecord.getId()).isEqualTo(2);
		assertThat(medicalRecord.getBirthdate()).isEqualTo("03/06/1989");
		assertThat(medicalRecord.getMedications()).isEqualTo(newMedication);
		assertThat(medicalRecord.getAllergies()).isEqualTo(newAllergies);
	}

	@Tag("MedicalRecordTest")
	@Test
	void setMedicalRecordAsNull() {
		medicalRecord.setId(0);
		medicalRecord.setBirthdate("");
		medicalRecord.setMedications(new ArrayList<>());
		medicalRecord.setAllergies(new ArrayList<>());

		assertThat(medicalRecord.getId()).isEqualTo(0);
		assertThat(medicalRecord.getBirthdate()).isEmpty();
		assertThat(medicalRecord.getMedications()).isEmpty();
		assertThat(medicalRecord.getAllergies()).isEmpty();
	}

	@AfterEach
	void undefinedTest() {
		medicalRecord = null;
	}

}
