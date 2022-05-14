package edu.sjsu.cmpe275.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.ForumDao;
import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.Forum;
import edu.sjsu.cmpe275.project.types.ForumType;

@Service
public class ForumService {
	
	@Autowired
	ForumDao forumDao;
	
	public Forum createForum(Event event, ForumType forumType) {
		Forum forum = new Forum(forumType, event);
		return forumDao.save(forum);
	}
	
	public Forum findForum(Long id) {
		return forumDao.findByEventId(id);
	}
	
}
