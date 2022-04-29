package edu.sjsu.cmpe275.project.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

	Integer number;
	String street;
	String city;
	String state;
	String zipcode;

	public Address(Integer _number, String _street) {
		this.number = _number;
		this.street = _street;
		this.city = "San Jose";
		this.state = "California";
		this.zipcode = "95110";
	}

}
