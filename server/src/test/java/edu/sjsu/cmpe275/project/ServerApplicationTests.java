package edu.sjsu.cmpe275.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sjsu.cmpe275.project.controllers.EventController;
import edu.sjsu.cmpe275.project.dao.EventDao;
import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.request.CreateEventRequest;
import edu.sjsu.cmpe275.project.services.EventService;
import edu.sjsu.cmpe275.project.types.AdmissionPolicy;
import jakarta.mail.MessagingException;

@SpringBootTest
class ServerApplicationTests {

	@Autowired
	EventController controller;

	@Autowired
	EventService service;

	@Autowired
	EventDao dao;

	@Test
	void testCreateEvent() throws UnsupportedEncodingException, MessagingException {

		CreateEventRequest request = new CreateEventRequest();
		request.setTitle("Junit Test Event");
		request.setStartTime(LocalDateTime.now());
		request.setEndTime(LocalDateTime.now());
		request.setDeadline(LocalDateTime.now());
		request.setMinimumParticipants(1);
		request.setMaximumParticipants(10);
		request.setFee(5);
		request.setAdmissionPolicy(AdmissionPolicy.FCFS);
		request.setCreatorId((Long.valueOf(7)));
		request.setDescription(Optional.of("Test description"));
		request.setStreet(Optional.of("Test street"));
		request.setCity(Optional.of("Test city"));
		request.setState(Optional.of("Test state"));
		request.setZip(Optional.of("775630"));

		Event createdEvent = (Event) controller.createEvent(request).getBody();
		System.out.println(createdEvent.toString());

		Event event = (Event) controller.getEvent(createdEvent.getId()).getBody();
		assertThat(event.equals(createdEvent));
	}

	@Test
	void testGetEvents() {

		Long id = Long.valueOf(11);
		Event event = (Event) controller.getEvent(id).getBody();
		assertThatObject(event);

	}

}
