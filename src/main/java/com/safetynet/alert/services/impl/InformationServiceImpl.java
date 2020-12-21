package com.safetynet.alert.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONValue;

import com.safetynet.alert.DAO.FirestationDAO;
import com.safetynet.alert.DAO.MedicalRecordDAO;
import com.safetynet.alert.DAO.PersonDAO;
import com.safetynet.alert.models.Firestation;
import com.safetynet.alert.models.MedicalRecord;
import com.safetynet.alert.models.Person;
import com.safetynet.alert.services.IInformationService;

public class InformationServiceImpl implements IInformationService {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger("PersonInformationService");

	/**
	 * Persons Profiles
	 */
	private PersonDAO personDAO;

	/**
	 * Fire station information
	 */
	private FirestationDAO stationDAO;

	/**
	 * Persons medical records
	 */
	private MedicalRecordDAO medicalRecordDAO;

	public InformationServiceImpl(PersonDAO personDAO, FirestationDAO stationDAO,
			MedicalRecordDAO medicalRecordDAO) {
		super();
		this.personDAO = personDAO;
		this.stationDAO = stationDAO;
		this.medicalRecordDAO = medicalRecordDAO;
	}

	private void deleteLastComma(StringBuilder data) {
		if (data.charAt(data.length() - 1) == ',')
			data.delete(data.length() - 1, data.length());
	}

