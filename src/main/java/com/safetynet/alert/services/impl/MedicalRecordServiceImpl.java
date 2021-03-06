package com.safetynet.alert.services.impl;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alert.DAO.MedicalRecordDAO;
import com.safetynet.alert.models.MedicalRecord;
import com.safetynet.alert.services.IMedicalRecordService;

@Singleton
public class MedicalRecordServiceImpl implements IMedicalRecordService {

	private static final Logger LOGGER = LogManager.getLogger("MedicalRecordService");

	private MedicalRecordDAO medicalRecordDAO;

	public MedicalRecordServiceImpl(MedicalRecordDAO medicalRecordDAO) {
		super();
		this.medicalRecordDAO = medicalRecordDAO;
	}

	@Override
	public String httpPostMedicalRecord(MedicalRecord addNewMedicalRecord) {
		if (addNewMedicalRecord != null) {
			if (medicalRecordDAO.addNewMedicalRecord(addNewMedicalRecord)) {
				LOGGER.info("New medical record for person n°" + addNewMedicalRecord.getId() + " added");
				return "Medical record added";
			} else {
				LOGGER.error("Medical record can't be added");
				return "Error : This Medical record can't be added";
			}
		} else
			throw new NullPointerException();

	}

	@Override
	public String httpPutMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord != null) {
			if (medicalRecordDAO.updateMedicalRecord(medicalRecord)) {
				LOGGER.info("Medical record for person n°" + medicalRecord.getId() + " updated");
				return "Medical record updated";
			} else {
				LOGGER.error("Medical record can't be updated");
				return "Error : This Medical record can't be updated";
			}
		} else
			throw new NullPointerException();

	}

	@Override
	public String httpDeleteMedicalRecord(Integer id) {
		if (medicalRecordDAO.deleteMedicalRecord(id)) {
			LOGGER.info("Medical record for person n°" + id + " deleted");
			return "Medical record deleted";
		} else {
			LOGGER.error("Medical record can't be deleted");
			return "Error : This Medical record can't be deleted";
		}

	}

}
