package com.safetynet.alert.DAO;

import java.util.List;

import com.safetynet.alert.configation.DatabaseConfig;
import com.safetynet.alert.interfaces.IFireStationDAO;
import com.safetynet.alert.models.Firestation;


public class FirestationDAO implements IFireStationDAO {

	public void setDatabaseConfig(DatabaseConfig dataBaseConfig) {
		
	}
	
	
	public List<Firestation> getFirestationByNumber(Integer number) {
		return null;
		
	}
	
	
	public Firestation getFirestationByAddress(String address) {
		return null;
		
	}
	
	public List<Firestation> getAllStations() {
		return null;
		
	}
	
	
	public boolean addNewFirestation(Firestation firestation) {
		return false;
		
	}
	
	public boolean updateFirestation(Firestation firestation) {
		return false;
		
	}
	
	public boolean deleteFirestationMapping(String address) {
		return false;
		
	}
	
	public boolean deleteFirestationByNumber(Integer number) {
		return false;
		
	}

}
