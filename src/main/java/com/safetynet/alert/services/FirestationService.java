package com.safetynet.alert.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alert.DAO.FirestationDAO;
import com.safetynet.alert.interfaces.IFirestationService;
import com.safetynet.alert.models.Firestation;

public class FirestationService implements IFirestationService {

	private static final Logger LOGGER = LogManager.getLogger("FirestationService");

	private FirestationDAO firestationDAO;

	public FirestationService(FirestationDAO firestationDAO) {
		super();
		this.firestationDAO = firestationDAO;
	}

	@Override
	public String httpPostFirestation(Firestation newFirestation) {
		if (newFirestation != null) {
			if (firestationDAO.addNewFirestation(newFirestation)) {
				LOGGER.info("New station mapping added, number : " + newFirestation.getNumberStation() + ", address : "
						+ newFirestation.getAddress());
				return "Station mapping added";
			} else {
				LOGGER.error("Station mapping can't be added");
				return "Error : This Station mapping can't be added";
			}
		} else
			throw new NullPointerException();

	}

	@Override
	public String httpPutFirestation(Firestation firestation) {
		if (firestation != null) {
			if (firestationDAO.updateFirestation(firestation)) {
				LOGGER.info("Station mapping update, number : " + firestation.getNumberStation() + ", address : "
						+ firestation.getAddress());
				return "Station mapping updated";
			} else {
				LOGGER.error("Station mapping can't be updated");
				return "Error : This Station mapping can't be updated";
			}
		} else
			throw new NullPointerException();

	}

	@Override
	public String httpDeleteFirestation(Integer firestationNumber) {
		if (firestationDAO.deleteFirestationByNumber(firestationNumber)) {
			LOGGER.info("Station n°" + firestationNumber + " deleted");
			return "Station deleted";
		} else {
			LOGGER.error("Station can't be deleted");
			return "Error : This Station can't be deleted";
		}

	}

	@Override
	public String httpDeleteMapping(String address) {
		if (firestationDAO.deleteFirestationMapping(address)) {
			LOGGER.info("Station mapping \"" + address + "\" deleted");
			return "Station mapping deleted";
		} else {
			LOGGER.error("Station mapping can't be deleted");
			return "Error : This Station mapping can't be deleted";
		}

	}

}
