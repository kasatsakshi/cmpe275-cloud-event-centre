package edu.sjsu.cmpe275.project.models;

import java.util.Set;

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
@JsonPropertyOrder({ "id", "type", "status", "event_id", "question_id"})
public class Forum {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private ForumType type;
	
	@Column
	private ForumStatus status;
	
	@ManyToOne
	@JoinColumn(name="event_id", nullable=false)
	private Event event;
	
	@OneToMany(mappedBy="forum", fetch=FetchType.EAGER)
	private Set<Question> questions;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
