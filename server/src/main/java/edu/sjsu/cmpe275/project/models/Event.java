package edu.sjsu.cmpe275.project.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe275.project.types.AdmissionPolicy;
import edu.sjsu.cmpe275.project.types.EventStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EVENTS")
@JsonPropertyOrder({ "id", "title", "description", "fee", "admissionPolicy", "startTime", "endTime", "deadline",
		"creatorId", "minimumParticipants", "maximumParticipants", "address", "creator", "participants", "status" })
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	private LocalDateTime startTime;

	@Column
	private LocalDateTime endTime;

	@Column
	private LocalDateTime deadline;

	@Embedded
	private Address address;

	@Column
	private Integer minimumParticipants;

	@Column
	private Integer maximumParticipants;

	@Column
	private Integer fee;

	@Column
	private AdmissionPolicy admissionPolicy;

	@Column
	private EventStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "creatorId")
	@JsonIgnoreProperties({ "address", "eventsCreated", "eventsRegistered", "password", "status", "description",
			"authProvider", "gender" })
	private User creator;

	@ManyToMany(mappedBy = "eventsRegistered", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({ "address", "eventsCreated", "eventsRegistered", "password", "status", "accountType",
			"description", "authProvider", "gender" })
	private List<User> participants;

	@OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
	private Set<EventRequest> signupRequests;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the deadline
	 */
	public LocalDateTime getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
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
	 * @return the minimumParticipants
	 */
	public Integer getMinimumParticipants() {
		return minimumParticipants;
	}

	/**
	 * @param minimumParticipants the minimumParticipants to set
	 */
	public void setMinimumParticipants(Integer minimumParticipants) {
		this.minimumParticipants = minimumParticipants;
	}

	/**
	 * @return the maximumParticipants
	 */
	public Integer getMaximumParticipants() {
		return maximumParticipants;
	}

	/**
	 * @param maximumParticipants the maximumParticipants to set
	 */
	public void setMaximumParticipants(Integer maximumParticipants) {
		this.maximumParticipants = maximumParticipants;
	}

	/**
	 * @return the fee
	 */
	public Integer getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(Integer fee) {
		this.fee = fee;
	}

	/**
	 * @return the admissionPolicy
	 */
	public AdmissionPolicy getAdmissionPolicy() {
		return admissionPolicy;
	}

	/**
	 * @param admissionPolicy the admissionPolicy to set
	 */
	public void setAdmissionPolicy(AdmissionPolicy admissionPolicy) {
		this.admissionPolicy = admissionPolicy;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the participants
	 */
	public List<User> getParticipants() {
		return participants;
	}

	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	/**
	 * @return the status
	 */
	public EventStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(EventStatus status) {
		this.status = status;
	}

}
