package edu.sjsu.cmpe275.project.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.sjsu.cmpe275.project.types.AdmissionPolicy;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EVENTS")
public class Event {

	@Id
	String id;

	@Column
	String title;

	@Column
	String description;

	@Column
	LocalDateTime startTime;

	@Column
	LocalDateTime endTime;

	@Column
	LocalDateTime deadline;

	@Embedded
	Address address;

	@Column
	Integer minimumParticipants;

	@Column
	Integer maximumParticipants;

	@Column
	Integer fee;

	@Column
	AdmissionPolicy admissionPolicy;

	@Embedded
	User creator;

}
