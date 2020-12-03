package com.safetynet.alert.unitTest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alert.DAO.MedicalRecordDAOImpl;
import com.safetynet.alert.models.MedicalRecord;
import com.safetynet.alert.services.MedicalRecordServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

	private MedicalRecord medicalRecord;
	private MedicalRecordServiceImpl medicalRecordsService;

	@Mock
	private static MedicalRecordDAOImpl medicalRecordDAO;

	@BeforeEach
	void setUp() {
		medicalRecord = new MedicalRecord(0, "", new ArrayList<>(), new ArrayList<>());
		medicalRecordsService = new MedicalRecordServiceImpl(medicalRecordDAO);
	}

	@Tag("MedicalRecordsService")
	@Test
	void httpPostIfMedicalRecordIsNull() {
		assertThatExceptionOfType(NullPointerException.class)
				.isThrownBy(() -> medicalRecordsService.httpPostMedicalRecord(null));
		verify(medicalRecordDAO, never()).addNewMedicalRecord(any(MedicalRecord.class));
	}

	@Tag("MedicalRecordsService")
	@Test
	void httpPostIfDAOIstrue() {
		when(medicalRecordDAO.addNewMedicalRecord(medicalRecord)).thenReturn(true);
		assertThat(medicalRecordsService.httpPostMedicalRecord(medicalRecord)).isEqualTo("Medical record added");
		verify(medicalRecordDAO, times(1)).addNewMedicalRecord(medicalRecord);
	}

	@Tag("MedicalRecordsService")
	@Test
	void httpPostIfDAOIsfalse() {
		when(medicalRecordDAO.addNewMedicalRecord(medicalRecord)).thenReturn(false);
		assertThat(medicalRecordsService.httpPostMedicalRecord(medicalRecord))
				.isEqualTo("Error : This Medical record can't be added");
		verify(medicalRecordDAO, times(1)).addNewMedicalRecord(medicalRecord);
	}

	@Tag("MedicalRecordsService")
	@Test
	void httpPutIfMedicalRecordIsNull() {
		assertThatExceptionOfType(NullPointerException.class)
				.isThrownBy(() -> medicalRecordsService.httpPutMedicalRecord(null));
		verify(medicalRecordDAO, never()).updateMedicalRecord(any(MedicalRecord.class));
	}

	@Tag("MedicalRecordsService")
	@Test
	void httpPutIfDAOIstrue() {
		when(medicalRecordDAO.updateMedicalRecord(medicalRecord)).thenReturn(true);
		assertThat(medicalRecordsService.httpPutMedicalRecord(medicalRecord)).isEqualTo("Medical record updated");
		verify(medicalRecordDAO, times(1)).updateMedicalRecord(medicalRecord);
	}

	@Tag("MedicalRecordsService")
	@Test
	void httpPutIfDAOIsfalse() {
		when(medicalRecordDAO.updateMedicalRecord(medicalRecord)).thenReturn(false);
		assertThat(medicalRecordsService.httpPutMedicalRecord(medicalRecord))
				.isEqualTo("Error : This Medical record can't be updated");
		verify(medicalRecordDAO, times(1)).updateMedicalRecord(medicalRecord);
	}

	@Tag("MedicalRecordsService")
	@Test
	void httpDeleteIfDAOIstrue() {
		when(medicalRecordDAO.deleteMedicalRecord(0)).thenReturn(true);
		assertThat(medicalRecordsService.httpDeleteMedicalRecord(0)).isEqualTo("Medical record deleted");
		verify(medicalRecordDAO, times(1)).deleteMedicalRecord(0);
	}

	@Tag("MedicalRecordsService")
	@Test
	void httpDeleteIfDAOIsfalse() {
		when(medicalRecordDAO.deleteMedicalRecord(0)).thenReturn(false);
		assertThat(medicalRecordsService.httpDeleteMedicalRecord(0))
				.isEqualTo("Error : This Medical record can't be deleted");
		verify(medicalRecordDAO, times(1)).deleteMedicalRecord(0);
	}

	@AfterEach
	void tearDown() {
		medicalRecord = null;
		medicalRecordsService = null;
	}

}
