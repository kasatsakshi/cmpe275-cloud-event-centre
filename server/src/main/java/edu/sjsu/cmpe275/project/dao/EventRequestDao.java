package edu.sjsu.cmpe275.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.EventRequest;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.types.RequestStatus;

public interface EventRequestDao extends JpaRepository<EventRequest, Long> {

	/**
	 * Return request by Participant and Event
	 * 
	 * @param user
	 * @param event
	 * @return
	 */
	Optional<EventRequest> findByUserAndEvent(User user, Event event);

	/**
	 * Return request by creator
	 * 
	 * @param creator
	 * @return
	 */
	Optional<List<EventRequest>> findByCreator(User creator);

	/**
	 * Return request by event
	 * 
	 * @param event
	 * @return
	 */
	Optional<List<EventRequest>> findByEvent(Event event);

	/**
	 * Return request by participant
	 * 
	 * @param participant
	 * @return
	 */
	Optional<List<EventRequest>> findByUser(User participant);

	Integer countByUser(User user);

	Integer countByUserAndStatusEquals(User user, RequestStatus status);
	
}
