package com.safetynet.alert.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.safetynet.alert.configuration.DatabaseConfig;
import com.safetynet.alert.models.MedicalRecord;

@Singleton
public class MedicalRecordDAO {

	private static final Logger LOGGER = LogManager.getLogger("MedicalRecordDAOImpl");

	private DatabaseConfig databaseConfig;

	private PersonDAO personDAO;

	private List<MedicalRecord> allMedicalRecords;

	public MedicalRecordDAO(DatabaseConfig databaseConfig, PersonDAO personDAO) {
		super();
		this.databaseConfig = databaseConfig;
		this.personDAO = personDAO;
		this.allMedicalRecords = new ArrayList<>();
		loadData();
	}

	public void setDatabaseConfig(DatabaseConfig dataBaseConfig) {
		this.databaseConfig = dataBaseConfig;

	}

	public MedicalRecord getMedicalRecord(Integer id) {
		for (MedicalRecord iMedicalRecord : allMedicalRecords) {
			if (iMedicalRecord.getId().equals(id)) {
				return iMedicalRecord;
			}
		}
		return null;

	}

	public List<MedicalRecord> getAllMedicalRecords() {
		return this.allMedicalRecords;

	}

	public boolean addNewMedicalRecord(MedicalRecord medicalRecord) {
		boolean medicalRecordAdded = false;

		if (personDAO.getPersonsById(medicalRecord.getId()) != null) {
			this.allMedicalRecords.add(medicalRecord);
			medicalRecordAdded = true;
		}

		if (medicalRecordAdded) {
			LOGGER.info("Medical record has been added");
		} else {
			LOGGER.error("The medical record of person n° " + medicalRecord.getId() + ", born on "
					+ medicalRecord.getBirthdate() + " does not correspond to an existing profile");
		}

		return medicalRecordAdded;

	}

	public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
		boolean medicalRecordUpdated = false;

		for (MedicalRecord iMedicalRecord : allMedicalRecords) {
			if (iMedicalRecord.getId().equals(medicalRecord.getId())) {
				iMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
				iMedicalRecord.setMedications(medicalRecord.getMedications());
				iMedicalRecord.setAllergies(medicalRecord.getAllergies());
				medicalRecordUpdated = true;
				break;
			}
		}

		if (medicalRecordUpdated) {
			LOGGER.info("Medical record informations have been updated");
		} else {
			LOGGER.error("Medical record doesn't exist in medical records list and could not be updated");
		}

		return medicalRecordUpdated;

	}

	public boolean deleteMedicalRecord(Integer id) {
		boolean medicalRecordDeleted = false;

		for (int i = 0; i < allMedicalRecords.size(); i++) {
			if (allMedicalRecords.get(i).getId().equals(id)) {
				allMedicalRecords.remove(i);
				medicalRecordDeleted = true;
				break;
			}
		}

		if (medicalRecordDeleted) {
			LOGGER.info("Medical record have been deleted");
		} else {
			LOGGER.error("Medical record doesn't exist in medical records list and could not be deleted");
		}

		return medicalRecordDeleted;

	}

	private void loadData() {
		try {
			JSONObject data = databaseConfig.openConnection();

			JSONArray medicalRecords = (JSONArray) data.get("medicalrecords");

			for (int i = 0; i < medicalRecords.size(); i++) {
				JSONObject medicalRecord = (JSONObject) medicalRecords.get(i);

				String birthdate = (String) medicalRecord.get("birthdate");
				List<String> medications = (List<String>) medicalRecord.get("medications");
				List<String> allergies = (List<String>) medicalRecord.get("allergies");

				allMedicalRecords.add(new MedicalRecord(allMedicalRecords.size() + 1, birthdate, medications, allergies));
			}
			LOGGER.info("Medical records are loaded from data");
		} catch (Exception e) {
			LOGGER.error("Data can't be loaded in MedicalRecordDAO : " + e);
		}

		databaseConfig.closeConnection();
	}

}
