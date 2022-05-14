package edu.sjsu.cmpe275.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.services.ForumService;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

	@Autowired
	ForumService forumService;

	@GetMapping
	public ResponseEntity<?> findForum(@RequestParam Long eventId) {
		return new ResponseEntity<>(forumService.findForum(eventId), HttpStatus.OK);
	}
}