	public String getPersonById(Integer id) {
		StringBuilder data = new StringBuilder();
		data.append("{\"person\" : [");

		for (Person person : this.personDAO.getAllPersons()) {
			person = personDAO.getPersonsById(id);
			if (person.getId().equals(id)) {
				data.append("{");
				data.append("\"id\" : ").append(person.getId()).append(",");
				data.append("\"firstName\" : \"").append(JSONValue.escape(person.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(person.getLastName())).append("\",");
				data.append("\"address\" : \"").append(JSONValue.escape(person.getAddress())).append("\",");
				data.append("\"city\" : \"").append(JSONValue.escape(person.getCity())).append("\",");
				data.append("\"zip\" : \"").append(JSONValue.escape(person.getZip())).append("\",");
				data.append("\"email\" : \"").append(JSONValue.escape(person.getEmail())).append("\",");
				data.append("\"phone\" : \"").append(JSONValue.escape(person.getPhone())).append("\"");
				data.append("},");

			}

		}

		deleteLastComma(data);
		data.append("]}");
		LOGGER.info(new StringBuffer("GET : A person by name"));
		return data.toString();

	}

	@Override
	public String getAllPersons() {
		StringBuilder data = new StringBuilder();
		data.append("{\"persons\" : [");

		for (Person person : this.personDAO.getAllPersons()) {
			data.append("{");
			data.append("\"id\" : ").append(person.getId()).append(",");
			data.append("\"firstName\" : \"").append(JSONValue.escape(person.getFirstName())).append("\",");
			data.append("\"lastName\" : \"").append(JSONValue.escape(person.getLastName())).append("\",");
			data.append("\"address\" : \"").append(JSONValue.escape(person.getAddress())).append("\",");
			data.append("\"city\" : \"").append(JSONValue.escape(person.getCity())).append("\",");
			data.append("\"zip\" : \"").append(JSONValue.escape(person.getZip())).append("\",");
			data.append("\"email\" : \"").append(JSONValue.escape(person.getEmail())).append("\",");
			data.append("\"phone\" : \"").append(JSONValue.escape(person.getPhone())).append("\"");
			data.append("},");
		}

		deleteLastComma(data);
		data.append("]}");
		LOGGER.info(new StringBuffer("GET : All Persons"));
		return data.toString();
	}

	@Override
	public String getAllFirestations() {
		StringBuilder data = new StringBuilder();
		data.append("{\"stations\" : [");

		for (Firestation firestation : this.stationDAO.getAllStations()) {
			data.append("{");
			data.append("\"number\" : ").append(firestation.getNumberStation()).append(",");
			data.append("\"address\" : \"").append(JSONValue.escape(firestation.getAddress())).append("\"");
			data.append("},");
		}

		deleteLastComma(data);
		data.append("]}");
		LOGGER.info(new StringBuffer("GET : All Firestations"));
		return data.toString();
	}

	@Override
	public String getAllMedicalRecords() {
		StringBuilder data = new StringBuilder();
		data.append("{\"medicalRecords\" : [");

		for (MedicalRecord medicalRecord : this.medicalRecordDAO.getAllMedicalRecords()) {
			data.append("{");
			data.append("\"id\" : ").append(medicalRecord.getId()).append(",");
			data.append("\"birthdate\" : \"").append(JSONValue.escape(medicalRecord.getBirthdate())).append("\",");
			data.append("\"age\" : ").append(medicalRecord.getAge()).append(",");
			data.append("\"medicalRecords\" : {");
			data.append("\"medications\" : [");
			for (String medication : medicalRecord.getMedications()) {
				data.append("\"").append(JSONValue.escape(medication)).append("\",");
			}
			deleteLastComma(data);
			data.append("], \"allergies\" : [");
			for (String allergy : medicalRecord.getAllergies()) {
				data.append("\"").append(JSONValue.escape(allergy)).append("\",");
			}
			deleteLastComma(data);
			deleteLastComma(data);
			data.append("]}},");
		}

		deleteLastComma(data);
		data.append("]}");
		LOGGER.info(new StringBuffer("GET : All Medical Records"));
		return data.toString();
	}

	@Override
	public String getAllPersonsServedByTheStationWithCount(Integer stationNumber) {
		int childCount = 0;
		int adultCount = 0;
		StringBuilder data = new StringBuilder();
		data.append("{\"station\" : " + stationNumber + ",");
		data.append("\"persons\" : [");

		for (Firestation firestation : this.stationDAO.getAllStations()) {
			if (firestation.getNumberStation().equals(stationNumber)) {
				for (Person iPerson : this.personDAO.getAllPersons()) {
					if (iPerson.getAddress().equals(firestation.getAddress())) {
						data.append("{");
						data.append("\"firstName\" : \"").append(JSONValue.escape(iPerson.getFirstName())).append("\",");
						data.append("\"lastName\" : \"").append(JSONValue.escape(iPerson.getLastName())).append("\",");
						data.append("\"address\" : \"").append(JSONValue.escape(iPerson.getAddress())).append("\",");
						data.append("\"phone\" : \"").append(JSONValue.escape(iPerson.getPhone())).append("\"");

						if (this.medicalRecordDAO.getMedicalRecord(iPerson.getId()).getAge() <= 18) {
							childCount++;
						} else
							adultCount++;

						data.append("},");
					}
				}
			}
		}

		deleteLastComma(data);
		data.append("],");

		data.append("\"adultCount\" : ").append(adultCount).append(",");
		data.append("\"childCount\" : ").append(childCount);
		data.append("}");
		LOGGER.info(new StringBuffer("GET : All Persons Served By The Firetation n°").append(stationNumber)
				.append(" With Count"));
		return data.toString();
	}

	@Override
	public String getChildrenByAddress(String address) {
		int childCount = 0;
		StringBuilder data = new StringBuilder();

		data.append("{\"address\" : \"").append(JSONValue.escape(address)).append("\",");
		data.append("\"children\" : [");

		for (Person person : this.personDAO.getAllPersons()) {
			if ((medicalRecordDAO.getMedicalRecord(person.getId()).getAge() <= 18)
					&& (person.getAddress().equals(address))) {
				data.append("{");
				data.append("\"firstName\" : \"").append(JSONValue.escape(person.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(person.getLastName())).append("\",");
				data.append("\"age\" : ").append(medicalRecordDAO.getMedicalRecord(person.getId()).getAge()).append("},");
				childCount++;
			}
		}

		deleteLastComma(data);
		data.append("], \"adults\" : [");

		for (Person person : this.personDAO.getAllPersons()) {
			if ((medicalRecordDAO.getMedicalRecord(person.getId()).getAge() > 18)
					&& (person.getAddress().equals(address))) {
				data.append("{");
				data.append("\"firstName\" : \"").append(JSONValue.escape(person.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(person.getLastName())).append("\"");
				data.append("},");
			}
		}

		deleteLastComma(data);
		data.append("]}");

		LOGGER.info(new StringBuffer("GET : All child for the address : ").append(address).append(", count : ")
				.append(childCount));
		if (childCount == 0) {
			return null;
		}
		return data.toString();
	}

	@Override
	public String getAllPersonsLivingAtTheAddressAndTheNumberStation(String address) {
		StringBuilder data = new StringBuilder();
		MedicalRecord personMedicalRecords;

		data.append("{\"address\" : \"").append(JSONValue.escape(address)).append("\",");
		data.append("\"stationNumber\": ").append(stationDAO.getFirestationByAddress(address).getNumberStation())
				.append(",");
		data.append("\"persons\" : [");

		for (Person person : this.personDAO.getAllPersons()) {
			if (person.getAddress().equals(address)) {
				personMedicalRecords = medicalRecordDAO.getMedicalRecord(person.getId());
				data.append("{");
				data.append("\"firstName\" : \"").append(JSONValue.escape(person.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(person.getLastName())).append("\",");
				data.append("\"phone\" : \"").append(JSONValue.escape(person.getPhone())).append("\",");
				data.append("\"birthdate\" : \"")
						.append((personMedicalRecords != null) ? JSONValue.escape(personMedicalRecords.getBirthdate()) : "")
						.append("\",");
				data.append("\"age\" : ").append((personMedicalRecords != null) ? personMedicalRecords.getAge() : "")
						.append(",");
				data.append("\"medicalRecords\" : {");
				data.append("\"medications\" : [");
				if (personMedicalRecords != null) {
					for (String medication : personMedicalRecords.getMedications()) {
						data.append("\"").append(JSONValue.escape(medication)).append("\",");
					}
					deleteLastComma(data);
				}
				data.append("], \"allergies\" : [");
				if (personMedicalRecords != null) {
					for (String allergy : personMedicalRecords.getAllergies()) {
						data.append("\"").append(JSONValue.escape(allergy)).append("\",");
					}
					deleteLastComma(data);
				}
				deleteLastComma(data);
				data.append("]}},");
			}
		}

		deleteLastComma(data);
		data.append("]}");

		LOGGER.info(new StringBuffer("GET : All Persons Living At The Address : ").append(address));
		return data.toString();
	}

	@Override
	public String getHouseholdListAndPersonsPerAddressWhenFlood(String firestation) {
		StringBuilder data = new StringBuilder();

		List<Integer> firestationNumbers = new ArrayList<>();
		for (String subStr : Arrays.asList(firestation.split("/")))
			firestationNumbers.add(Integer.valueOf(subStr));

		data.append("{\"stations\" : [");
		for (Integer numStation : firestationNumbers) {
			data.append("{\"number\" : ").append(numStation).append(",");
			data.append("\"households\" : [");

			for (Firestation iFirestation : stationDAO.getFirestationByNumber(numStation)) {
				data.append("{ \"address\" : \"").append(JSONValue.escape(iFirestation.getAddress())).append("\",");

				MedicalRecord personMedicalRecords;

				data.append("\"persons\" : [");
				for (Person person : this.personDAO.getAllPersons()) {
					if (person.getAddress().equals(iFirestation.getAddress())) {
						personMedicalRecords = this.medicalRecordDAO.getMedicalRecord(person.getId());
						data.append("{");
						data.append("\"firstName\" : \"").append(JSONValue.escape(person.getFirstName())).append("\",");
						data.append("\"lastName\" : \"").append(JSONValue.escape(person.getLastName())).append("\",");
						data.append("\"phone\" : \"").append(JSONValue.escape(person.getPhone())).append("\",");
						data.append("\"birthdate\" : \"").append(
								(personMedicalRecords != null) ? JSONValue.escape(personMedicalRecords.getBirthdate()) : "")
								.append("\",");
						data.append("\"age\" : ").append((personMedicalRecords != null) ? personMedicalRecords.getAge() : "")
								.append(",");
						data.append("\"medicalRecords\" : {");
						data.append("\"medications\" : [");
						if (personMedicalRecords != null) {
							for (String medication : personMedicalRecords.getMedications()) {
								data.append("\"").append(JSONValue.escape(medication)).append("\",");
							}
							deleteLastComma(data);
						}
						data.append("], \"allergies\" : [");
						if (personMedicalRecords != null) {
							for (String allergy : personMedicalRecords.getAllergies()) {
								data.append("\"").append(JSONValue.escape(allergy)).append("\",");
							}
							deleteLastComma(data);
						}
						data.append("]}},");
					}
				}
				deleteLastComma(data);
				data.append("]},");
			}
			deleteLastComma(data);
			data.append("]},");
		}
		deleteLastComma(data);
		data.append("]}");

		LOGGER.info(new StringBuffer("GET : All Persons Served By The Stations : ").append(firestation));
		return data.toString();
	}

	@Override
	public String getAllPersonsPhoneByStationNumber(Integer stationNumber) {
		StringBuilder data = new StringBuilder();
		data.append("{\"station\": ").append(stationNumber).append(",");
		data.append("\"phones\" : [");

		for (Firestation firestation : this.stationDAO.getAllStations()) {
			if (firestation.getNumberStation().equals(stationNumber)) {
				for (Person iPerson : this.personDAO.getAllPersons()) {
					if (iPerson.getAddress().equals(firestation.getAddress())) {
						data.append("\"").append(JSONValue.escape(iPerson.getPhone())).append("\",");
					}
				}
			}
		}

		deleteLastComma(data);
		data.append("]}");

		LOGGER.info(new StringBuffer("GET : All Persons Phone By Firestation Number : ").append(stationNumber));
		return data.toString();
	}

	@Override
	public String getAllCompleteProfileOfPersonsByName(String firstName, String lastName) {
		StringBuilder data = new StringBuilder();
		MedicalRecord personMedicalRecords;

		data.append("{\"persons\" : [");

		for (Person person : this.personDAO.getAllPersons()) {
			if (person.getFirstName().equals(person.getFirstName()) && person.getLastName().equals(person.getLastName())) {
				personMedicalRecords = medicalRecordDAO.getMedicalRecord(person.getId());
				data.append("{");
				data.append("\"id\" : ").append(person.getId()).append(",");
				data.append("\"firstName\" : \"").append(JSONValue.escape(person.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(person.getLastName())).append("\",");
				data.append("\"address\" : \"").append(JSONValue.escape(person.getAddress())).append("\",");
				data.append("\"city\" : \"").append(JSONValue.escape(person.getCity())).append("\",");
				data.append("\"zip\" : \"").append(JSONValue.escape(person.getZip())).append("\",");
				data.append("\"email\" : \"").append(JSONValue.escape(person.getEmail())).append("\",");
				data.append("\"phone\" : \"").append(JSONValue.escape(person.getPhone())).append("\",");
				data.append("\"birthdate\" : \"")
						.append((personMedicalRecords != null) ? JSONValue.escape(personMedicalRecords.getBirthdate()) : "")
						.append("\",");
				data.append("\"age\" : ").append((personMedicalRecords != null) ? personMedicalRecords.getAge() : "")
						.append(",");
				data.append("\"medicalRecords\" : {");
				data.append("\"medications\" : [");
				if (personMedicalRecords != null) {
					for (String medication : personMedicalRecords.getMedications()) {
						data.append("\"").append(JSONValue.escape(medication)).append("\",");
					}
					deleteLastComma(data);
				}
				data.append("], \"allergies\" : [");
				if (personMedicalRecords != null) {
					for (String allergy : personMedicalRecords.getAllergies()) {
						data.append("\"").append(JSONValue.escape(allergy)).append("\",");
					}
					deleteLastComma(data);
				}
				data.append("]}},");
			}
		}
		deleteLastComma(data);
		data.append("]}");

		LOGGER.info(new StringBuffer("GET : All Complete Profile Of Persons By Name : ").append(firstName).append(" ")
				.append(lastName));
		return data.toString();
	}

	@Override
	public String getAllPersonsEmailByCity(String city) {
		StringBuilder data = new StringBuilder();
		data.append("{\"city\": ").append(city).append(",");
		data.append("\"emails\" : [");

		for (Person person : this.personDAO.getAllPersons()) {
			if (person.getCity().equals(person.getCity())) {
				data.append("\"").append(JSONValue.escape(person.getEmail())).append("\",");
			}
		}

		deleteLastComma(data);
		data.append("]}");

		LOGGER.info(new StringBuffer("GET : All Persons Email By City : ").append(city));
		return data.toString();
	}

}
