package edu.sjsu.cmpe275.project.request;

import java.util.Optional;

public class QuestionRequest {

	Long forumId;
	Long userId;
	String text;
	Optional<String> pictureUrl;

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Optional<String> getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(Optional<String> pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

}
