package com.safetynet.alert.services.impl;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alert.DAO.MedicalRecordDAO;
import com.safetynet.alert.DAO.PersonDAO;
import com.safetynet.alert.models.Person;
import com.safetynet.alert.services.IPersonService;

@Singleton
public class PersonServiceImpl implements IPersonService {

	private static final Logger LOGGER = LogManager.getLogger("PersonService");
	private PersonDAO personDAO;
	private MedicalRecordDAO medicalRecordDAO;

	public PersonServiceImpl(PersonDAO personDAO, MedicalRecordDAO medicalRecordDAO) {
		super();
		this.personDAO = personDAO;
		this.medicalRecordDAO = medicalRecordDAO;
	}

	@Override
	public String httpPostPerson(Person addNewPerson) {
		if (addNewPerson != null) {
			addNewPerson.setId(personDAO.getAllPersons().size() + 1);
			if (personDAO.addNewPerson(addNewPerson)) {
				LOGGER.info("New person profile added, id : " + addNewPerson.getId() + ", name : " + addNewPerson.getFirstName()
						+ " " + addNewPerson.getLastName());
				return "Person added";
			} else {
				LOGGER.error("Person can't be added");
				return "Error : This Person can't be added";
			}
		} else
			throw new NullPointerException();
	}

	@Override
	public String httpPutPerson(Person person) {
		if (person != null) {
			if (personDAO.updatePerson(person)) {
				LOGGER.info("Person profile n°" + person.getId() + " has been updated");
				return "Person updated";
			} else {
				LOGGER.error("Person profile can't be updated");
				return "Error : This Person hasn't been updated";
			}
		} else
			throw new NullPointerException();
	}

	@Override
	public String httpDeletePerson(Integer id) {
		String resultMsg;
		if (medicalRecordDAO.deleteMedicalRecord(id)) {
			LOGGER.info("The medical record of person n°" + id + " has been deleted");
			resultMsg = "Medical record deleted";
		} else {
			LOGGER.error("The medical record of person n°" + id + " hasn't be deleted");
			resultMsg = "Error : The medical record of this person hasn't be deleted";
		}

		if (personDAO.deletePerson(id)) {
			LOGGER.info("The person profile n°" + id + " has been deleted");
			resultMsg += " / Person deleted";
		} else {
			LOGGER.error("The person profile n°" + id + " hasn't be deleted");
			resultMsg += " / Error : This Person hasn't been deleted";
		}
		return resultMsg;
	}

}
