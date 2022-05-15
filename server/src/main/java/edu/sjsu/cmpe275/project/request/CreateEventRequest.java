package edu.sjsu.cmpe275.project.request;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

import edu.sjsu.cmpe275.project.types.AdmissionPolicy;

public class CreateEventRequest {
	private String title;
	private @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startTime;
	private @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endTime;
	private @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline;
	private Integer minimumParticipants;
	private Integer maximumParticipants;
	private Integer fee;
	private AdmissionPolicy admissionPolicy;
	private Long creatorId;
	private Optional<String> description;
	private Optional<String> street;
	private Optional<String> city;
	private Optional<String> state;
	private Optional<String> zip;

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
	 * @return the creatorId
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
