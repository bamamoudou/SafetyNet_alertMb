package com.safetynet.alert.unitTest.DAO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.io.IOException;
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

import com.safetynet.alert.DAO.impl.FirestationDAOImpl;
import com.safetynet.alert.configuration.DatabaseConfigImpl;
import com.safetynet.alert.models.Firestation;

@ExtendWith(MockitoExtension.class)
public class FirestationDAOTest {

	private FirestationDAOImpl firestation;

	private static StringBuilder data;

	@Mock
	private static DatabaseConfigImpl databaseConfig;

	@BeforeAll
	public static void initTestClass() {
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
		firestation = new FirestationDAOImpl(databaseConfig);

	}

	@Tag("FirestationDAOTest")
	@Test
	public void loadDataInDAOConstructor() {
		assertThat(firestation.getAllStations().size()).isEqualTo(5);
	}

	@Tag("FirestationDAOTest")
	@Test
	public void getStationByNumber() {
		assertThat(firestation.getFirestationByNumber(1)).isInstanceOf(List.class);
		assertThat(firestation.getFirestationByNumber(1).size()).isEqualTo(1);
		assertThat(firestation.getFirestationByNumber(1).get(0).getNumberStation()).isEqualTo(1);
		assertThat(firestation.getFirestationByNumber(1).get(0).getAddress()).isEqualTo("644 Gershwin Cir");
		assertThat(firestation.getFirestationByNumber(1).size()).isEqualTo(1);
		
		
	}

	@Tag("FirestationDAOTest")
	@Test
	public void getStationByAddress() {
		assertThat(firestation.getFirestationByAddress("1509 Culver St")).isInstanceOf(Firestation.class);
		assertThat(firestation.getFirestationByAddress("1509 Culver St").getNumberStation()).isEqualTo(3);

		assertThat(firestation.getFirestationByAddress("951 LoneTree Rd")).isNull();
	}

	@Tag("FirestationDAOTest")
	@Test
	public void getAllStations() {
		assertThat(firestation.getAllStations()).isInstanceOf(List.class);
		assertThat(firestation.getAllStations().size()).isEqualTo(5);
		assertThat(firestation.getAllStations().get(0).getNumberStation()).isEqualTo(3);
		assertThat(firestation.getAllStations().get(0).getAddress()).isEqualTo("1509 Culver St");

		assertThat(firestation.getAllStations().get(1).getNumberStation()).isEqualTo(2);
		assertThat(firestation.getAllStations().get(1).getAddress()).isEqualTo("29 15th St");

		assertThat(firestation.getAllStations().get(2).getNumberStation()).isEqualTo(3);
		assertThat(firestation.getAllStations().get(2).getAddress()).isEqualTo("834 Binoc Ave");

		assertThat(firestation.getAllStations().get(3).getNumberStation()).isEqualTo(1);
		assertThat(firestation.getAllStations().get(3).getAddress()).isEqualTo("644 Gershwin Cir");

		assertThat(firestation.getAllStations().get(4).getNumberStation()).isEqualTo(3);
		assertThat(firestation.getAllStations().get(4).getAddress()).isEqualTo("748 Townings Dr");

		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> firestation.getAllStations().get(-1));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> firestation.getAllStations().get(5));
	}

	@Tag("FirestationDAOTest")
	@Test
	public void addNewStation() {
		assertThat(firestation.getAllStations().size()).isEqualTo(5);

		assertThat(firestation.addNewFirestation(new Firestation(0, ""))).isFalse();

		assertThat(firestation.getAllStations().size()).isEqualTo(5);

		assertThat(firestation.addNewFirestation(new Firestation(99, "951 LoneTree Rd"))).isTrue();

		assertThat(firestation.getAllStations().size()).isEqualTo(6);
		assertThat(firestation.getFirestationByAddress("951 LoneTree Rd").getNumberStation()).isEqualTo(99);
	}

	@Tag("FirestationDAOTest")
	@Test
	public void updateStation() {
		assertThat(firestation.getFirestationByAddress("834 Binoc Ave")).isInstanceOf(Firestation.class);
		assertThat(firestation.getFirestationByAddress("834 Binoc Ave").getNumberStation()).isEqualTo(3);

		assertThat(firestation.getAllStations().size()).isEqualTo(5);

		firestation.updateFirestation(new Firestation(12, "834 Binoc Ave"));

		assertThat(firestation.getAllStations().size()).isEqualTo(5);

		assertThat(firestation.getFirestationByAddress("834 Binoc Ave").getNumberStation()).isEqualTo(12);
	}

	@Tag("FirestationDAOTest")
	@Test
	public void deleteStationMapping() {
		assertThat(firestation.getAllStations().size()).isEqualTo(5);
		firestation.deleteFirestationMapping("1509 Culver St");
		assertThat(firestation.getAllStations().size()).isEqualTo(4);
		assertThat(firestation.deleteFirestationMapping("1509 Culver St")).isFalse();

	}

	@Tag("FirestationDAOTest")
	@Test
	public void deleteStationByNumber() {
		assertThat(firestation.getAllStations().size()).isEqualTo(5);
		firestation.deleteFirestationByNumber(3);
		assertThat(firestation.getAllStations().size()).isEqualTo(2);
		assertThat(firestation.deleteFirestationByNumber(3)).isFalse();
		assertThat(firestation.deleteFirestationByNumber(-1)).isFalse();
		assertThat(firestation.deleteFirestationByNumber(10)).isFalse();
	}

}
