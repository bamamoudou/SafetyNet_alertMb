package com.safetynet.alert.unitTest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alert.DAO.FirestationDAO;
import com.safetynet.alert.DAO.MedicalRecordDAO;
import com.safetynet.alert.DAO.PersonDAO;
import com.safetynet.alert.models.Firestation;
import com.safetynet.alert.models.MedicalRecord;
import com.safetynet.alert.models.Person;
import com.safetynet.alert.services.InformationService;

@ExtendWith(MockitoExtension.class)
public class InformationServiceTest {

	private List<Person> persons;
	private List<Firestation> stations;
	private List<MedicalRecord> medicalRecords;
	private String data;
	private InformationService informationService;

	@Mock
	private static PersonDAO personDAO;

	@Mock
	private static MedicalRecordDAO medicalRecordDAO;

	@Mock
	private static FirestationDAO stationDAO;

	@BeforeEach
	public void initTest() {
		informationService = new InformationService(personDAO, stationDAO, medicalRecordDAO);

		persons = new ArrayList<>();
		persons.add(
				new Person(1, "John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
		persons.add(new Person(2, "Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"));
		persons.add(
				new Person(3, "Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
		persons.add(
				new Person(4, "Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
		persons.add(
				new Person(5, "Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544", "jaboyd@email.com"));
		persons.add(
				new Person(6, "Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com"));
		persons.add(
				new Person(7, "Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512", "tenz@email.com"));
		persons.add(new Person(8, "Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com"));

		stations = new ArrayList<>();
		stations.add(new Firestation(3, "1509 Culver St"));
		stations.add(new Firestation(2, "29 15th St"));
		stations.add(new Firestation(3, "834 Binoc Ave"));
		stations.add(new Firestation(1, "644 Gershwin Cir"));
		stations.add(new Firestation(3, "748 Townings Dr"));
		stations.add(new Firestation(3, "112 Steppes Pl"));

		medicalRecords = new ArrayList<>();
		List<String> medications = new ArrayList<>();
		List<String> allergies = new ArrayList<>();
		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		allergies.add("nillacilan");
		medicalRecords.add(new MedicalRecord(1, "03/06/1984", medications, allergies));
		medicalRecords.add(new MedicalRecord(2, "03/06/1989", medications, allergies));
		medicalRecords.add(new MedicalRecord(3, "02/18/2012", medications, allergies));
		medicalRecords.add(new MedicalRecord(4, "09/06/2017", medications, allergies));
		medicalRecords.add(new MedicalRecord(5, "01/08/1986", medications, allergies));
		medicalRecords.add(new MedicalRecord(6, "01/03/1989", medications, allergies));
		medicalRecords.add(new MedicalRecord(7, "02/18/2012", medications, allergies));
		medicalRecords.add(new MedicalRecord(8, "09/06/2000", medications, allergies));
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllPersonsTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		data = informationService.getAllPersons();
		verify(personDAO, times(1)).getAllPersons();

		assertThat(data).isNotEmpty();

		JSONObject result = new JSONObject(data);
		JSONArray persons = (JSONArray) result.get("persons");
		assertThat(persons.length()).isEqualTo(8);
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllFirestationsTest() throws JSONException {
		when(stationDAO.getAllStations()).thenReturn(stations);
		data = informationService.getAllFirestations();
		verify(stationDAO, times(1)).getAllStations();

		assertThat(data).isNotEmpty();

		JSONObject result = new JSONObject(data);
		JSONArray stations = (JSONArray) result.get("stations");
		assertThat(stations.length()).isEqualTo(6);
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllMedicalRecordsTest() throws JSONException {
		when(medicalRecordDAO.getAllMedicalRecords()).thenReturn(medicalRecords);
		data = informationService.getAllMedicalRecords();
		verify(medicalRecordDAO, times(1)).getAllMedicalRecords();

		assertThat(data).isNotEmpty();

		JSONObject result = new JSONObject(data);
		JSONArray medicalRecords = (JSONArray) result.get("medicalRecords");
		assertThat(medicalRecords.length()).isEqualTo(8);
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllPersonsServedByTheStationWithCountMoreThanEightTeenTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(stationDAO.getAllStations()).thenReturn(stations);
		when(medicalRecordDAO.getMedicalRecord(any(Integer.class))).thenReturn(medicalRecords.get(0));
		data = informationService.getAllPersonsServedByTheStationWithCount(3);
		verify(stationDAO, times(1)).getAllStations();
		verify(personDAO, times(4)).getAllPersons();

		assertThat(data).isNotEmpty();

		JSONObject result = new JSONObject(data);
		Integer stationNumber = (Integer) result.get("station");
		JSONArray persons = (JSONArray) result.get("persons");
		Integer adultCount = (Integer) result.get("adultCount");
		Integer childCount = (Integer) result.get("childCount");

		assertThat(stationNumber).isEqualTo(3);
		assertThat(persons.length()).isEqualTo(6);
		assertThat(adultCount).isEqualTo(6);
		assertThat(childCount).isZero();
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllPersonsServedByTheStationWithCountLessThanEightTeenTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(stationDAO.getAllStations()).thenReturn(stations);
		when(medicalRecordDAO.getMedicalRecord(any(Integer.class))).thenReturn(medicalRecords.get(2));
		data = informationService.getAllPersonsServedByTheStationWithCount(3);
		verify(stationDAO, times(1)).getAllStations();
		verify(personDAO, times(4)).getAllPersons();

		assertThat(data).isNotEmpty();

		JSONObject result = new JSONObject(data);
		Integer stationNumber = (Integer) result.get("station");
		JSONArray persons = (JSONArray) result.get("persons");
		Integer adultCount = (Integer) result.get("adultCount");
		Integer childCount = (Integer) result.get("childCount");

		assertThat(stationNumber).isEqualTo(3);
		assertThat(persons.length()).isEqualTo(6);
		assertThat(adultCount).isZero();
		assertThat(childCount).isEqualTo(6);
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllChildByAddressMoreThanEightTeenTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(medicalRecordDAO.getMedicalRecord(any(Integer.class))).thenReturn(medicalRecords.get(0));
		data = informationService.getAllChildByAddress("1509 Culver St");
		verify(personDAO, times(2)).getAllPersons();

		assertThat(data).isNull();
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllChildByAddressLessThanEightTeenTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(medicalRecordDAO.getMedicalRecord(anyInt())).thenReturn(medicalRecords.get(2));
		data = informationService.getAllChildByAddress("1509 Culver St");
		verify(personDAO, times(2)).getAllPersons();

		assertThat(data).isNotEmpty();

		JSONObject result = new JSONObject(data);
		String address = (String) result.get("address");
		JSONArray childs = (JSONArray) result.get("childs");
		JSONArray adults = (JSONArray) result.get("adults");

		assertThat(address).isEqualTo("1509 Culver St");
		assertThat(childs.length()).isEqualTo(5);
		assertThat(adults.length()).isZero();
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllPersonsPhoneByStationNumberOneTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(stationDAO.getAllStations()).thenReturn(stations);
		data = informationService.getAllPersonsPhoneByStationNumber(1);
		verify(personDAO, times(1)).getAllPersons();
		verify(stationDAO, times(1)).getAllStations();

		assertThat(data).isNotEmpty();

		JSONObject result = new JSONObject(data);
		Integer stationNumber = (Integer) result.get("station");
		JSONArray phones = (JSONArray) result.get("phones");

		assertThat(stationNumber).isEqualTo(1);
		assertThat(phones.length()).isEqualTo(1);
		assertThat(phones.get(0)).isEqualTo("841-874-6512");

	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllPersonsPhoneByStationNumberThreeTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(stationDAO.getAllStations()).thenReturn(stations);
		data = informationService.getAllPersonsPhoneByStationNumber(3);
		verify(personDAO, times(4)).getAllPersons();
		verify(stationDAO, times(1)).getAllStations();

		assertThat(data).isNotEmpty();

		JSONObject result = new JSONObject(data);
		Integer stationNumber = (Integer) result.get("station");
		JSONArray phones = (JSONArray) result.get("phones");

		assertThat(stationNumber).isEqualTo(3);
		assertThat(phones.length()).isEqualTo(6);
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllPersonsLivingAtTheAddressTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(stationDAO.getFirestationByAddress(anyString())).thenReturn(stations.get(0));
		when(medicalRecordDAO.getMedicalRecord(anyInt())).thenReturn(medicalRecords.get(0));
		data = informationService.getAllPersonsLivingAtTheAddress("1509 Culver St");
		verify(personDAO, times(1)).getAllPersons();
		verify(stationDAO, times(1)).getFirestationByAddress(anyString());
		verify(medicalRecordDAO, times(5)).getMedicalRecord(anyInt());

		JSONObject result = new JSONObject(data);
		Integer stationNumber = (Integer) result.get("station");
		String address = (String) result.get("address");
		JSONArray persons = (JSONArray) result.get("persons");

		assertThat(stationNumber).isEqualTo(3);
		assertThat(address).isEqualTo("1509 Culver St");
		assertThat(persons.length()).isEqualTo(5);
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllPersonsServedByTheStationsTest() throws JSONException {
		List<Firestation> listOfStation = new ArrayList<>();

		for (Firestation iStation : stations) {
			if (iStation.getNumberStation().equals(3)) {
				listOfStation.add(iStation);
			}
		}

		when(stationDAO.getFirestationByNumber(anyInt())).thenReturn(listOfStation);
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(medicalRecordDAO.getMedicalRecord(anyInt())).thenReturn(medicalRecords.get(0));
		data = informationService.getAllPersonsServedByTheStations("3");
		verify(stationDAO, times(1)).getFirestationByNumber(anyInt());
		verify(personDAO, times(4)).getAllPersons();
		verify(medicalRecordDAO, times(6)).getMedicalRecord(anyInt());

		JSONObject result = new JSONObject(data);
		JSONArray stations = (JSONArray) result.get("stations");
		JSONObject station = (JSONObject) stations.get(0);
		Integer number = (Integer) station.get("number");
		JSONArray homes = (JSONArray) station.get("homes");
		JSONObject home = (JSONObject) homes.get(0);
		String address = (String) home.get("address");
		JSONArray persons = (JSONArray) home.get("persons");

		assertThat(stations.length()).isEqualTo(1);
		assertThat(number).isEqualTo(3);
		assertThat(homes.length()).isEqualTo(4);
		assertThat(address).isEqualTo("1509 Culver St");
		assertThat(persons.length()).isEqualTo(5);
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllCompleteProfileOfPersonsByNameTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		when(medicalRecordDAO.getMedicalRecord(anyInt())).thenReturn(medicalRecords.get(0));
		data = informationService.getAllCompleteProfileOfPersonsByName("John", "Boyd");
		verify(personDAO, times(1)).getAllPersons();
		verify(medicalRecordDAO, times(1)).getMedicalRecord(anyInt());

		JSONObject result = new JSONObject(data);
		JSONArray persons = (JSONArray) result.get("persons");
		JSONObject person = (JSONObject) persons.get(0);
		JSONObject JSONmedicalRecords = (JSONObject) person.get("medicalRecords");
		JSONArray medications = (JSONArray) JSONmedicalRecords.get("medications");
		JSONArray allergies = (JSONArray) JSONmedicalRecords.get("allergies");

		assertThat(persons.length()).isEqualTo(1);
		assertThat((String) person.get("firstName")).isEqualTo("John");
		assertThat((String) person.get("lastName")).isEqualTo("Boyd");
		assertThat((String) person.get("address")).isEqualTo("1509 Culver St");
		assertThat((String) person.get("city")).isEqualTo("Culver");
		assertThat((String) person.get("zip")).isEqualTo("97451");
		assertThat((String) person.get("email")).isEqualTo("jaboyd@email.com");
		assertThat((String) person.get("phone")).isEqualTo("841-874-6512");
		assertThat((String) person.get("birthdate")).isEqualTo("03/06/1984");
		assertThat((Integer) person.get("age")).isEqualTo(36);
		assertThat(medications.length()).isEqualTo(2);
		assertThat(allergies.length()).isEqualTo(1);
	}

	@Tag("InformationServiceTest")
	@Test
	public void getAllPersonsEmailByCityTest() throws JSONException {
		when(personDAO.getAllPersons()).thenReturn(persons);
		data = informationService.getAllPersonsEmailByCity("Culver");
		verify(personDAO, times(1)).getAllPersons();

		assertThat(data).isNotEmpty();

		JSONArray emails = (JSONArray) new JSONObject(data).get("emails");
		assertThat(emails.length()).isEqualTo(8);
		assertThat(emails.get(5)).isEqualTo("drk@email.com");
	}

	@AfterEach
	public void tearDown() {
		persons = null;
		stations = null;
		medicalRecords = null;
		data = null;
		informationService = null;
	}

}
