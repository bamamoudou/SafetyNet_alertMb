package com.safetynet.alert.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.safetynet.alert.configuration.DatabaseConfig;
import com.safetynet.alert.models.Firestation;

@Singleton
public class FirestationDAO {

	private static final Logger LOGGER = LogManager.getLogger("FirestationDAOImpl");

	private DatabaseConfig databaseConfig;

	private List<Firestation> allStations;

	public FirestationDAO(DatabaseConfig databaseConfig) {
		super();
		this.databaseConfig = databaseConfig;
		this.allStations = new ArrayList<>();
		loadData();
	}

	public void setDatabaseConfig(DatabaseConfig dataBaseConfig) {
		this.databaseConfig = dataBaseConfig;

	}

	public List<Firestation> getFirestationByNumber(Integer number) {

		List<Firestation> listFirestations = new ArrayList<>();

		for (Firestation firestation : allStations) {
			if (firestation.getNumberStation().equals(number)) {
				listFirestations.add(firestation);

			}

		}
		return listFirestations;

	}

	public Firestation getFirestationByAddress(String address) {

		for (Firestation firestation : allStations) {
			if (firestation.getAddress().equals(address)) {

				return firestation;
			}

		}

		return null;

	}

	public Firestation getFirestationByAddressAndNumber(String address, Integer number) {

		for (Firestation firestation : allStations) {
			if (firestation.getAddress().equals(address) && firestation.getNumberStation().equals(number)) {

				return firestation;
			}

		}

		return null;

	}

	public List<Firestation> getAllStations() {
		return allStations;

	}

	public boolean addNewFirestation(Firestation firestation) {
		boolean stationAdded = false;

		if ((firestation.getNumberStation() > 0) && !firestation.getAddress().isEmpty()) {
			this.allStations.add(firestation);
			stationAdded = true;
		}

		if (stationAdded) {
			LOGGER.info("Station has been added");
		} else {
			LOGGER.error("Station informations aren't complete and could not be added");
		}

		return stationAdded;

	}

	public boolean updateFirestation(Firestation firestation) {
		boolean stationUpdated = false;

		for (Firestation iStation : allStations) {
			if (iStation.getAddress().equals(firestation.getAddress())) {
				iStation.setNumberStation(firestation.getNumberStation());
				stationUpdated = true;
				break;
			}
		}

		if (stationUpdated) {
			LOGGER.info("Station informations have been updated");
		} else {
			LOGGER.error("Station doesn't exist in stations list and could not be updated");
		}

		return stationUpdated;

	}

	public boolean deleteFirestationMapping(String address) {
		boolean stationDeleted = false;

		for (int i = 0; i < allStations.size(); i++) {
			if (allStations.get(i).getAddress().equals(address)) {
				allStations.remove(i);
				stationDeleted = true;
				break;
			}
		}

		if (stationDeleted) {
			LOGGER.info("Station has been deleted");
		} else {
			LOGGER.error("Station doesn't exist in stations list and could not be deleted");
		}

		return stationDeleted;

	}

	public boolean deleteFirestationByNumber(Integer number) {
		boolean stationDeleted = false;

		for (int i = 0; i < allStations.size(); i++) {
			if (allStations.get(i).getNumberStation().equals(number)) {
				allStations.remove(i);
				stationDeleted = true;
			}
		}

		if (stationDeleted) {
			LOGGER.info("Station has been deleted");
		} else {
			LOGGER.error("Station doesn't exist in stations list and could not be deleted");
		}

		return stationDeleted;

	}

	private void loadData() {
		try {
			JSONObject data = databaseConfig.openConnection();

			JSONArray stations = (JSONArray) data.get("firestations");

			for (int i = 0; i < stations.size(); i++) {
				JSONObject station = (JSONObject) stations.get(i);
				Integer number = Integer.parseInt((String) station.get("station"));
				String address = (String) station.get("address");
				allStations.add(new Firestation(number, address));
			}

			LOGGER.info("Stations are loaded from data");
		} catch (Exception e) {
			LOGGER.error("Data can't be loaded in StationDAO : " + e);
		}
		databaseConfig.closeConnection();
	}

}
