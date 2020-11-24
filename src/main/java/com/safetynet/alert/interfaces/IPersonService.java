package com.safetynet.alert.interfaces;

import com.safetynet.alert.models.Person;

public interface IPersonService {
	/**
	 * Add person from HTTP POST
	 * 
	 * @param person
	 */
	String httpPost(Person person);

	/**
	 * Update person from HTTP PUT
	 * 
	 * @param person
	 */
	String httpPut(Person person);

	/**
	 * Delete person from HTTP DELETE
	 * 
	 * @param id
	 */
	String httpDelete(Integer id);

}
