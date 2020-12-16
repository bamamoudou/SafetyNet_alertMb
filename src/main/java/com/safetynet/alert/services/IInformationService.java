package com.safetynet.alert.services;

public interface IInformationService {
	/**
	 * Get list of all persons
	 * 
	 * @return List of all persons
	 */
	String getAllPersons();

	/**
	 * Get list of all stations mapping
	 * 
	 * @return
	 */
	String getAllFirestations();

	/**
	 * Get list of all medical reords
	 * 
	 * @return
	 */
	String getAllMedicalRecords();

	/**
	 * Get all persons served by the station with count of child and adults
	 * 
	 * @param stationNumber
	 * @return String in JSON format
	 */
	String getAllPersonsServedByTheStationWithCount(Integer stationNumber);

	/**
	 * Get all child living at this address with all adults
	 * 
	 * @param address
	 * @return String in JSON format
	 */
	String getChildrenByAddress(String address);

	/**
	 * Get all informations of persons living at this address with their served
	 * station
	 * 
	 * @param address
	 * @return String in JSON format
	 */
	String getAllPersonsLivingAtTheAddressAndTheNumberStation(String address);

	/**
	 * Get all persons served by the station group by address
	 * 
	 * @param stationNumbers
	 * @return String in JSON format
	 */
	String getHouseholdListAndPersonsPerAddressWhenFlood(String firestation);

	/**
	 * Get all phones of persons by station location
	 * 
	 * @param stationNumber
	 * @return String in JSON format
	 */
	String getAllPersonsPhoneByStationNumber(Integer stationNumber);

	/**
	 * Get all informations of persons by name
	 * 
	 * @param firstName
	 * @param lastName
	 * @return String in JSON format
	 */
	String getAllCompleteProfileOfPersonsByName(String firstName, String lastName);

	/**
	 * Get all emails of persons by city
	 * 
	 * @param city
	 * @return String in JSON format
	 */
	String getAllPersonsEmailByCity(String city);

}
