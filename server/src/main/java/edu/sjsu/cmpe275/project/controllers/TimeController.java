package edu.sjsu.cmpe275.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.services.TimeService;

@RestController
@RequestMapping("/api/time")
public class TimeController {

	@Autowired
	private TimeService timeService;

	@PostMapping
	public ResponseEntity<?> setTime(@RequestParam String dateTime) {
		timeService.setTime(dateTime);
		return ResponseEntity.ok(timeService.getTime());
	}

	@GetMapping
	public ResponseEntity<?> getTime() {
		return ResponseEntity.ok(timeService.getTime());
	}

}
