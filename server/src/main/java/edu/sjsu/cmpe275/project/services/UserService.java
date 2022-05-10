package edu.sjsu.cmpe275.project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.dao.UserDao;
import edu.sjsu.cmpe275.project.models.Address;
import edu.sjsu.cmpe275.project.models.User;
import edu.sjsu.cmpe275.project.types.AccountStatus;
import edu.sjsu.cmpe275.project.types.AccountType;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

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
			Optional<String> state, Optional<String> zip) {
		User user = new User();
		setValues(user, fullName, screenName, email, password, gender, accountType, description, street, city, state,
				zip);
		User response = userDao.save(user);

		return response;
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
//		User user = findUserById(id);
		user.setStatus(AccountStatus.ACTIVE);
		User response = userDao.save(user);
		return response;
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
		user.setStatus(AccountStatus.ACTIVE);
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
