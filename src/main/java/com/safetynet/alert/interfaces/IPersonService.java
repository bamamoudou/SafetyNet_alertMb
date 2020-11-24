package com.safetynet.alert.interfaces;

import com.safetynet.alert.models.Person;

public interface IPersonService {
	/**
	 * Add person from HTTP POST
	 * 
	 * @param person
	 */
	String httpPostPerson(Person person);

	/**
	 * Update person from HTTP PUT
	 * 
	 * @param person
	 */
	String httpPutPerson(Person person);

	/**
	 * Delete person from HTTP DELETE
	 * 
	 * @param id
	 */
	String httpDeletePerson(Integer id);

}
