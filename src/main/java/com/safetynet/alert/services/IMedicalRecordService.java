package com.safetynet.alert.services;

import com.safetynet.alert.models.MedicalRecord;

public interface IMedicalRecordService {

	/**
	 * Add MedicalRecord from HTTP POST
	 * 
	 * @param newMedicalRecord
	 */
	public String httpPostMedicalRecord(MedicalRecord newMedicalRecord);

	/**
	 * Update MedicalRecord from HTTP PUT
	 * 
	 * @param medicalRecord
	 */
	public String httpPutMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Delete MedicalRecord from HTTP DELETE
	 * 
	 * @param id
	 */
	public String httpDeleteMedicalRecord(Integer id);

}
