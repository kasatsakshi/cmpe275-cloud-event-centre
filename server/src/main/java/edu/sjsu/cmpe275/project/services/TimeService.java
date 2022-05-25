package edu.sjsu.cmpe275.project.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.Time;
import jakarta.annotation.PostConstruct;

@Service
public class TimeService {

	@Autowired
	Time time;

	@Autowired
	EventService eventService;

	@PostConstruct
	private void initTime() {
		this.time.setSystemTime(LocalDateTime.now());
	}

	public List<Event> setTime(LocalDateTime time) {
		List<Event> finishedEvents = eventService.eventStatusTrigger(time);
		this.time.setSystemTime(time);
		return finishedEvents;
	}

	public LocalDateTime getTime() {
		return this.time.getSystemTime();
	}
}
