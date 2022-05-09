package edu.sjsu.cmpe275.project.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe275.project.types.AdmissionPolicy;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EVENTS")
@JsonPropertyOrder({ "id", "title", "description", "fee", "admissionPolicy", "startTime", "endTime", "deadline", "creatorId", "minimumParticipants", "maximumParticipants", "address" })
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "creatorId")
	@JsonIgnoreProperties({ "address", "eventsCreated", "eventsRegistered", "password" })
	private User creator;

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

}
