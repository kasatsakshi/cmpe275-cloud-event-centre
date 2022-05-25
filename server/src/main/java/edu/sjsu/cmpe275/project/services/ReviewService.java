package edu.sjsu.cmpe275.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.EventDao;
import edu.sjsu.cmpe275.project.dao.OrganizerReviewDao;
import edu.sjsu.cmpe275.project.dao.ParticipantReviewDao;
import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.OrganizerReviews;
import edu.sjsu.cmpe275.project.models.ParticipantReviews;
import edu.sjsu.cmpe275.project.models.User;

@Service
public class ReviewService {

	@Autowired
	ParticipantReviewDao participantReviewDao;

	@Autowired
	OrganizerReviewDao organizerReviewDao;

	@Autowired
	UserDao userDao;

	@Autowired
	EventDao eventDao;

	/**
	 * Get participant reviews
	 * 
	 * @param participantId
	 * @return
	 */
	public List<ParticipantReviews> getParticipantReviews(Long participantId) {
		Optional<List<ParticipantReviews>> reviews = participantReviewDao.findByUserId(participantId);
		return reviews.isPresent() ? reviews.get() : null;
	}

	/**
	 * Get organizer reviews
	 * 
	 * @param organizerId
	 * @return
	 */
	public List<OrganizerReviews> getOrganizerReviews(Long organizerId) {
		Optional<List<OrganizerReviews>> reviews = organizerReviewDao.findByUserId(organizerId);
		return reviews.isPresent() ? reviews.get() : null;
	}

	/**
	 * Add review for participant
	 * 
	 * @param userId
	 * @param eventId
	 * @param text
	 * @param rating
	 * @return
	 */
	public ParticipantReviews reviewParticipant(Long userId, Long eventId, String text, int rating) {
		User user = userDao.findById(userId).get();
		Event event = eventDao.findById(eventId).get();
		ParticipantReviews review = new ParticipantReviews(user, event, text, rating);
		review = participantReviewDao.save(review);

		Optional<List<ParticipantReviews>> reviews = participantReviewDao.findByUserId(userId);
		if (reviews.isPresent()) {
			int reviewRating = 0;
			for (int i = 0; i < reviews.get().size(); i++) {
				reviewRating += reviews.get().get(i).getRating();
			}
			reviewRating = reviewRating / reviews.get().size();
			user.setParticipantReputation(reviewRating);
			userDao.save(user);
		}
		return review;
	}

	/**
	 * Add review for organizer
	 * 
	 * @param userId
	 * @param eventId
	 * @param text
	 * @param rating
	 * @return
	 */
	public OrganizerReviews reviewOrganizer(Long userId, Long eventId, String text, int rating) {
		User user = userDao.findById(userId).get();
		Event event = eventDao.findById(eventId).get();
		OrganizerReviews review = new OrganizerReviews(user, event, text, rating);
		review = organizerReviewDao.save(review);

		Optional<List<OrganizerReviews>> reviews = organizerReviewDao.findByUserId(userId);
		if (reviews.isPresent()) {
			int reviewRating = 0;
			for (int i = 0; i < reviews.get().size(); i++) {
				reviewRating += reviews.get().get(i).getRating();
			}
			reviewRating = (reviewRating / reviews.get().size());
			user.setOrganizerReputation(reviewRating);
			userDao.save(user);
		}

		return review;
	}
}
