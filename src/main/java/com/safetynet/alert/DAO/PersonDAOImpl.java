package com.safetynet.alert.DAO;

import java.util.List;

import com.safetynet.alert.configation.DatabaseConfigImpl;
import com.safetynet.alert.models.Person;

public class PersonDAOImpl implements IPersonDAO{
	
	@Override
	public void setDatabaseConfig(DatabaseConfigImpl dataBaseConfig) {
		
	}
	
   @Override
	public List<Person> getPersonsByName(String firstName, String lastName) {
		return null;
		
	}
	
   @Override
	public Person getPersonsById(Integer id) {
		return null;
		
	}
   
   @Override
	public List<Person> getAllPersons() {
		return null;
		
	}
	
   @Override
	public boolean addNewPerson(Person person) {
		return false;
		
	}
	
   @Override
	public boolean updatePerson(Person person) {
		return false;
		
	}
	
   @Override
	public boolean deletePerson(Integer id) {
		return false;
		
	}

}
