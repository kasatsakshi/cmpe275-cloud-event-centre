package edu.sjsu.cmpe275.project.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.EventDao;
import edu.sjsu.cmpe275.project.models.Address;
import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.types.AdmissionPolicy;

@Service
public class EventService {
	@Autowired
	EventDao eventDao;

	@Autowired
	UserService userService;

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
	 * Find event by ID
	 * 
	 * @param id
	 * @return
	 */
	public Event findEventById(Long id) {
		Optional<Event> event = eventDao.findById(id);
		return event.isPresent() ? event.get() : null;
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

}
