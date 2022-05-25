package edu.sjsu.cmpe275.project.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ParticipantReviews")
@JsonPropertyOrder({ "id", "participant_id", "event_id", "text", "rating" })
public class ParticipantReviews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "participant_id", nullable = false)
	@JsonIncludeProperties({ "id", "screenName" })
	private User user;

	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	@JsonIncludeProperties({ "id", "title" })
	private Event event;

	@Column
	private String text;

	@Column
	private int rating;

	public ParticipantReviews() {
	}

	public ParticipantReviews(User user, Event event, String text, int rating) {
		this.user = user;
		this.event = event;
		this.text = text;
		this.rating = rating;
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
