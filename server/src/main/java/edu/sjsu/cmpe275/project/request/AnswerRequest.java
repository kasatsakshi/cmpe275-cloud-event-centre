package edu.sjsu.cmpe275.project.request;

import java.util.Optional;

public class AnswerRequest {

	Long questionId;
	Long userId;
	String text;
	Optional<String> pictureUrl;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
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
