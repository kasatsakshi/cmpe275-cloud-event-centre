package edu.sjsu.cmpe275.project.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe275.project.types.ForumStatus;
import edu.sjsu.cmpe275.project.types.ForumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "FORUMS")
@JsonPropertyOrder({ "id", "type", "status", "event_id", "question_id" })
public class Forum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private ForumType type;

	@Column
	private ForumStatus status;

	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	@JsonIncludeProperties({ "id", "title", "status" })
	private Event event;

	@OneToMany(mappedBy = "forum", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({ "forum" })
	private Set<Question> questions;

	public Forum() {

	}

	public Forum(ForumType type, Event event) {
		super();
		this.type = type;
		this.status = ForumStatus.ACTIVE;
		this.event = event;
		this.questions = new HashSet<Question>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ForumType getType() {
		return type;
	}

	public void setType(ForumType type) {
		this.type = type;
	}

	public ForumStatus getStatus() {
		return status;
	}

	public void setStatus(ForumStatus status) {
		this.status = status;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question question) {
		this.questions.add(question);
	}

	@Override
	public String toString() {
		return "Forum [id=" + id + ", type=" + type + ", status=" + status + ", event=" + event + ", questions="
				+ questions + "]";
	}

}
