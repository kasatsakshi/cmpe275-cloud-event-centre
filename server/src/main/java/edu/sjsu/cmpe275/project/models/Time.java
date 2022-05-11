package edu.sjsu.cmpe275.project.models;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Time {

	private LocalDateTime systemTime;

	public LocalDateTime getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(LocalDateTime systemTime) {
		this.systemTime = systemTime;
	}

	@Override
	public String toString() {
		return "Time [systemTime=" + systemTime + "]";
	}

}
