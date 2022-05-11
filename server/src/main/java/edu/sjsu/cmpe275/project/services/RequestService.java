package edu.sjsu.cmpe275.project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.EventDao;
import edu.sjsu.cmpe275.project.dao.EventRequestDao;
import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.EventRequest;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.types.RequestStatus;

/**
 * @author Siddharth Sircar
 *
 */
@Service
public class RequestService {
	@Autowired
	EventDao eventDao;

	@Autowired
	UserDao userDao;

	@Autowired
	EventService eventService;

	@Autowired
	EventRequestDao eventRequestDao;

	/**
	 * Send Request for Approval Event
	 * 
	 * @param userId
	 * @param eventId
	 * @return
	 */
	public EventRequest sendRequest(User participant, Event event) {
		Optional<EventRequest> request = eventRequestDao.findByUserAndEvent(participant, event);
		if (request.isEmpty()) {
			EventRequest eventRequest = new EventRequest();
			eventRequest.setUser(participant);
			eventRequest.setEvent(event);
			eventRequest.setCreator(event.getCreator());
			eventRequest.setStatus(RequestStatus.PENDING);
			eventRequestDao.save(eventRequest);
			return eventRequest;
		}
		return null;
	}

	/**
	 * Update request status: Accepted / Rejected
	 * 
	 * @param userId
	 * @param eventId
	 * @return
	 */
	public EventRequest updateRequest(EventRequest request, RequestStatus status) {
		request.setStatus(status);
		EventRequest response = eventRequestDao.save(request);

		if (status == RequestStatus.ACCEPTED) {
			eventService.addParticipant(request.getUser(), request.getEvent());
		}
		return response;
	}

	/**
	 * Find request by id
	 * 
	 * @param id
	 * @return
	 */
	public EventRequest findById(Long id) {
		Optional<EventRequest> request = eventRequestDao.findById(id);

		return request.isPresent() ? request.get() : null;
	}
}
