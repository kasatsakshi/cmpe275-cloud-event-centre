package edu.sjsu.cmpe275.project.services;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.models.Event;
import edu.sjsu.cmpe275.project.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationService {
	@Autowired
	JavaMailSender mailSender;

	public void sendEmailNotification(User user, Event event, Map<String, String> emailContent)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "cloudeventc@gmail.com";
		String senderName = "Cloud Event Centre";
		String subject = emailContent.get("subject");
		String content = emailContent.get("body");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getFullName());
		content = content.replace("[[event]]", event.getTitle());
		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("Email has been sent");
	}
}
