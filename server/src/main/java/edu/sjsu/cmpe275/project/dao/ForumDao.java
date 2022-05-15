package edu.sjsu.cmpe275.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.models.Forum;

@Repository
public interface ForumDao extends JpaRepository<Forum, Long> {

	Forum findByEventId(Long id);

}
