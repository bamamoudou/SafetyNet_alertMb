package com.safetynet.alert.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.safetynet.alert.configation.DatabaseConfigImpl;
import com.safetynet.alert.models.Person;

@Singleton
public class PersonDAOImpl implements IPersonDAO {
	private static final Logger LOGGER = LogManager.getLogger("PersonDAOImpl");

	private DatabaseConfigImpl databaseConfig;

	private List<Person> allPersons;

	public PersonDAOImpl(DatabaseConfigImpl databaseConfig) {
		super();
		this.databaseConfig = databaseConfig;
		this.allPersons = new ArrayList<Person>();
		loadData();
	}

	@Override
	public void setDatabaseConfig(DatabaseConfigImpl dataBaseConfig) {
		this.databaseConfig = dataBaseConfig;

	}

	@Override
	public List<Person> getPersonsByName(String firstName, String lastName) {
		List<Person> listOfPersons = new ArrayList<>();

		for (Person iPerson : allPersons) {
			if ((iPerson.getFirstName().equals(firstName)) && (iPerson.getLastName().equals(lastName))) {
				listOfPersons.add(iPerson);

			}
		}
		return listOfPersons;

	}

	@Override
	public Person getPersonsById(Integer id) {
		for (Person iPerson : allPersons) {
			if (iPerson.getId().equals(id)) {
				return iPerson;
			}
		}
		return null;

	}

	@Override
	public List<Person> getAllPersons() {
		return this.allPersons;

	}

	@Override
	public boolean addNewPerson(Person person) {
		boolean personPosted = false;

		if ((person.getId() > 0) && !person.getFirstName().isEmpty() && !person.getLastName().isEmpty()
				&& !person.getAddress().isEmpty() && !person.getCity().isEmpty() && !person.getZip().isEmpty()
				&& !person.getEmail().isEmpty() && !person.getEmail().isEmpty() && !person.getPhone().isEmpty()) {
			this.allPersons.add(person);
			personPosted = true;
		}

		if (personPosted) {
			LOGGER.info("Person profile has been added");
		} else {
			LOGGER.error("Person isn't complete and could not be added");
		}

		return personPosted;

	}

	@Override
	public boolean updatePerson(Person person) {
		boolean personUpdated = false;

		for (Person iPerson : allPersons) {
			if ((iPerson.getFirstName().equals(person.getFirstName()))
					&& (iPerson.getLastName().equals(person.getLastName())) && (iPerson.getId().equals(person.getId()))) {
				iPerson.setAddress(person.getAddress());
				iPerson.setCity(person.getCity());
				iPerson.setEmail(person.getEmail());
				iPerson.setPhone(person.getPhone());
				iPerson.setZip(person.getZip());
				personUpdated = true;
				break;
			}
		}

		if (personUpdated) {
			LOGGER.info("Person profile has been updated");
		} else {
			LOGGER.error("Person doesn't exist in persons list and could not be updated");
		}

		return personUpdated;

	}

	@Override
	public boolean deletePerson(Integer id) {
		boolean personDeleted = false;

		for (int i = 0; i < allPersons.size(); i++) {
			if (allPersons.get(i).getId().equals(id)) {
				allPersons.remove(i);
				personDeleted = true;
				break;
			}
		}

		if (personDeleted) {
			LOGGER.info("Person profile has been deleted");
		} else {
			LOGGER.error("Person doesn't exist in persons list and could not be deleted");
		}

		return personDeleted;

	}

	private void loadData() {
		try {
			JSONObject data = databaseConfig.openConnection();

			JSONArray persons = (JSONArray) data.get("persons");

			for (int i = 0; i < persons.size(); i++) {
				JSONObject person = (JSONObject) persons.get(i);
				String firstName = (String) person.get("firstName");
				String lastName = (String) person.get("lastName");
				String address = (String) person.get("address");
				String city = (String) person.get("city");
				String zip = (String) person.get("zip");
				String phone = (String) person.get("phone");
				String email = (String) person.get("email");
				allPersons.add(new Person(allPersons.size() + 1, firstName, lastName, address, city, zip, phone, email));
			}

			LOGGER.info("Persons are loaded from data");
		} catch (Exception e) {
			LOGGER.error("Data can't be loaded in PersonDAO : " + e);
		}
		databaseConfig.closeConnection();

	}

}
