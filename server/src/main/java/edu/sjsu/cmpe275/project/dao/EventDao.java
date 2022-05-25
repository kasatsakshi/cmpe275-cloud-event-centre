package edu.sjsu.cmpe275.project.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.types.EventStatus;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {

	List<Event> findByStartTimeBefore(LocalDateTime startTime);

	List<Event> findByEndTimeBefore(LocalDateTime endTime);

	List<Event> findByDeadlineBefore(LocalDateTime endTime);

	Integer countByCreationTimeBefore(LocalDateTime time);

	Integer countByCreationTimeBeforeAndFeeGreaterThan(LocalDateTime time, Integer fee);

	List<Event> findByDeadlineBeforeAndStatusEquals(LocalDateTime time, EventStatus status);

	List<Event> findByEndTimeBeforeAndStatusEquals(LocalDateTime time, EventStatus status);

}
