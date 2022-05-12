package edu.sjsu.cmpe275.project.services;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.EventRequestDao;
import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.models.Address;
import edu.sjsu.cmpe275.project.models.EventRequest;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.types.AccountStatus;
import edu.sjsu.cmpe275.project.types.AccountType;
import edu.sjsu.cmpe275.project.types.AuthProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	@Autowired
	EventRequestDao eventRequestDao;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Create a new user
	 * 
	 * @param fullName
	 * @param screenName
	 * @param email
	 * @param password
	 * @param gender
	 * @param accountType
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	public User registerUser(String fullName, String screenName, String email, String password, Optional<String> gender,
			AccountType accountType, Optional<String> description, Optional<String> street, Optional<String> city,
			Optional<String> state, Optional<String> zip, String siteURL)
			throws UnsupportedEncodingException, MessagingException {
		User user = new User();
		setValues(user, fullName, screenName, email, password, gender, accountType, description, street, city, state,
				zip);
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		User response = userDao.save(user);
		sendVerificationEmail(user, siteURL);
		return response;
	}

	private void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "cloudeventc@gmail.com";
		String senderName = "Cloud Event Centre";
		String subject = "Please verify your email";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">Verify your email.</a></h3>" + "Thank you,<br>" + "CEC";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getFullName());
		String verifyURL = siteURL + "/api/user/verify?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("Email has been sent");
	}

	public boolean verify(String verificationCode) {
		User user = userDao.findByVerificationCode(verificationCode);

		if (user == null || user.getStatus() == AccountStatus.ACTIVE) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setStatus(AccountStatus.ACTIVE);
			userDao.save(user);

			return true;
		}

	}

	/**
	 * Update user details
	 * 
	 * @param id
	 * @param fullName
	 * @param screenName
	 * @param gender
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	public User updateUser(User user, Optional<String> fullName, Optional<String> screenName, Optional<String> gender,
			Optional<String> description, Optional<String> street, Optional<String> city, Optional<String> state,
			Optional<String> zip) {
		setValues(user, fullName, screenName, gender, description, street, city, state, zip);
		User response = userDao.save(user);

		return response;
	}

	/**
	 * Find user by email
	 * 
	 * @param email
	 * @return
	 */
	public User findUserByEmail(String email) {
		Optional<User> user = userDao.findByEmail(email);
		return user.isPresent() ? user.get() : null;
	}

	/**
	 * Find user by ID
	 * 
	 * @param id
	 * @return
	 */
	public User findUserById(Long id) {
		Optional<User> user = userDao.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	/**
	 * Activate User Account
	 * 
	 * @param id
	 * @param active
	 * @return
	 */
	public User activateAccount(User user, AccountStatus active) {
		user.setStatus(AccountStatus.ACTIVE);
		User response = userDao.save(user);
		return response;
	}

	/**
	 * Get user's sent requests
	 * 
	 * @param participant
	 * @return
	 */
	public List<EventRequest> myRequests(User participant) {
		Optional<List<EventRequest>> myRequests = eventRequestDao.findByUser(participant);
		return myRequests.isPresent() ? myRequests.get() : null;
	}

	/**
	 * Get creator's recieved requests
	 * 
	 * @param participant
	 * @return
	 */
	public List<EventRequest> requestsRecieved(User participant) {
		Optional<List<EventRequest>> requestsRecieved = eventRequestDao.findByCreator(participant);
		return requestsRecieved.isPresent() ? requestsRecieved.get() : null;
	}

	/**
	 * Add new entry
	 * 
	 * @param user
	 * @param fullName
	 * @param screenName
	 * @param email
	 * @param password
	 * @param gender
	 * @param accountType
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 */
	private void setValues(User user, String fullName, String screenName, String email, String password,
			Optional<String> gender, AccountType accountType, Optional<String> description, Optional<String> street,
			Optional<String> city, Optional<String> state, Optional<String> zip) {
		user.setFullName(fullName);
		user.setScreenName(screenName);
		user.setEmail(email);

		String encodedPassword = bCryptPasswordEncoder.encode(password);
		user.setPassword(encodedPassword);
		user.setAccountType(accountType);
		if (gender.isPresent())
			user.setGender(gender.get());
		if (description.isPresent())
			user.setDescription(description.get());
		Address address = new Address();
		if (street.isPresent())
			address.setStreet(street.get());
		if (city.isPresent())
			address.setCity(city.get());
		if (state.isPresent())
			address.setState(state.get());
		if (zip.isPresent())
			address.setZip(zip.get());
		user.setAddress(address);
		user.setStatus(AccountStatus.INACTIVE);
		user.setAuthProvider(AuthProvider.local);
	}

	/**
	 * Update existing entry
	 * 
	 * @param user
	 * @param fullName
	 * @param screenName
	 * @param gender
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 */
	private void setValues(User user, Optional<String> fullName, Optional<String> screenName, Optional<String> gender,
			Optional<String> description, Optional<String> street, Optional<String> city, Optional<String> state,
			Optional<String> zip) {
		if (fullName.isPresent())
			user.setFullName(fullName.get());
		if (screenName.isPresent())
			user.setScreenName(screenName.get());
		if (gender.isPresent())
			user.setEmail(gender.get());
		if (description.isPresent())
			user.setDescription(description.get());

		Address address = user.getAddress();
		if (address == null)
			address = new Address();
		if (street.isPresent())
			address.setStreet(street.get());
		if (city.isPresent())
			address.setCity(city.get());
		if (state.isPresent())
			address.setState(state.get());
		if (zip.isPresent())
			address.setZip(zip.get());
		user.setAddress(address);
	}
}
