package com.safetynet.alert.DAO;

import java.util.List;

import com.safetynet.alert.configation.DatabaseConfig;
import com.safetynet.alert.interfaces.IMedicalRecordDAO;
import com.safetynet.alert.models.MedicalRecord;

public class MedicalRecordDAO implements IMedicalRecordDAO {
	
	@Override
	public void setDatabaseConfig(DatabaseConfig dataBaseConfig) {
		
	}
   
	@Override
	public MedicalRecord getMedicalRecord(Integer id) {
		return null;
		
	}
	
	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return null;
		
	}
	
	@Override
	public boolean addNewMedicalRecord(MedicalRecord medicalRecord) {
		return false;
		
	}
	
	@Override
	public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
		return false;
		
	}
	
	@Override
	public boolean deleteMedicalRecord(Integer id) {
		return false;
		
	}

}
