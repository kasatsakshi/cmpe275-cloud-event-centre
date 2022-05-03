package edu.sjsu.cmpe275.project.models;

import java.util.ArrayList;

import edu.sjsu.cmpe275.project.types.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	Long id;

	@Column(nullable = false, unique = true)
	String email;

	@Column(nullable = false)
	String fullName;

	@Column(nullable = false)
	AccountType accountType;

	@Column(nullable = false)
	String screenName;

	@Column
	String gender;

	@Column
	String description;

	@Embedded
	Address address;

	@ManyToMany
	ArrayList<Event> events;
}
