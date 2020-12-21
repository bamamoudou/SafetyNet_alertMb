package com.safetynet.alert.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.safetynet.alert.DAO.FirestationDAO;
import com.safetynet.alert.DAO.MedicalRecordDAO;
import com.safetynet.alert.DAO.PersonDAO;
import com.safetynet.alert.services.impl.FirestationServiceImpl;
import com.safetynet.alert.services.impl.InformationServiceImpl;
import com.safetynet.alert.services.impl.MedicalRecordServiceImpl;
import com.safetynet.alert.services.impl.PersonServiceImpl;

@Configuration
public class AppConfig {

	private DatabaseConfig databaseConfig = new DatabaseConfig();

	@Bean
	public PersonDAO personDAO() {
		return new PersonDAO(databaseConfig);
	}

	@Bean
	public FirestationDAO stationDAO() {
		return new FirestationDAO(databaseConfig);
	}

	@Bean
	public MedicalRecordDAO medicalRecordDAO() {
		return new MedicalRecordDAO(databaseConfig, personDAO());
	}

	@Bean
	public PersonServiceImpl personService() {
		return new PersonServiceImpl(personDAO(), medicalRecordDAO());
	}

	@Bean
	public FirestationServiceImpl stationService() {
		return new FirestationServiceImpl(stationDAO());
	}

	@Bean
	public MedicalRecordServiceImpl medicalRecordsService() {
		return new MedicalRecordServiceImpl(medicalRecordDAO());
	}

	@Bean
	public InformationServiceImpl informationService() {
		return new InformationServiceImpl(personDAO(), stationDAO(), medicalRecordDAO());
	}

}
