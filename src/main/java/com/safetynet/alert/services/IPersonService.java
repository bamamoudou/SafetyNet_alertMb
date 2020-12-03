package com.safetynet.alert.services;

import com.safetynet.alert.models.Person;

public interface IPersonService {
	/**
	 * Add person from HTTP POST
	 * 
	 * @param person
	 */
	public String httpPostPerson(Person person);

	/**
	 * Update person from HTTP PUT
	 * 
	 * @param person
	 */
	public String httpPutPerson(Person person);

	/**
	 * Delete person from HTTP DELETE
	 * 
	 * @param id
	 */
	public String httpDeletePerson(Integer id);

}
