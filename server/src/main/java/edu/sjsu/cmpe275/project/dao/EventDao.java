package edu.sjsu.cmpe275.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.models.Event;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {

}
