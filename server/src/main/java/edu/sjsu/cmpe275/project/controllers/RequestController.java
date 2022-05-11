package edu.sjsu.cmpe275.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.models.EventRequest;
import edu.sjsu.cmpe275.project.services.RequestService;
import edu.sjsu.cmpe275.project.types.RequestStatus;

@RestController
@RequestMapping("/api/eventrequest")
@Transactional
public class RequestController {

	@Autowired
	RequestService requestService;

	/**
	 * Accept sign up request
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/accept/{id}")
	public ResponseEntity<?> accept(@PathVariable Long id) {
		EventRequest request = requestService.findById(id);
		if (request == null)
			return new ResponseEntity<>("Request id is incorrect", HttpStatus.NOT_FOUND);
		request = requestService.updateRequest(request, RequestStatus.ACCEPTED);
		if (request == null)
			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<EventRequest>(request, HttpStatus.OK);
	}

	/**
	 * Reject sign up request
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/reject/{id}")
	public ResponseEntity<?> reject(@PathVariable Long id) {
		EventRequest request = requestService.findById(id);
		if (request == null)
			return new ResponseEntity<>("Request id is incorrect", HttpStatus.NOT_FOUND);
		request = requestService.updateRequest(request, RequestStatus.REJECTED);
		if (request == null)
			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<EventRequest>(request, HttpStatus.OK);
	}
}
