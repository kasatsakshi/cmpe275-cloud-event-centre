package edu.sjsu.cmpe275.project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.sjsu.cmpe275.project.types.RequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EVENT_REQUESTS")
public class EventRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	@JsonIgnoreProperties({ "address", "eventsCreated", "eventsRegistered", "password", "status", "accountType",
			"description", "authProvider", "gender" })
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "eventId")
	@JsonIgnoreProperties({ "creator", "address", "participants", "startTime", "endTime", "minimumParticipants",
			"admissionPolicy" })
	private Event event;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "creatorId")
	@JsonIgnoreProperties({ "address", "eventsCreated", "eventsRegistered", "password", "status", "accountType",
			"description", "authProvider", "gender" })
	private User creator;

	@Column
	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
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
	 * @return the status
	 */
	public RequestStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
}
