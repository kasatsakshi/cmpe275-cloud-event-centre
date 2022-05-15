package edu.sjsu.cmpe275.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.request.AnswerRequest;
import edu.sjsu.cmpe275.project.request.QuestionRequest;
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

	@PostMapping("/question")
	public ResponseEntity<?> askQuestion(@RequestBody QuestionRequest question) {
		return new ResponseEntity<>(forumService.askQuestion(question.getForumId(), question.getUserId(),
				question.getText(), question.getPictureUrl()), HttpStatus.OK);
	}

	@PostMapping("/answer")
	public ResponseEntity<?> addAnswer(@RequestBody AnswerRequest answer) {
		return new ResponseEntity<>(forumService.addAnswer(answer.getQuestionId(), answer.getUserId(), answer.getText(),
				answer.getPictureUrl()), HttpStatus.OK);
	}

}
