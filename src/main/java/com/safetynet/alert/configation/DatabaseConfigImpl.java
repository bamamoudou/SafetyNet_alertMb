package com.safetynet.alert.configation;

import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DatabaseConfigImpl implements IDatabaseConfig {

	private static final Logger LOGGER = LogManager.getLogger("DatabaseConfigImpl");

	private JSONObject data;

	public DatabaseConfigImpl() {

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

	@Override
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

	@Override
	public JSONObject getData() {
		return data;

	}

	@Override
	public void closeConnection() {
		this.data = null;
		LOGGER.info("Connection close");

	}

}
