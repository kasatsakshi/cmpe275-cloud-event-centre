package edu.sjsu.cmpe275.project.services;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.EventDao;
import edu.sjsu.cmpe275.project.dao.EventRequestDao;
import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.SystemReport;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.models.UserReport;
import edu.sjsu.cmpe275.project.types.EventStatus;
import edu.sjsu.cmpe275.project.types.RequestStatus;

@Service
public class ReportService {

	@Autowired
	EventDao eventDao;

	@Autowired
	EventRequestDao eventRequestDao;

	@Autowired
	UserDao userDao;

	DecimalFormat df = new DecimalFormat("##.##");

	public SystemReport generateSystemReport(LocalDateTime time) {

		Integer createdEvents = eventDao.countByCreationTimeBefore(time);
		Integer paidEvents = eventDao.countByCreationTimeBeforeAndFeeGreaterThan(time, 0);
		Double percentPaidEvents = Double.valueOf(df.format((double) paidEvents / createdEvents * 100));

		List<Event> canceledEvents = eventDao.findByDeadlineBeforeAndStatusEquals(time, EventStatus.CANCELLED);
		Integer participantRequests = canceledEvents.stream().mapToInt(event -> event.getSignupRequests().size()).sum();
		Integer totalMinParticipants = canceledEvents.stream().mapToInt(event -> event.getMinimumParticipants()).sum();
		Double requestsPerMinParticipants = Double
				.valueOf(df.format((double) participantRequests / totalMinParticipants * 100));

		List<Event> finishedEvents = eventDao.findByEndTimeBeforeAndStatusEquals(time, EventStatus.FINISHED);
		Integer totalParticipants = finishedEvents.stream().mapToInt(event -> event.getParticipants().size()).sum();
		Double avgParticipants = Double.valueOf(df.format((double) totalParticipants / finishedEvents.size() * 100));

		SystemReport report = new SystemReport(createdEvents, percentPaidEvents, canceledEvents.size(),
				requestsPerMinParticipants, finishedEvents.size(), avgParticipants);

		return report;
	}

	public UserReport generateUserReport(Long id, LocalDateTime time) {
		User user = userDao.findById(id).get();

		Integer signups = eventRequestDao.countByUser(user);
		Integer rejects = eventRequestDao.countByUserAndStatusEquals(user, RequestStatus.REJECTED);
		Integer approvals = eventRequestDao.countByUserAndStatusEquals(user, RequestStatus.ACCEPTED);
		Integer participantFinishedEvents = 0;

		Integer createdEvents = eventDao.countByCreator(user);
		Integer paidEvents = eventDao.countByCreatorAndFeeGreaterThan(user, 0);
		Double percentPaidEvents = Double.valueOf(df.format((double) paidEvents / createdEvents * 100));

		List<Event> canceledEvents = eventDao.findByCreatorAndStatusEquals(user, EventStatus.CANCELLED);
		Integer participantRequests = canceledEvents.stream().mapToInt(event -> event.getSignupRequests().size()).sum();
		Integer totalMinParticipants = canceledEvents.stream().mapToInt(event -> event.getMinimumParticipants()).sum();
		Double requestsPerMinParticipants = Double
				.valueOf(df.format((double) participantRequests / totalMinParticipants * 100));

		List<Event> organiserFinishedEvents = eventDao.findByCreatorAndStatusEquals(user, EventStatus.FINISHED);
		Integer totalParticipants = organiserFinishedEvents.stream().mapToInt(event -> event.getParticipants().size())
				.sum();
		Double avgParticipants = Double
				.valueOf(df.format((double) totalParticipants / organiserFinishedEvents.size() * 100));

		List<Event> finishedPaidEvents = eventDao.findByCreatorAndStatusEqualsAndFeeGreaterThan(user,
				EventStatus.FINISHED, 0);
		Integer totalRevenue = finishedPaidEvents.stream()
				.mapToInt(event -> event.getParticipants().size() * event.getFee()).sum();

		UserReport report = new UserReport(signups, rejects, approvals, participantFinishedEvents, createdEvents,
				percentPaidEvents, createdEvents, requestsPerMinParticipants, participantFinishedEvents,
				avgParticipants, paidEvents, totalRevenue);

		return report;
	}

}
