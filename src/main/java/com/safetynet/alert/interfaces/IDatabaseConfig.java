package com.safetynet.alert.interfaces;

import org.json.simple.JSONObject;

public interface IDatabaseConfig {

	/**
	 * Read data.json and cast it in JSONObject data
	 * 
	 * @return data : JSONObject
	 */
	JSONObject openConnection();

	/**
	 * Read data.json and cast it in JSONObject data
	 * 
	 * @param filepath
	 * @return data : JSONObject
	 */
	JSONObject openConnection(String filepath);

	/**
	 * Getter data
	 * 
	 * @return data : JSONObject
	 */
	JSONObject getData();

	/**
	 * Set data as null
	 */
	void closeConnection();

}
