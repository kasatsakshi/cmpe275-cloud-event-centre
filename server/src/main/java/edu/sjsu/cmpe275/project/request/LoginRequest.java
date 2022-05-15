package edu.sjsu.cmpe275.project.request;

import edu.sjsu.cmpe275.project.types.AuthProvider;

public class LoginRequest {

	private String email;

	private String password;

	private AuthProvider provider;

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
}
