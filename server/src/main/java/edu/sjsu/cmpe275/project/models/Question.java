package edu.sjsu.cmpe275.project.models;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "QUESTIONS")
@JsonPropertyOrder({ "id", "text", "pictureURL", "user_id", "answer_id","forum_id"})
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String text;
	
	@Column
	private String pictureURL;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="forum_id", nullable=false)
	private Forum forum;
	
	@OneToMany(mappedBy="question")
    private Set<Answer> answers;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
