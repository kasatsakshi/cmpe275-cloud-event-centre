package edu.sjsu.cmpe275.project.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "QUESTIONS")
@JsonPropertyOrder({ "id", "text", "pictureUrl", "user_id", "answer_id", "forum_id" })
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String text;

	@Column
	private String pictureUrl;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "forum_id", nullable = false)
	private Forum forum;

	@OneToMany(mappedBy = "question")
	private Set<Answer> answers;

	public Question() {
	}

	public Question(Forum forum, User user, String text, String pictureUrl) {
		this.forum = forum;
		this.user = user;
		this.text = text;
		this.pictureUrl = pictureUrl;
		this.answers = new HashSet<Answer>();
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

	public String getPictureURL() {
		return pictureUrl;
	}

	public void setPictureURL(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", pictureUrl=" + pictureUrl + ", user=" + user + ", forum="
				+ forum + ", answers=" + answers + "]";
	}

}
