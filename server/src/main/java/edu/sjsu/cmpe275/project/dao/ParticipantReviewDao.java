package edu.sjsu.cmpe275.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.models.ParticipantReviews;

@Repository
public interface ParticipantReviewDao extends JpaRepository<ParticipantReviews, Long> {

	Optional<List<ParticipantReviews>> findByUserId(Long id);

}
