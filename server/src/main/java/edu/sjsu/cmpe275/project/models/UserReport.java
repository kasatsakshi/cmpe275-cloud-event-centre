package edu.sjsu.cmpe275.project.models;

public class UserReport {

	// Participant report
	Integer signups; // 1
	Integer rejects; // 2
	Integer approvals; // 2
	Integer participantFinishedEvents; // 3

	// Organizer report
	// 1
	Integer createdEvents;
	Double percentPaidEvents;
	// 2
	Integer canceledEvents;
	Double requestsPerMinParticipants;
	// 3
	Integer organiserFinishedEvents;
	Double avgParticipants;
	// 4
	Integer finishedPaidEvents;
	Integer totalRevenue;

	public UserReport(Integer signups, Integer rejects, Integer approvals, Integer participantFinishedEvents,
			Integer createdEvents, Double percentPaidEvents, Integer canceledEvents, Double requestsPerMinParticipants,
			Integer organiserFinishedEvents, Double avgParticipants, Integer finishedPaidEvents, Integer totalRevenue) {
		super();
		this.signups = signups;
		this.rejects = rejects;
		this.approvals = approvals;
		this.participantFinishedEvents = participantFinishedEvents;
		this.createdEvents = createdEvents;
		this.percentPaidEvents = percentPaidEvents;
		this.canceledEvents = canceledEvents;
		this.requestsPerMinParticipants = requestsPerMinParticipants;
		this.organiserFinishedEvents = organiserFinishedEvents;
		this.avgParticipants = avgParticipants;
		this.finishedPaidEvents = finishedPaidEvents;
		this.totalRevenue = totalRevenue;
	}

	public Integer getSignups() {
		return signups;
	}

	public void setSignups(Integer signups) {
		this.signups = signups;
	}

	public Integer getRejects() {
		return rejects;
	}

	public void setRejects(Integer rejects) {
		this.rejects = rejects;
	}

	public Integer getApprovals() {
		return approvals;
	}

	public void setApprovals(Integer approvals) {
		this.approvals = approvals;
	}

	public Integer getParticipantFinishedEvents() {
		return participantFinishedEvents;
	}

	public void setParticipantFinishedEvents(Integer participantFinishedEvents) {
		this.participantFinishedEvents = participantFinishedEvents;
	}

	public Integer getCreatedEvents() {
		return createdEvents;
	}

	public void setCreatedEvents(Integer createdEvents) {
		this.createdEvents = createdEvents;
	}

	public Double getPercentPaidEvents() {
		return percentPaidEvents;
	}

	public void setPercentPaidEvents(Double percentPaidEvents) {
		this.percentPaidEvents = percentPaidEvents;
	}

	public Integer getCanceledEvents() {
		return canceledEvents;
	}

	public void setCanceledEvents(Integer canceledEvents) {
		this.canceledEvents = canceledEvents;
	}

	public Double getRequestsPerMinParticipants() {
		return requestsPerMinParticipants;
	}

	public void setRequestsPerMinParticipants(Double requestsPerMinParticipants) {
		this.requestsPerMinParticipants = requestsPerMinParticipants;
	}

	public Integer getOrganiserFinishedEvents() {
		return organiserFinishedEvents;
	}

	public void setOrganiserFinishedEvents(Integer organiserFinishedEvents) {
		this.organiserFinishedEvents = organiserFinishedEvents;
	}

	public Double getAvgParticipants() {
		return avgParticipants;
	}

	public void setAvgParticipants(Double avgParticipants) {
		this.avgParticipants = avgParticipants;
	}

	public Integer getFinishedPaidEvents() {
		return finishedPaidEvents;
	}

	public void setFinishedPaidEvents(Integer finishedPaidEvents) {
		this.finishedPaidEvents = finishedPaidEvents;
	}

	public Integer getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Integer totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

}
