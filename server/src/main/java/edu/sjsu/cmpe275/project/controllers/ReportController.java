package edu.sjsu.cmpe275.project.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.models.SystemReport;
import edu.sjsu.cmpe275.project.models.UserReport;
import edu.sjsu.cmpe275.project.services.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	@Autowired
	ReportService reportService;

	@GetMapping("/system")
	public ResponseEntity<?> generateSystemReport(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime time) {
		SystemReport report = reportService.generateSystemReport(time);
		return new ResponseEntity<SystemReport>(report, HttpStatus.OK);

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> generateUserReport(@PathVariable Long id,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime time) {
		UserReport report = reportService.generateUserReport(id, time);
		return new ResponseEntity<UserReport>(report, HttpStatus.OK);
	}

}
