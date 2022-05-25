package edu.sjsu.cmpe275.project.services;

import java.io.UnsupportedEncodingException;
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
import edu.sjsu.cmpe275.project.types.EventStatus;
import edu.sjsu.cmpe275.project.types.ForumStatus;
import edu.sjsu.cmpe275.project.types.ForumType;
import edu.sjsu.cmpe275.project.util.EmailTemplates;
import jakarta.mail.MessagingException;

@Service
public class EventService {
	@Autowired
	EventDao eventDao;

	@Autowired
	UserDao userDao;

	@Autowired
	UserService userService;

	@Autowired
	ForumService forumService;

	@Autowired
	NotificationService notificationService;

	private EmailTemplates emailTemplates;

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
	 * Find all events based on filter
	 * 
	 * @param city
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Event> getAllEvents(Optional<String> city, Optional<EventStatus> status,
			Optional<LocalDateTime> startTime, Optional<LocalDateTime> endTime) {
		List<Event> events = null;
		events = eventDao.findAll();

		return events;
	}

	/**
	 * Create new event
	 * 
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
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public Event createEvent(String title, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime deadline,
			Integer minimumParticipants, Integer maximumParticipants, Integer fee, AdmissionPolicy admissionPolicy,
			Long creatorId, Optional<String> description, Optional<String> street, Optional<String> city,
			Optional<String> state, Optional<String> zip, EventStatus status)
			throws UnsupportedEncodingException, MessagingException {
		Event event = new Event();
		User creator = userService.findUserById(creatorId);
		if (creator == null) {
			return null;
		}
		setValues(event, title, LocalDateTime.now(), startTime, endTime, deadline, minimumParticipants,
				maximumParticipants, fee, admissionPolicy, creator, description, street, city, state, zip, status);
		Event response = eventDao.save(event);

		forumService.createForum(event, ForumType.SIGNUP, ForumStatus.ACTIVE);
		forumService.createForum(event, ForumType.PARTICIPANT, ForumStatus.CLOSED);

		notificationService.sendEmailNotification(creator, event, emailTemplates.getEventCreatedEmail());
		return response;
	}

	/**
	 * Update event info
	 * 
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
	public Event updateEvent(Event event, Optional<String> title, Optional<LocalDateTime> startTime,
			Optional<LocalDateTime> endTime, Optional<LocalDateTime> deadline, Optional<Integer> minimumParticipants,
			Optional<Integer> maximumParticipants, Optional<Integer> fee, Optional<AdmissionPolicy> admissionPolicy,
			Optional<String> description, Optional<String> street, Optional<String> city, Optional<String> state,
			Optional<String> zip) {
//		Event event = findEventById(id);
		if (event == null)
			return null;
		setValues(event, title, startTime, endTime, deadline, minimumParticipants, maximumParticipants, fee,
				admissionPolicy, description, street, city, state, zip);
		Event response = eventDao.save(event);
		return response;
	}

	/**
	 * Add Participant to FCFS event
	 * 
	 * @param userId
	 * @param eventId
	 * @return
	 */
	public Event addParticipant(User participant, Event event) {
		List<Event> registeredEvents = participant.getEventsRegistered();
		if (registeredEvents.contains(event)) {
			return null;
		} else
			registeredEvents.add(event);
		participant.setEventsRegistered(registeredEvents);
		userDao.save(participant);

		// Add user to event participants
		List<User> participants = event.getParticipants();
		if (participants.contains(participant)) {
			return null;
		} else
			participants.add(participant);
		event.setParticipants(participants);
		Event response = eventDao.save(event);
		try {
			notificationService.sendEmailNotification(participant, event, emailTemplates.getEventSignupSuccessEmail());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public List<Event> eventStatusTrigger(LocalDateTime endTime) {
		List<Event> registrationClosedEvents = eventDao.findByDeadlineBefore(endTime);
		registrationClosedEvents.forEach((event) -> {
			if (event.getParticipants().size() < event.getMinimumParticipants()) {
				event.setStatus(EventStatus.CANCELLED);
				for (int i = 0; i < event.getParticipants().size(); i++) {
					try {
						notificationService.sendEmailNotification(event.getParticipants().get(i), event,
								emailTemplates.getEventCancelledEmail());
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				event.setStatus(EventStatus.REGISTRATION_CLOSED);

			}
			eventDao.save(event);
		});
		List<Event> activeEvents = eventDao.findByStartTimeBefore(endTime);
		activeEvents.forEach((event) -> {
			event.setStatus(EventStatus.ACTIVE);
			for (int i = 0; i < event.getParticipants().size(); i++) {
				try {
					notificationService.sendEmailNotification(event.getParticipants().get(i), event,
							emailTemplates.getEventStartedEmail());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			eventDao.save(event);
		});
		List<Event> finishedEvents = eventDao.findByEndTimeBefore(endTime);
		finishedEvents.forEach((event) -> {
			event.setStatus(EventStatus.FINISHED);
			eventDao.save(event);
		});
		finishedEvents.addAll(registrationClosedEvents);
		return finishedEvents;
	}

	/**
	 * Cancel event
	 * 
	 * @param id
	 * @return
	 */
	public Event finishEvent(Event event) {
		List<User> participants = event.getParticipants();
		if (participants.size() < event.getMinimumParticipants())
			event.setStatus(EventStatus.CANCELLED);
		else
			event.setStatus(EventStatus.FINISHED);
		Event response = eventDao.save(event);
		return response;
	}

	/**
	 * Add new entry
	 * 
	 * @param event
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
	 */
	private void setValues(Event event, String title, LocalDateTime creationTime, LocalDateTime startTime,
			LocalDateTime endTime, LocalDateTime deadline, Integer minimumParticipants, Integer maximumParticipants,
			Integer fee, AdmissionPolicy admissionPolicy, User creator, Optional<String> description,
			Optional<String> street, Optional<String> city, Optional<String> state, Optional<String> zip,
			EventStatus status) {
		event.setTitle(title);
		event.setCreationTime(creationTime);
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
		event.setStatus(status);
	}

	/**
	 * Update existing entry
	 * 
	 * @param event
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
	 */
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

		Address address = event.getAddress();
		if (address == null)
			address = new Address();
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
