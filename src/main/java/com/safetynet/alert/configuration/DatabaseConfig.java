package com.safetynet.alert.configuration;

import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DatabaseConfig {

	private static final Logger LOGGER = LogManager.getLogger("DatabaseConfigImpl");

	private JSONObject data;

	public DatabaseConfig() {

	}

	public JSONObject openConnection() {
		try (FileReader fileData = new FileReader("src/main/resources/static/data.json")) {
			this.data = (JSONObject) new JSONParser().parse(fileData);
			LOGGER.info("Connection open");
			return this.data;
		} catch (Exception e) {
			LOGGER.error("Connection error");
			return null;
		}

	}

	public JSONObject openConnection(String filepath) {
		try (FileReader fileData = new FileReader(filepath)) {
			this.data = (JSONObject) new JSONParser().parse(fileData);
			LOGGER.info("Connection open");
			return this.data;
		} catch (Exception e) {
			LOGGER.error("Connection error");
			return null;
		}

	}

	public JSONObject getData() {
		return data;

	}

	public void closeConnection() {
		this.data = null;
		LOGGER.info("Connection close");

	}

}
