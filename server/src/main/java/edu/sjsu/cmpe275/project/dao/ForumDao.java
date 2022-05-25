package edu.sjsu.cmpe275.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.models.Forum;

@Repository
public interface ForumDao extends JpaRepository<Forum, Long> {

	List<Forum> findByEventId(Long id);

}
