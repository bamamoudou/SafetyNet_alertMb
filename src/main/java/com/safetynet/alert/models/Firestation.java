package com.safetynet.alert.models;

public class Firestation {
	private Integer numberStation;
	private String address;

	/**
	 * 
	 * @param numberStation
	 * @param address
	 */
	public Firestation(Integer numberStation, String address) {
		super();
		this.numberStation = numberStation;
		this.address = address;
	}

	public Integer getNumberStation() {
		return numberStation;
	}

	public void setNumberStation(Integer numberStation) {
		this.numberStation = numberStation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
