package edu.sjsu.cmpe275.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.request.ReviewRequest;
import edu.sjsu.cmpe275.project.services.ReviewService;

@RestController
@RequestMapping("/api/review")
@Transactional
public class ReviewController {

	@Autowired
	ReviewService reviewService;

	@GetMapping("/participant")
	public ResponseEntity<?> getParticipantReviews(@RequestParam Long id) {
		return new ResponseEntity<>(reviewService.getParticipantReviews(id), HttpStatus.OK);
	}

	@GetMapping("/organizer")
	public ResponseEntity<?> getOrganizerReviews(@RequestParam Long id) {
		return new ResponseEntity<>(reviewService.getOrganizerReviews(id), HttpStatus.OK);
	}

	@PostMapping("/participant")
	public ResponseEntity<?> reviewParticipant(@RequestBody ReviewRequest review) {
		return new ResponseEntity<>(reviewService.reviewParticipant(review.getUserId(), review.getEventId(),
				review.getText(), review.getRating()), HttpStatus.OK);
	}

	@PostMapping("/organizer")
	public ResponseEntity<?> reviewOrganizer(@RequestBody ReviewRequest review) {
		return new ResponseEntity<>(reviewService.reviewOrganizer(review.getUserId(), review.getEventId(),
				review.getText(), review.getRating()), HttpStatus.OK);
	}

}
