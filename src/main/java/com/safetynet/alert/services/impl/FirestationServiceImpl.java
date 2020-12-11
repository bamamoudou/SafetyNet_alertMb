package com.safetynet.alert.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alert.DAO.impl.FirestationDAOImpl;
import com.safetynet.alert.models.Firestation;
import com.safetynet.alert.services.IFirestationService;

public class FirestationServiceImpl implements IFirestationService {

	private static final Logger LOGGER = LogManager.getLogger("FirestationService");

	private FirestationDAOImpl firestationDAO;

	public FirestationServiceImpl(FirestationDAOImpl firestationDAO) {
		super();
		this.firestationDAO = firestationDAO;
	}
	
	@Override
	public Firestation httpGetFirestation(String address, Integer number) {
		return firestationDAO.getFirestationByAddressAndNumber(address, number);
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
