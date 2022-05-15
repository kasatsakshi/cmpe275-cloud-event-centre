package edu.sjsu.cmpe275.project.request;

import java.util.Optional;

import edu.sjsu.cmpe275.project.types.AccountType;
import edu.sjsu.cmpe275.project.types.AuthProvider;

public class SignUpRequest {
	private String fullName;
	private String screenName;
	private String email;
	private String password;
	private AccountType accountType;
	private AuthProvider provider;
	private Optional<String> gender;
	private Optional<String> description;
	private Optional<String> street;
	private Optional<String> city;
	private Optional<String> state;
	private Optional<String> zip;

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the accountType
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the provider
	 */
	public AuthProvider getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	/**
	 * @return the gender
	 */
	public Optional<String> getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Optional<String> gender) {
		this.gender = gender;
	}

	/**
	 * @return the description
	 */
	public Optional<String> getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(Optional<String> description) {
		this.description = description;
	}

	/**
	 * @return the street
	 */
	public Optional<String> getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(Optional<String> street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public Optional<String> getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(Optional<String> city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public Optional<String> getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Optional<String> state) {
		this.state = state;
	}

	/**
	 * @return the zip
	 */
	public Optional<String> getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(Optional<String> zip) {
		this.zip = zip;
	}
}
