package edu.sjsu.cmpe275.project.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.models.EventRequest;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.services.EventService;
import edu.sjsu.cmpe275.project.services.UserService;
import edu.sjsu.cmpe275.project.types.AccountStatus;
import edu.sjsu.cmpe275.project.types.AccountType;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@Transactional
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	EventService eventService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Endpoint for fetching user information by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(params = "id")
	public ResponseEntity<?> getUserDetails(@RequestParam Long id) {
		User user = userService.findUserById(id);
		if (user == null) {
			return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Endpoint for fetching user information by email
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping(params = "email")
	public ResponseEntity<?> getUserDetails(@RequestParam String email) {
		User user = userService.findUserByEmail(email);
		if (user == null) {
			return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Register User
	 * 
	 * @param fullName
	 * @param screenName
	 * @param email
	 * @param password
	 * @param accountType
	 * @param gender
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	@PostMapping(params = { "fullName", "screenName", "email", "password", "accountType" })
	public ResponseEntity<?> signUp(@RequestParam String fullName, @RequestParam String screenName,
			@RequestParam String email, @RequestParam String password, @RequestParam AccountType accountType,
			@RequestParam Optional<String> gender, @RequestParam Optional<String> description,
			@RequestParam Optional<String> street, @RequestParam Optional<String> city,
			@RequestParam Optional<String> state, @RequestParam Optional<String> zip, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		if (fullName == null || screenName == null || email == null || password == null || accountType == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		User user = userService.findUserByEmail(email);
		if (user != null)
			return new ResponseEntity<>("Email is already in use.", HttpStatus.BAD_REQUEST);
		else {
			String siteURL = request.getRequestURL().toString();
			siteURL = siteURL.replace(request.getServletPath(), "");
			user = userService.registerUser(fullName, screenName, email, password, gender, accountType, description,
					street, city, state, zip, siteURL);
			if (user == null)
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}

	}

	@GetMapping(path = { "/verify" }, params = { "code" })
	public ResponseEntity<?> verifyUser(@RequestParam String code) {
		if (userService.verify(code)) {
			return ResponseEntity.status(302).location(URI.create("http://localhost:3000/")).build();
		} else {
			return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(
					"\"message\":\"Sorry, we could not verify account. It maybe already verified, or verification code is incorrect.\"");
		}
	}

	/**
	 * User Login
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@PostMapping(params = { "email", "password" })
	public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String password) {
		if (email == null || email == "" || password == null || password == "")
			return new ResponseEntity<>("Email or Password can not be empty", HttpStatus.BAD_REQUEST);

		User user = userService.findUserByEmail(email);
		if (user == null)
			return new ResponseEntity<>("No such user exists", HttpStatus.NOT_FOUND);
		else {
			if (user.getStatus() == AccountStatus.ACTIVE) {
				if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
					HashMap<String, Object> response = new HashMap<String, Object>();
					response.put("id", user.getId());
					response.put("fullName", user.getFullName());
					response.put("screenName", user.getScreenName());
					response.put("email", user.getEmail());
					response.put("status", user.getStatus());
					response.put("eventsRegistered", user.getEventsRegistered());
					return new ResponseEntity<>(response, HttpStatus.OK);
//					return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON)
//							.body("\"message\":\"Logged in successfully\"");
				} else
					return new ResponseEntity<>("Password is incorrect.", HttpStatus.UNAUTHORIZED);
			} else
				return new ResponseEntity<>("Please verify your email", HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * Update user information
	 * 
	 * @param id
	 * @param fullName
	 * @param screenName
	 * @param gender
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestParam Optional<String> fullName,
			@RequestParam Optional<String> screenName, @RequestParam Optional<String> gender,
			@RequestParam Optional<String> description, @RequestParam Optional<String> street,
			@RequestParam Optional<String> city, @RequestParam Optional<String> state,
			@RequestParam Optional<String> zip) {
		User user = userService.findUserById(id);
		if (user == null)
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		else
			user = userService.updateUser(user, fullName, screenName, gender, description, street, city, state, zip);
		if (user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		else
			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Activate user account after verification link
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/activate/{id}")
	public ResponseEntity<?> activateAccount(@PathVariable Long id) {
		User user = userService.findUserById(id);
		if (user == null)
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		else if (user.getStatus() == AccountStatus.ACTIVE)
			return new ResponseEntity<>("User is already active", HttpStatus.FORBIDDEN);
		else {
			user = userService.activateAccount(user, AccountStatus.ACTIVE);
			if (user != null)
				return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Get user's event signup requests
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/signuprequests/{id}")
	public ResponseEntity<?> sentRequests(@PathVariable Long id) {
		User user = userService.findUserById(id);
		if (user == null)
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		List<EventRequest> myRequests = userService.myRequests(user);
		if (myRequests == null)
			return new ResponseEntity<>("You have not requested signup for any event", HttpStatus.OK);
		return new ResponseEntity<List<EventRequest>>(myRequests, HttpStatus.OK);
	}

	/**
	 * Get user's recieved requests for created events
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/recievedrequests/{id}")
	public ResponseEntity<?> recievedRequests(@PathVariable Long id) {
		User user = userService.findUserById(id);
		if (user == null)
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		List<EventRequest> requestsRecieved = userService.requestsRecieved(user);
		if (requestsRecieved == null)
			return new ResponseEntity<>("You have not recieved any requests for your events", HttpStatus.OK);
		return new ResponseEntity<List<EventRequest>>(requestsRecieved, HttpStatus.OK);
	}
}
