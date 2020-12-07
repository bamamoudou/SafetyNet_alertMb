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

import com.safetynet.alert.DAO.impl.FirestationDAOImpl;
import com.safetynet.alert.models.Firestation;
import com.safetynet.alert.service.impl.FirestationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

	private Firestation firestation;

	private FirestationServiceImpl firestationService;

	@Mock
	private static FirestationDAOImpl firestationDAO;

	@BeforeEach
	public void setUp() {
		firestation = new Firestation(0, "");
		firestationService = new FirestationServiceImpl(firestationDAO);
	}

	@Tag("StationServiceTest")
	@Test
	public void httpPostIfFirestationIsNull() {
		assertThatExceptionOfType(NullPointerException.class)
				.isThrownBy(() -> firestationService.httpPostFirestation(null));
		verify(firestationDAO, never()).addNewFirestation(any(Firestation.class));
	}

	@Tag("StationServiceTest")
	@Test
	public void httpPostIfDAOIstrue() {
		when(firestationDAO.addNewFirestation(firestation)).thenReturn(true);
		assertThat(firestationService.httpPostFirestation(firestation)).isEqualTo("Station mapping added");
		verify(firestationDAO, times(1)).addNewFirestation(firestation);
	}

	@Tag("StationServiceTest")
	@Test
	public void httpPostIfDAOIsfalse() {
		when(firestationDAO.addNewFirestation(firestation)).thenReturn(false);
		assertThat(firestationService.httpPostFirestation(firestation))
				.isEqualTo("Error : This Station mapping can't be added");
		verify(firestationDAO, times(1)).addNewFirestation(firestation);
	}

	@Tag("StationServiceTest")
	@Test
	public void httpPutIfFirestationIsNull() {
		assertThatExceptionOfType(NullPointerException.class)
				.isThrownBy(() -> firestationService.httpPutFirestation(null));
		verify(firestationDAO, never()).updateFirestation(any(Firestation.class));
	}

	@Tag("StationServiceTest")
	@Test
	public void httpPutIfDAOIstrue() {
		when(firestationDAO.updateFirestation(firestation)).thenReturn(true);
		assertThat(firestationService.httpPutFirestation(firestation)).isEqualTo("Station mapping updated");
		verify(firestationDAO, times(1)).updateFirestation(firestation);
	}

	@Tag("StationServiceTest")
	@Test
	public void httpPutIfDAOIsfalse() {
		when(firestationDAO.updateFirestation(firestation)).thenReturn(false);
		assertThat(firestationService.httpPutFirestation(firestation))
				.isEqualTo("Error : This Station mapping can't be updated");
		verify(firestationDAO, times(1)).updateFirestation(firestation);
	}

	@Tag("StationServiceTest")
	@Test
	public void httpDeleteIfDAOIstrue() {
		when(firestationDAO.deleteFirestationByNumber(0)).thenReturn(true);
		assertThat(firestationService.httpDeleteFirestation(0)).isEqualTo("Station deleted");
		verify(firestationDAO, times(1)).deleteFirestationByNumber(0);
	}

	@Tag("StationServiceTest")
	@Test
	public void httpDeleteIfDAOIsfalse() {
		when(firestationDAO.deleteFirestationByNumber(0)).thenReturn(false);
		assertThat(firestationService.httpDeleteFirestation(0)).isEqualTo("Error : This Station can't be deleted");
		verify(firestationDAO, times(1)).deleteFirestationByNumber(0);
	}

	@Tag("StationServiceTest")
	@Test
	public void httpDeleteMappingIfDAOIstrue() {
		when(firestationDAO.deleteFirestationMapping("")).thenReturn(true);
		assertThat(firestationService.httpDeleteMapping("")).isEqualTo("Station mapping deleted");
		verify(firestationDAO, times(1)).deleteFirestationMapping("");
	}

	@Tag("StationServiceTest")
	@Test
	public void httpDeleteMappingIfDAOIsfalse() {
		when(firestationDAO.deleteFirestationMapping("")).thenReturn(false);
		assertThat(firestationService.httpDeleteMapping("")).isEqualTo("Error : This Station mapping can't be deleted");
		verify(firestationDAO, times(1)).deleteFirestationMapping("");
	}

	@AfterEach
	public void tearDown() {
		firestation = null;
		firestationService = null;
	}

}
