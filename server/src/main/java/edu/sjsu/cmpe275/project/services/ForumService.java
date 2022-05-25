package edu.sjsu.cmpe275.project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.AnswerDao;
import edu.sjsu.cmpe275.project.dao.ForumDao;
import edu.sjsu.cmpe275.project.dao.QuestionDao;
import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.models.Answer;
import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.Forum;
import edu.sjsu.cmpe275.project.models.Question;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.types.ForumStatus;
import edu.sjsu.cmpe275.project.types.ForumType;

@Service
public class ForumService {

	@Autowired
	ForumDao forumDao;

	@Autowired
	UserDao userDao;

	@Autowired
	QuestionDao questionDao;

	@Autowired
	AnswerDao answerDao;

	public Forum createForum(Event event, ForumType forumType, ForumStatus forumStatus) {
		Forum forum = new Forum(event, forumType, forumStatus);
		return forumDao.save(forum);
	}

	public Forum findForum(Long id) {
		return forumDao.findByEventId(id);
	}

	public Forum askQuestion(Long forumId, Long userId, String text, Optional<String> pictureUrl) {
		Forum forum = forumDao.findById(forumId).get();
		User user = userDao.findById(userId).get();
		Question question = new Question(forum, user, text, pictureUrl.orElse(null));
		questionDao.save(question);
		forum.addQuestion(question);
		return forumDao.save(forum);
	}

	public Question addAnswer(Long questionId, Long userId, String text, Optional<String> pictureUrl) {
		Question question = questionDao.findById(questionId).get();
		User user = userDao.findById(userId).get();
		Answer answer = new Answer(question, user, text, pictureUrl.orElse(null));
		question.addAnswer(answer);
		answerDao.save(answer);
		return questionDao.save(question);
	}

	public void forumStatusTrigger(LocalDateTime endTime) {
		List<Forum> closedSignupForums;
	}

}
