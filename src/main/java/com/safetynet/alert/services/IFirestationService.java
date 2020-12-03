package com.safetynet.alert.services;

import com.safetynet.alert.models.Firestation;

public interface IFirestationService {

	/**
	 * Add Firestation from HTTP POST
	 * 
	 * @param newStation
	 */
	public String httpPostFirestation(Firestation newFirestation);

	/**
	 * Update Firestation from HTTP PUT
	 * 
	 * @param station
	 */
	public String httpPutFirestation(Firestation firestation);

	/**
	 * Delete Firestation from HTTP DELETE
	 * 
	 * @param stationNumber
	 */
	public String httpDeleteFirestation(Integer firestationNumber);

	/**
	 * Delete Firestation address from HTTP DELETE
	 * 
	 * @param address
	 */
	public String httpDeleteMapping(String address);

}
