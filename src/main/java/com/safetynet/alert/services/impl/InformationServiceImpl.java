package com.safetynet.alert.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONValue;

import com.safetynet.alert.DAO.impl.FirestationDAOImpl;
import com.safetynet.alert.DAO.impl.MedicalRecordDAOImpl;
import com.safetynet.alert.DAO.impl.PersonDAOImpl;
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
	private PersonDAOImpl personDAO;

	/**
	 * Fire station information
	 */
	private FirestationDAOImpl stationDAO;

	/**
	 * Persons medical records
	 */
	private MedicalRecordDAOImpl medicalRecordDAO;

	public InformationServiceImpl(PersonDAOImpl personDAO, FirestationDAOImpl stationDAO, MedicalRecordDAOImpl medicalRecordDAO) {
		super();
		this.personDAO = personDAO;
		this.stationDAO = stationDAO;
		this.medicalRecordDAO = medicalRecordDAO;
	}

	private void deleteLastComma(StringBuilder data) {
		if (data.charAt(data.length() - 1) == ',')
			data.delete(data.length() - 1, data.length());
	}

	@Override
	public String getAllPersons() {
		StringBuilder data = new StringBuilder();
		data.append("{\"persons\" : [");

		for (Person iPerson : this.personDAO.getAllPersons()) {
			data.append("{");
			data.append("\"id\" : ").append(iPerson.getId()).append(",");
			data.append("\"firstName\" : \"").append(JSONValue.escape(iPerson.getFirstName())).append("\",");
			data.append("\"lastName\" : \"").append(JSONValue.escape(iPerson.getLastName())).append("\",");
			data.append("\"address\" : \"").append(JSONValue.escape(iPerson.getAddress())).append("\",");
			data.append("\"city\" : \"").append(JSONValue.escape(iPerson.getCity())).append("\",");
			data.append("\"zip\" : \"").append(JSONValue.escape(iPerson.getZip())).append("\",");
			data.append("\"email\" : \"").append(JSONValue.escape(iPerson.getEmail())).append("\",");
			data.append("\"phone\" : \"").append(JSONValue.escape(iPerson.getPhone())).append("\"");
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

		for (Firestation iFirestation : this.stationDAO.getAllStations()) {
			data.append("{");
			data.append("\"number\" : ").append(iFirestation.getNumberStation()).append(",");
			data.append("\"address\" : \"").append(JSONValue.escape(iFirestation.getAddress())).append("\"");
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

		for (MedicalRecord iMedicalRecord : this.medicalRecordDAO.getAllMedicalRecords()) {
			data.append("{");
			data.append("\"id\" : ").append(iMedicalRecord.getId()).append(",");
			data.append("\"birthdate\" : \"").append(JSONValue.escape(iMedicalRecord.getBirthdate())).append("\",");
			data.append("\"age\" : ").append(iMedicalRecord.getAge()).append(",");
			data.append("\"medicalRecords\" : {");
			data.append("\"medications\" : [");
			for (String medication : iMedicalRecord.getMedications()) {
				data.append("\"").append(JSONValue.escape(medication)).append("\",");
			}
			deleteLastComma(data);
			data.append("], \"allergies\" : [");
			for (String allergy : iMedicalRecord.getAllergies()) {
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

		for (Firestation iFirestation : this.stationDAO.getAllStations()) {
			if (iFirestation.getNumberStation().equals(stationNumber)) {
				for (Person iPerson : this.personDAO.getAllPersons()) {
					if (iPerson.getAddress().equals(iFirestation.getAddress())) {
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

		for (Person iPerson : this.personDAO.getAllPersons()) {
			if ((medicalRecordDAO.getMedicalRecord(iPerson.getId()).getAge() <= 18)
					&& (iPerson.getAddress().equals(address))) {
				data.append("{");
				data.append("\"firstName\" : \"").append(JSONValue.escape(iPerson.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(iPerson.getLastName())).append("\",");
				data.append("\"age\" : ").append(medicalRecordDAO.getMedicalRecord(iPerson.getId()).getAge()).append("},");
				childCount++;
			}
		}

		deleteLastComma(data);
		data.append("], \"adults\" : [");

		for (Person iPerson : this.personDAO.getAllPersons()) {
			if ((medicalRecordDAO.getMedicalRecord(iPerson.getId()).getAge() > 18)
					&& (iPerson.getAddress().equals(address))) {
				data.append("{");
				data.append("\"firstName\" : \"").append(JSONValue.escape(iPerson.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(iPerson.getLastName())).append("\"");
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
		data.append("\"stationNumber\": ").append(stationDAO.getFirestationByAddress(address).getNumberStation()).append(",");
		data.append("\"persons\" : [");

		for (Person iPerson : this.personDAO.getAllPersons()) {
			if (iPerson.getAddress().equals(address)) {
				personMedicalRecords = medicalRecordDAO.getMedicalRecord(iPerson.getId());
				data.append("{");
				data.append("\"firstName\" : \"").append(JSONValue.escape(iPerson.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(iPerson.getLastName())).append("\",");
				data.append("\"phone\" : \"").append(JSONValue.escape(iPerson.getPhone())).append("\",");
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
		for (Integer iNumStation : firestationNumbers) {
			data.append("{\"number\" : ").append(iNumStation).append(",");
			data.append("\"households\" : [");

			for (Firestation iFirestation : stationDAO.getFirestationByNumber(iNumStation)) {
				data.append("{ \"address\" : \"").append(JSONValue.escape(iFirestation.getAddress())).append("\",");

				MedicalRecord personMedicalRecords;

				data.append("\"persons\" : [");
				for (Person iPerson : this.personDAO.getAllPersons()) {
					if (iPerson.getAddress().equals(iFirestation.getAddress())) {
						personMedicalRecords = this.medicalRecordDAO.getMedicalRecord(iPerson.getId());
						data.append("{");
						data.append("\"firstName\" : \"").append(JSONValue.escape(iPerson.getFirstName())).append("\",");
						data.append("\"lastName\" : \"").append(JSONValue.escape(iPerson.getLastName())).append("\",");
						data.append("\"phone\" : \"").append(JSONValue.escape(iPerson.getPhone())).append("\",");
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

		for (Firestation iFiretation : this.stationDAO.getAllStations()) {
			if (iFiretation.getNumberStation().equals(stationNumber)) {
				for (Person iPerson : this.personDAO.getAllPersons()) {
					if (iPerson.getAddress().equals(iFiretation.getAddress())) {
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

		for (Person iPerson : this.personDAO.getAllPersons()) {
			if (iPerson.getFirstName().equals(firstName) && iPerson.getLastName().equals(lastName)) {
				personMedicalRecords = medicalRecordDAO.getMedicalRecord(iPerson.getId());
				data.append("{");
				data.append("\"id\" : ").append(iPerson.getId()).append(",");
				data.append("\"firstName\" : \"").append(JSONValue.escape(iPerson.getFirstName())).append("\",");
				data.append("\"lastName\" : \"").append(JSONValue.escape(iPerson.getLastName())).append("\",");
				data.append("\"address\" : \"").append(JSONValue.escape(iPerson.getAddress())).append("\",");
				data.append("\"city\" : \"").append(JSONValue.escape(iPerson.getCity())).append("\",");
				data.append("\"zip\" : \"").append(JSONValue.escape(iPerson.getZip())).append("\",");
				data.append("\"email\" : \"").append(JSONValue.escape(iPerson.getEmail())).append("\",");
				data.append("\"phone\" : \"").append(JSONValue.escape(iPerson.getPhone())).append("\",");
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
		data.append("{\"emails\" : [");

		for (Person iPerson : this.personDAO.getAllPersons()) {
			if (iPerson.getCity().equals(city)) {
				data.append("\"").append(JSONValue.escape(iPerson.getEmail())).append("\",");
			}
		}

		deleteLastComma(data);
		data.append("]}");

		LOGGER.info(new StringBuffer("GET : All Persons Email By City : ").append(city));
		return data.toString();
	}

}
