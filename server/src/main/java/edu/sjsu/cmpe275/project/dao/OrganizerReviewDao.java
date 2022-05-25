package edu.sjsu.cmpe275.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.models.OrganizerReviews;

@Repository
public interface OrganizerReviewDao extends JpaRepository<OrganizerReviews, Long> {
	Optional<List<OrganizerReviews>> findByUserId(Long id);
}
