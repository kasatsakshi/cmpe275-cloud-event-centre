package edu.sjsu.cmpe275.project.models;

public class SystemReport {

	Integer createdEvents;
	Double percentPaidEvents;
	Integer canceledEvents;
	Double requestsPerMinParticipants;
	Integer finishedEvents;
	Double avgParticipants;

	public SystemReport(Integer createdEvents, Double percentPaidEvents, Integer canceledEvents,
			Double requestsPerMinParticipants, Integer finishedEvents, Double avgParticipants) {
		super();
		this.createdEvents = createdEvents;
		this.percentPaidEvents = percentPaidEvents;
		this.canceledEvents = canceledEvents;
		this.requestsPerMinParticipants = requestsPerMinParticipants;
		this.finishedEvents = finishedEvents;
		this.avgParticipants = avgParticipants;
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

	public Integer getFinishedEvents() {
		return finishedEvents;
	}

	public void setFinishedEvents(Integer finishedEvents) {
		this.finishedEvents = finishedEvents;
	}

	public Double getAvgParticipants() {
		return avgParticipants;
	}

	public void setAvgParticipants(Double avgParticipants) {
		this.avgParticipants = avgParticipants;
	}

	@Override
	public String toString() {
		return "SystemReport [createdEvents=" + createdEvents + ", percentPaidEvents=" + percentPaidEvents
				+ ", canceledEvents=" + canceledEvents + ", finishedEvents=" + finishedEvents + ", avgParticipants="
				+ avgParticipants + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
