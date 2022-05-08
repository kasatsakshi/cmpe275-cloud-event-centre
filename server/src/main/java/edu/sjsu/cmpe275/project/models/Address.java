package edu.sjsu.cmpe275.project.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

	Integer number;
	String street;
	String city;
	String state;
	String zip;

	public Address(Integer _number, String _street) {
		this.number = _number;
		this.street = _street;
		this.city = "San Jose";
		this.state = "California";
		this.zip = "95110";
	}

	public Address() {
	}

	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zip;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZip(String zipcode) {
		this.zip = zipcode;
	}

}
