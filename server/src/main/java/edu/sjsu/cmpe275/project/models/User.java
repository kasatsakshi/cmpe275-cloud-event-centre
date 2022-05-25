package edu.sjsu.cmpe275.project.models;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe275.project.types.AccountStatus;
import edu.sjsu.cmpe275.project.types.AccountType;
import edu.sjsu.cmpe275.project.types.AuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@JsonPropertyOrder({ "id", "fullName", "screenName", "description", "email", "accountType", "gender", "address",
		"eventsCreated", "eventsRegistered" })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@JsonIgnore
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

	@Column(length = 64)
	@JsonIgnore
	private String verificationCode;

	@Column
	private AccountStatus status;

	@Column
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider;

	@Embedded
	private Address address;

	@Column
	private int participantReputation;

	@Column
	private int organizerReputation;

	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "creator", "address", "participants" })
	private List<Event> eventsCreated;

	@ManyToMany
	@JoinTable(name = "event_participation", joinColumns = @JoinColumn(name = "participant_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
	@JsonIncludeProperties({ "id", "title", "startTime", "endTime", "status" })
	private List<Event> eventsRegistered;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
	private Set<EventRequest> signupRequests;

	@OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
	private Set<EventRequest> requestsRecieved;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Answer> answers;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Question> questions;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<OrganizerReviews> OrganizerReviews;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<ParticipantReviews> ParticipantReviews;

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
	 * @return the participantReputation
	 */
	public int getParticipantReputation() {
		return participantReputation;
	}

	/**
	 * @param participantReputation the participantReputation to set
	 */
	public void setParticipantReputation(int participantReputation) {
		this.participantReputation = participantReputation;
	}

	/**
	 * @return the organizerReputation
	 */
	public int getOrganizerReputation() {
		return organizerReputation;
	}

	/**
	 * @param organizerReputation the organizerReputation to set
	 */
	public void setOrganizerReputation(int organizerReputation) {
		this.organizerReputation = organizerReputation;
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

	/**
	 * @return the status
	 */
	public AccountStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	/**
	 * @return the authProvider
	 */
	public AuthProvider getAuthProvider() {
		return authProvider;
	}

	/**
	 * @param authProvider the authProvider to set
	 */
	public void setAuthProvider(AuthProvider authProvider) {
		this.authProvider = authProvider;
	}

	/**
	 * @return the verificationCode
	 */
	public String getVerificationCode() {
		return verificationCode;
	}

	/**
	 * @param verificationCode the verificationCode to set
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	/**
	 * @return the signupRequests
	 */
	public Set<EventRequest> getSignupRequests() {
		return signupRequests;
	}

	/**
	 * @param signupRequests the signupRequests to set
	 */
	public void setSignupRequests(Set<EventRequest> signupRequests) {
		this.signupRequests = signupRequests;
	}

	/**
	 * @return the requestsRecieved
	 */
	public Set<EventRequest> getRequestsRecieved() {
		return requestsRecieved;
	}

	/**
	 * @param requestsRecieved the requestsRecieved to set
	 */
	public void setRequestsRecieved(Set<EventRequest> requestsRecieved) {
		this.requestsRecieved = requestsRecieved;
	}

	/**
	 * @return the answers
	 */
	public Set<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	/**
	 * @return the questions
	 */
	public Set<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	/**
	 * @return the organizerReviews
	 */
	public Set<OrganizerReviews> getOrganizerReviews() {
		return OrganizerReviews;
	}

	/**
	 * @param organizerReviews the organizerReviews to set
	 */
	public void setOrganizerReviews(Set<OrganizerReviews> organizerReviews) {
		OrganizerReviews = organizerReviews;
	}

	/**
	 * @return the participantReviews
	 */
	public Set<ParticipantReviews> getParticipantReviews() {
		return ParticipantReviews;
	}

	/**
	 * @param participantReviews the participantReviews to set
	 */
	public void setParticipantReviews(Set<ParticipantReviews> participantReviews) {
		ParticipantReviews = participantReviews;
	}

}
