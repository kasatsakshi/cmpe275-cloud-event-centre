package edu.sjsu.cmpe275.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.project.models.Answer;

public interface AnswerDao extends JpaRepository<Answer, Long> {

}
