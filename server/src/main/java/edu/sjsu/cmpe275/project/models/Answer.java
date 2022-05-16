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
@Table(name = "ANSWERS")
@JsonPropertyOrder({ "id", "text", "pictureUrl", "user_id", "question_id" })
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String text;

	@Column
	private String pictureUrl;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIncludeProperties({ "id", "screenName" })
	private User user;

	@ManyToOne
	@JoinColumn(name = "question_id", nullable = false)
	@JsonIncludeProperties({ "id", "text" })
	private Question question;

	public Answer() {
	}

	public Answer(Question question, User user, String text, String pictureUrl) {
		this.question = question;
		this.user = user;
		this.text = text;
		this.pictureUrl = pictureUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
