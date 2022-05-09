package edu.sjsu.cmpe275.project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.EventDao;
import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.models.Address;
import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.types.AdmissionPolicy;

@Service
public class EventService {
	@Autowired
	EventDao eventDao;

	@Autowired
	UserDao userDao;

	@Autowired
	UserService userService;

	/**
	 * Find event by ID
	 * 
	 * @param id
	 * @return
	 */
	public Event findEventById(Long id) {
		Optional<Event> event = eventDao.findById(id);
		return event.isPresent() ? event.get() : null;
	}

	/**
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param deadline
	 * @param minimumParticipants
	 * @param maximumParticipants
	 * @param fee
	 * @param admissionPolicy
	 * @param creatorId
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	public Event createEvent(String title, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime deadline,
			Integer minimumParticipants, Integer maximumParticipants, Integer fee, AdmissionPolicy admissionPolicy,
			Long creatorId, Optional<String> description, Optional<String> street, Optional<String> city,
			Optional<String> state, Optional<String> zip) {
		Event event = new Event();
		User creator = userService.findUserById(creatorId);
		if (creator == null) {
			return null;
		}
		setValues(event, title, startTime, endTime, deadline, minimumParticipants, maximumParticipants, fee,
				admissionPolicy, creator, description, street, city, state, zip);
		Event response = eventDao.save(event);

		return response;
	}

	/**
	 * @param id
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param deadline
	 * @param minimumParticipants
	 * @param maximumParticipants
	 * @param fee
	 * @param admissionPolicy
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	public Event updateEvent(Long id, Optional<String> title, Optional<LocalDateTime> startTime,
			Optional<LocalDateTime> endTime, Optional<LocalDateTime> deadline, Optional<Integer> minimumParticipants,
			Optional<Integer> maximumParticipants, Optional<Integer> fee, Optional<AdmissionPolicy> admissionPolicy,
			Optional<String> description, Optional<String> street, Optional<String> city, Optional<String> state,
			Optional<String> zip) {
		Event event = findEventById(id);
		if (event == null)
			return null;
		setValues(event, title, startTime, endTime, deadline, minimumParticipants, maximumParticipants, fee,
				admissionPolicy, description, street, city, state, zip);
		Event response = eventDao.save(event);

		return response;
	}

	/**
	 * @param userId
	 * @param eventId
	 * @return
	 */
	public Event addParticipant(Long userId, Long eventId) {
		User user = userService.findUserById(userId);
		Event event = findEventById(eventId);

		// Add event to User registered events
		List<Event> registeredEvents = user.getEventsRegistered();
		if (registeredEvents.contains(event)) {
			return null;
		} else
			registeredEvents.add(event);
		user.setEventsRegistered(registeredEvents);
		userDao.save(user);

		// Add user to event participants
		List<User> participants = event.getParticipants();
		if (participants.contains(user)) {
			return null;
		} else
			participants.add(user);
		event.setParticipants(participants);
		eventDao.save(event);
		return event;
	}

	/**
	 * @param id
	 * @return
	 */
	public Event cancelEvent(Long id) {
		Event event = findEventById(id);
		eventDao.deleteById(id);
		return event;
	}

	private void setValues(Event event, String title, LocalDateTime startTime, LocalDateTime endTime,
			LocalDateTime deadline, Integer minimumParticipants, Integer maximumParticipants, Integer fee,
			AdmissionPolicy admissionPolicy, User creator, Optional<String> description, Optional<String> street,
			Optional<String> city, Optional<String> state, Optional<String> zip) {
		event.setTitle(title);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		event.setDeadline(deadline);
		event.setMinimumParticipants(minimumParticipants);
		event.setMaximumParticipants(maximumParticipants);
		event.setFee(fee);
		event.setAdmissionPolicy(admissionPolicy);
		event.setCreator(creator);
		if (description.isPresent())
			event.setDescription(description.get());
		Address address = new Address();
		if (street.isPresent())
			address.setStreet(street.get());
		if (city.isPresent())
			address.setCity(city.get());
		if (state.isPresent())
			address.setState(state.get());
		if (zip.isPresent())
			address.setZip(zip.get());
		event.setAddress(address);
	}

	private void setValues(Event event, Optional<String> title, Optional<LocalDateTime> startTime,
			Optional<LocalDateTime> endTime, Optional<LocalDateTime> deadline, Optional<Integer> minimumParticipants,
			Optional<Integer> maximumParticipants, Optional<Integer> fee, Optional<AdmissionPolicy> admissionPolicy,
			Optional<String> description, Optional<String> street, Optional<String> city, Optional<String> state,
			Optional<String> zip) {
		if (title.isPresent())
			event.setTitle(title.get());
		if (startTime.isPresent())
			event.setStartTime(startTime.get());
		if (endTime.isPresent())
			event.setEndTime(endTime.get());
		if (deadline.isPresent())
			event.setDeadline(deadline.get());
		if (minimumParticipants.isPresent())
			event.setMinimumParticipants(minimumParticipants.get());
		if (maximumParticipants.isPresent())
			event.setMaximumParticipants(maximumParticipants.get());
		if (fee.isPresent())
			event.setFee(fee.get());
		if (admissionPolicy.isPresent())
			event.setAdmissionPolicy(admissionPolicy.get());
		if (description.isPresent())
			event.setDescription(description.get());
		Address address = new Address();
		if (street.isPresent())
			address.setStreet(street.get());
		if (city.isPresent())
			address.setCity(city.get());
		if (state.isPresent())
			address.setState(state.get());
		if (zip.isPresent())
			address.setZip(zip.get());
		event.setAddress(address);
	}

}
