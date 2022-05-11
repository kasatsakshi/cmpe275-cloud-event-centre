package edu.sjsu.cmpe275.project.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.models.Time;
import jakarta.annotation.PostConstruct;

@Service
public class TimeService {

	@Autowired
	Time time;

	@PostConstruct
	private void initTime() {
		this.time.setSystemTime(LocalDateTime.now());
	}

	public boolean setTime(String time) {
		LocalDateTime dateTime = LocalDateTime.parse(time);
		this.time.setSystemTime(dateTime);
		return true;
	}

	public LocalDateTime getTime() {
		return this.time.getSystemTime();
	}
}
