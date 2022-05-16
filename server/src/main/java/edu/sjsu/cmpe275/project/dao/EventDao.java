package edu.sjsu.cmpe275.project.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.models.Event;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {

	List<Event> findByEndTimeBefore(LocalDateTime endTime);

	List<Event> findByDeadlineBefore(LocalDateTime endTime);

}
