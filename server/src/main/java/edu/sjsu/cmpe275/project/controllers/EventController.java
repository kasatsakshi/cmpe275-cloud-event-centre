package edu.sjsu.cmpe275.project.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.services.EventService;
import edu.sjsu.cmpe275.project.services.UserService;
import edu.sjsu.cmpe275.project.types.AdmissionPolicy;

@RestController
@RequestMapping("/api/event")
@Transactional
public class EventController {
	@Autowired
	UserService userService;

	@Autowired
	EventService eventService;

	/**
	 * Endpoint for fetching event information by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable Long id) {
		Event event = eventService.findEventById(id);
		if (event != null) {
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		}
		return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
	}

	/**
	 * Endpoint for fetching all events
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAllEvents() {
		List<Event> events = eventService.getAllEvents();
		if (events != null) {
			return new ResponseEntity<>(events, HttpStatus.OK);
		}
		return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Endpoint for creating event
	 * 
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param deadline
	 * @param minimumParticipants
	 * @param maximumParticipants
	 * @param fee
	 * @param admissionPolicy
	 * @param creator
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	@PostMapping(params = { "title", "startTime", "endTime", "deadline", "minimumParticipants", "maximumParticipants",
			"fee", "admissionPolicy", "creatorId" })
	public ResponseEntity<?> createEvent(@RequestParam String title,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startTime,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endTime,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline,
			@RequestParam Integer minimumParticipants, @RequestParam Integer maximumParticipants,
			@RequestParam Integer fee, @RequestParam AdmissionPolicy admissionPolicy, @RequestParam Long creatorId,
			@RequestParam Optional<String> description, @RequestParam Optional<String> street,
			@RequestParam Optional<String> city, @RequestParam Optional<String> state,
			@RequestParam Optional<String> zip) {
		if (title == null || startTime == null || endTime == null || deadline == null || minimumParticipants == null
				|| maximumParticipants == null || fee == null || admissionPolicy == null || creatorId == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Event event = eventService.createEvent(title, startTime, endTime, deadline, minimumParticipants,
				maximumParticipants, fee, admissionPolicy, creatorId, description, street, city, state, zip);
		if (event != null)
			return new ResponseEntity<Event>(event, HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Endpoint for creating event
	 * 
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param deadline
	 * @param minimumParticipants
	 * @param maximumParticipants
	 * @param fee
	 * @param admissionPolicy
	 * @param creator
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestParam Optional<String> title,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Optional<LocalDateTime> startTime,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Optional<LocalDateTime> endTime,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Optional<LocalDateTime> deadline,
			@RequestParam Optional<Integer> minimumParticipants, @RequestParam Optional<Integer> maximumParticipants,
			@RequestParam Optional<Integer> fee, @RequestParam Optional<AdmissionPolicy> admissionPolicy,
			@RequestParam Optional<String> description, @RequestParam Optional<String> street,
			@RequestParam Optional<String> city, @RequestParam Optional<String> state,
			@RequestParam Optional<String> zip) {

		Event event = eventService.findEventById(id);
		if (event == null)
			return new ResponseEntity<>("No such event created.", HttpStatus.NOT_FOUND);
		else {
			event = eventService.updateEvent(id, title, startTime, endTime, deadline, minimumParticipants,
					maximumParticipants, fee, admissionPolicy, description, street, city, state, zip);
			if (event != null)
				return new ResponseEntity<Event>(event, HttpStatus.OK);
			else
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelEvent(@PathVariable Long id) {
		Event event = eventService.findEventById(id);
		if (event == null) {
			return new ResponseEntity<>("No such event created", HttpStatus.NOT_FOUND);
		} else {
			List<User> participants = event.getParticipants();
			if (participants.size() < event.getMinimumParticipants()) {
				event = eventService.cancelEvent(id);
				return new ResponseEntity<Event>(event, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param userId
	 * @param eventId
	 * @return
	 */
	@PostMapping(path = "/register", params = { "userId", "eventId" })
	public ResponseEntity<?> registerToEvent(@RequestParam Long userId, @RequestParam Long eventId) {
		User user = userService.findUserById(userId);
		if (user == null)
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		Event event = eventService.findEventById(eventId);
		if (event == null)
			return new ResponseEntity<>("Event does not exist", HttpStatus.NOT_FOUND);

//		user = userService.registerEvent(userId, eventId);

		event = eventService.addParticipant(userId, eventId);

		if (event == null)
			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		else
			return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
}
