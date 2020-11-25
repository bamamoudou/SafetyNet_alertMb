package com.safetynet.alert.interfaces;

import java.util.List;

import com.safetynet.alert.configation.DatabaseConfig;
import com.safetynet.alert.models.Firestation;

public interface IFireStationDAO {
	/**
	 * Set DatabaseConfiguration
	 * 
	 * @param dataBaseConfig
	 */
	public void setDatabaseConfig(DatabaseConfig dataBaseConfig);

	/**
	 * Get one station from allStations choose by station number
	 * 
	 * @param number
	 * @return List of Stations
	 */
	public List<Firestation> getFirestationByNumber(Integer number);

	/**
	 * Get one station from allStations choose by station address
	 * 
	 * @param address
	 * @return One Station
	 */
	public Firestation getFirestationByAddress(String address);

	/**
	 * Get all stations
	 * 
	 * @return List of all stations
	 */
	public List<Firestation> getAllStations();

	/**
	 * Add one new station
	 * 
	 * @param station
	 */
	public boolean addNewFirestation(Firestation firestation);

	/**
	 * Update one station in allStations
	 * 
	 * @param station
	 */
	public boolean updateFirestation(Firestation firestation);

	/**
	 * Delete the station mapping
	 * 
	 * @param address
	 */
	public boolean deleteFirestationMapping(String address);

	/**
	 * Delete the station in allStations
	 * 
	 * @param number
	 */
	public boolean deleteFirestationByNumber(Integer number);

}
