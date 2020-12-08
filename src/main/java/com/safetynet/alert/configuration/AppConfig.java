package com.safetynet.alert.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.safetynet.alert.DAO.impl.FirestationDAOImpl;
import com.safetynet.alert.DAO.impl.MedicalRecordDAOImpl;
import com.safetynet.alert.DAO.impl.PersonDAOImpl;
import com.safetynet.alert.services.impl.FirestationServiceImpl;
import com.safetynet.alert.services.impl.InformationServiceImpl;
import com.safetynet.alert.services.impl.MedicalRecordServiceImpl;
import com.safetynet.alert.services.impl.PersonServiceImpl;

@Configuration
public class AppConfig {

	private DatabaseConfigImpl databaseConfig = new DatabaseConfigImpl();

	@Bean
	public PersonDAOImpl personDAO() {
		return new PersonDAOImpl(databaseConfig);
	}

	@Bean
	public FirestationDAOImpl stationDAO() {
		return new FirestationDAOImpl(databaseConfig);
	}

	@Bean
	public MedicalRecordDAOImpl medicalRecordDAO() {
		return new MedicalRecordDAOImpl(databaseConfig, personDAO());
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
