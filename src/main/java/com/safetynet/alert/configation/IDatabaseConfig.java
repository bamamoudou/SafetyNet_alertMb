package com.safetynet.alert.configation;

import org.json.simple.JSONObject;

public interface IDatabaseConfig {

	/**
	 * Read data.json and cast it in JSONObject data
	 * 
	 * @return data : JSONObject
	 */
	public JSONObject openConnection();

	/**
	 * Read data.json and cast it in JSONObject data
	 * 
	 * @param filepath
	 * @return data : JSONObject
	 */
	public JSONObject openConnection(String filepath);

	/**
	 * Getter data
	 * 
	 * @return data : JSONObject
	 */
	public JSONObject getData();

	/**
	 * Set data as null
	 */
	public void closeConnection();

}
