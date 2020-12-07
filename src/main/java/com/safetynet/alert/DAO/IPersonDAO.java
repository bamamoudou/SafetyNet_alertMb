package com.safetynet.alert.DAO;

import java.util.List;

import com.safetynet.alert.configuration.DatabaseConfigImpl;
import com.safetynet.alert.models.Person;

public interface IPersonDAO {
	
	 /**
    * Set DatabaseConfiguration
    * @param dataBaseConfig
    */
   public void setDatabaseConfig(DatabaseConfigImpl dataBaseConfig);

	/**
	 * Get list of persons profile from allPersons choose by firstName and lastName
	 * 
	 * @param firstName
	 * @param lastName
	 * @return list of persons profile
	 */
	public List<Person> getPersonsByName(String firstName, String lastName);

	/**
	 * Get list of persons profile from allPersons choose by id
	 * 
	 * @param id
	 * @return List of persons profile
	 */
	public Person getPersonsById(Integer id);

	/**
	 * Get all persons profiles
	 * 
	 * @return List of all persons
	 */
	public List<Person> getAllPersons();

	/**
	 * Add one profile to allPersons
	 * 
	 * @param person
	 */
	public boolean addNewPerson(Person person);

	/**
	 * Update one person profile in allPersons
	 * 
	 * @param person
	 */
	public boolean updatePerson(Person person);

	/**
	 * Delete the profile of one person in allPersons
	 * 
	 * @param id
	 */
	public boolean deletePerson(Integer id);

}
