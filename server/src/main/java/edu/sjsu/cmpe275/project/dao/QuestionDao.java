package edu.sjsu.cmpe275.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.project.models.Question;

public interface QuestionDao extends JpaRepository<Question, Long> {

}
