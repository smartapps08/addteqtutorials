package com.myorg.listactivity;

public class CountryProfile {
	private String countryName;
	private String capitalName;
	private int flagImageRes;
	public CountryProfile(String countryName, String capitalName,
			int flagImageRes) {
		super();
		this.countryName = countryName;
		this.capitalName = capitalName;
		this.flagImageRes = flagImageRes;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCapitalName() {
		return capitalName;
	}
	public void setCapitalName(String capitalName) {
		this.capitalName = capitalName;
	}
	public int getFlagImageRes() {
		return flagImageRes;
	}
	public void setFlagImageRes(int flagImageRes) {
		this.flagImageRes = flagImageRes;
	}
	
	
}
