package com.safetynet.alert.interfaces;

import java.util.List;

import com.safetynet.alert.configation.DatabaseConfig;
import com.safetynet.alert.models.MedicalRecord;

public interface IMedicalRecordDAO {
	
	 /**
    * Set DatabaseConfiguration
    * @param dataBaseConfig
    */
   public void setDatabaseConfig(DatabaseConfig dataBaseConfig);

	/**
	 * Get one medicalRecord from allMedicalRecords choose by person id
	 * 
	 * @param id
	 * @return One MedicalRecord
	 */
	public MedicalRecord getMedicalRecord(Integer id);

	/**
	 * Get all medicalRecords
	 * 
	 * @return List of all medicalRecords
	 */
	public List<MedicalRecord> getAllMedicalRecords();

	/**
	 * Add one new medical record
	 * 
	 * @param medicalRecord
	 */
	public boolean addNewMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Update one medical record in allMedicalRecords
	 * 
	 * @param medicalRecord
	 */
	public boolean updateMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Delete the medical record in allMedicalRecords
	 * 
	 * @param id
	 */
	public boolean deleteMedicalRecord(Integer id);

}
