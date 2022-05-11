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

	public List<Event> setTime(String time) {
		LocalDateTime newTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
		List<Event> cancelledEvents = eventService.cancelEventTrigger(newTime);
		this.time.setSystemTime(newTime);
		return cancelledEvents;
	}

	public LocalDateTime getTime() {
		return this.time.getSystemTime();
	}
}
