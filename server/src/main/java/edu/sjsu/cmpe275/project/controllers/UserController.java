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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.project.models.EventRequest;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.request.LoginRequest;
import edu.sjsu.cmpe275.project.request.SignUpRequest;
import edu.sjsu.cmpe275.project.services.EventService;
import edu.sjsu.cmpe275.project.services.UserService;
import edu.sjsu.cmpe275.project.types.AccountStatus;
import edu.sjsu.cmpe275.project.types.AuthProvider;
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
			return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"User not found\"}");
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
			return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"User not found\"}");
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
//	@PostMapping(params = { "fullName", "screenName", "email", "password", "accountType", "provider" })
	@PostMapping(path = "/register")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		if (signUpRequest.getFullName() == null || signUpRequest.getScreenName() == null
				|| signUpRequest.getEmail() == null || signUpRequest.getPassword() == null
				|| signUpRequest.getAccountType() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		User user = userService.findUserByEmail(signUpRequest.getEmail());
		if (user != null)
			return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"Email is already in use. Try logging in.\"}");
		else {
			String siteURL = request.getRequestURL().toString();
			siteURL = siteURL.replace(request.getServletPath(), "");
			user = userService.registerUser(signUpRequest.getFullName(), signUpRequest.getScreenName(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getGender(),
					signUpRequest.getAccountType(), signUpRequest.getProvider(), signUpRequest.getDescription(),
					signUpRequest.getStreet(), signUpRequest.getCity(), signUpRequest.getState(),
					signUpRequest.getZip(), siteURL);
			if (user == null)
				return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON)
						.body("{\"message\":\"Something went wrong\"}");
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}

	}

	@GetMapping(path = { "/verify" }, params = { "code" })
	public ResponseEntity<?> verifyUser(@RequestParam String code) {
		if (userService.verify(code)) {
			return ResponseEntity.status(302).location(URI.create("/")).build();
		} else {
			return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(
					"{\"message\":\"Sorry, we could not verify your account. It may already be verified, or verification code is incorrect.\"}");
		}
	}

	/**
	 * User Login
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
//	@PostMapping(params = { "email", "password", "provider" })
	@PostMapping(path = "/signin")
	public ResponseEntity<?> signIn(@Validated @RequestBody LoginRequest loginRequest) {
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		AuthProvider provider = loginRequest.getProvider();

		if (email == null || email == "" || password == null || password == "")
			return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"Email or Password can not be empty\"}");

		User user = userService.findUserByEmail(email);
		if (user == null)
			return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(
					"{\"message\":\"You are not registered with us. Please create an account to access our events\"}");
		else {
			if (user.getStatus() == AccountStatus.ACTIVE) {
				if (provider == AuthProvider.local) {
					if (user.getAuthProvider() == AuthProvider.google) {
						return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(
								"{\"message\":\"You signed up using your google account. Please use the same to login.\"}");
					}
					if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
						HashMap<String, Object> response = new HashMap<String, Object>();
						response.put("id", user.getId());
						response.put("fullName", user.getFullName());
						response.put("screenName", user.getScreenName());
						response.put("email", user.getEmail());
						response.put("status", user.getStatus());
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else {
						return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON)
								.body("{\"message\":\"Password you entered is incorrect.\"}");
					}
				} else if (provider == AuthProvider.google) {
//					if (user.getAuthProvider() == AuthProvider.local) {
//						return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(
//								"\"message\":\"You signed up using an email and password. Please use the same to login.\"");
//					}
					HashMap<String, Object> response = new HashMap<String, Object>();
					response.put("id", user.getId());
					response.put("fullName", user.getFullName());
					response.put("screenName", user.getScreenName());
					response.put("email", user.getEmail());
					response.put("status", user.getStatus());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			} else {
				return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON)
						.body("{\"message\":\"Please verify your email to access our events.\"}");
			}
		}
		return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON)
				.body("{\"message\":\"Something went wrong.\"}");
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
			return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"User not found\"}");
		else
			user = userService.updateUser(user, fullName, screenName, gender, description, street, city, state, zip);
		if (user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		else
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"Something went wrong\"}");
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
			return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"User not found\"}");
		else if (user.getStatus() == AccountStatus.ACTIVE)
			return ResponseEntity.status(403).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"User is already active\"}");
		else {
			user = userService.activateAccount(user, AccountStatus.ACTIVE);
			if (user != null)
				return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON)
				.body("{\"message\":\"Something went wrong\"}");
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
			return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"User not found\"}");
		List<EventRequest> myRequests = userService.myRequests(user);
		if (myRequests == null)
			return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"You have not requested signup for any event.\"}");
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
			return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"User not found\"}");
		List<EventRequest> requestsRecieved = userService.requestsRecieved(user);
		if (requestsRecieved == null)
			return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\":\"You have not recieved signup request for any event.\"}");
		return new ResponseEntity<List<EventRequest>>(requestsRecieved, HttpStatus.OK);
	}
}
