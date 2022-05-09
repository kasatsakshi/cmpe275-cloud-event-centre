package edu.sjsu.cmpe275.project.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe275.project.types.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
@JsonPropertyOrder({ "id", "fullName", "screenName", "description", "accountType", "gender", "email", "password",
		"team", "opponents" })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = false)
	private String password;

	@Column(nullable = false)
	private String fullName;

	@Column(nullable = false)
	private AccountType accountType;

	@Column(nullable = false)
	private String screenName;

	@Column
	private String gender;

	@Column
	private String description;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({ "creator", "address", "participants" })
	private List<Event> eventsCreated;
//(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ManyToMany
	@JoinTable(name = "event_participation", joinColumns = @JoinColumn(name = "participant_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
	@JsonIgnoreProperties({ "creator", "address", "participants" })
	private List<Event> eventsRegistered;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the eventsCreated
	 */
	public List<Event> getEventsCreated() {
		return eventsCreated;
	}

	/**
	 * @param eventsCreated the eventsCreated to set
	 */
	public void setEventsCreated(List<Event> eventsCreated) {
		this.eventsCreated = eventsCreated;
	}

	/**
	 * @return the eventsRegistered
	 */
	public List<Event> getEventsRegistered() {
		return eventsRegistered;
	}

	/**
	 * @param eventsRegistered the eventsRegistered to set
	 */
	public void setEventsRegistered(List<Event> eventsRegistered) {
		this.eventsRegistered = eventsRegistered;
	}

}
