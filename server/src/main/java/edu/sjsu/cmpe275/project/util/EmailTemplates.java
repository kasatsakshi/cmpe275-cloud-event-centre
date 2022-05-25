package edu.sjsu.cmpe275.project.util;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailTemplates {

	private Map<String, String> accountVerifiedEmail = Map.of("subject", "Account Verified", "body",
			"Dear [[name]],<br>" + "Your account has been verified successfully.<br>" + "Thank you,<br>" + "CEC");

	// For organizer
	private Map<String, String> eventCreatedEmail = Map.of("subject", "Event Created", "body", "Dear [[name]],<br>"
			+ "You have successfully created a new event <b>[[event]]</b>.<br>" + "Thank you,<br>" + "CEC");

	// For user
	private Map<String, String> eventSignupSuccessEmail = Map.of("subject", "Registered for event", "body",
			"Dear [[name]],<br>" + "You have successfully signed up for the event <b>[[event]]</b>.<br>"
					+ "Thank you,<br>" + "CEC");

	// For user
	private Map<String, String> signupRequestAcceptedEmail = Map.of("subject", "Signup Request Accepted", "body",
			"Dear [[name]],<br>" + "Your <b>[[event]]</b> event signup request has been accepted.<br>"
					+ "Thank you,<br>" + "CEC");

	// For user
	private Map<String, String> signupRequestRejectedEmail = Map.of("subject", "Signup Request Rejected", "body",
			"Dear [[name]],<br>" + "Your <b>[[event]]</b> event signup request has been rejected.<br>"
					+ "Thank you,<br>" + "CEC");

	// For Event participants and users with pending requests
	private Map<String, String> eventCancelledEmail = Map.of("subject", "Event Cancelled", "body", "Dear [[name]],<br>"
			+ "The event <b>[[event]]</b> you signed up for has been cancelled.<br>" + "Thank you,<br>" + "CEC");

	// For Event Participants
	private Map<String, String> eventStartedEmail = Map.of("subject", "Event has started", "body", "Dear [[name]],<br>"
			+ "The event <b>[[event]]</b> you signed up for has started.<br>" + "Thank you,<br>" + "CEC");

	// For Any user
	private Map<String, String> reviewReceivedEmail = Map.of("subject", "You received a review", "body",
			"Dear [[name]],<br>"
					+ "You have received a new review for the event <b>[[event]]</b>. Check your profile to see the review.<br>"
					+ "Thank you,<br>" + "CEC");

	// For organizer only
	private Map<String, String> messageInForumEmail = Map.of("subject", "New message in forum", "body",
			"Dear [[name]],<br>" + "A new message has been posted in the <b>[[event]]</b> event forum.<br>"
					+ "Thank you,<br>" + "CEC");

	/**
	 * @return the accountVerifiedEmail
	 */
	public Map<String, String> getAccountVerifiedEmail() {
		return accountVerifiedEmail;
	}

	/**
	 * @return the eventCreatedEmail
	 */
	public Map<String, String> getEventCreatedEmail() {
		return eventCreatedEmail;
	}

	/**
	 * @return the eventSignupSuccessEmail
	 */
	public Map<String, String> getEventSignupSuccessEmail() {
		return eventSignupSuccessEmail;
	}

	/**
	 * @return the signupRequestAcceptedEmail
	 */
	public Map<String, String> getSignupRequestAcceptedEmail() {
		return signupRequestAcceptedEmail;
	}

	/**
	 * @return the signupRequestRejectedEmail
	 */
	public Map<String, String> getSignupRequestRejectedEmail() {
		return signupRequestRejectedEmail;
	}

	/**
	 * @return the eventCancelledEmail
	 */
	public Map<String, String> getEventCancelledEmail() {
		return eventCancelledEmail;
	}

	/**
	 * @return the eventStartedEmail
	 */
	public Map<String, String> getEventStartedEmail() {
		return eventStartedEmail;
	}

	/**
	 * @return the reviewReceivedEmail
	 */
	public Map<String, String> getReviewReceivedEmail() {
		return reviewReceivedEmail;
	}

	/**
	 * @return the messageInForumEmail
	 */
	public Map<String, String> getMessageInForumEmail() {
		return messageInForumEmail;
	}

}
