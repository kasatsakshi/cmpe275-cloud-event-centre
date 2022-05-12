package edu.sjsu.cmpe275.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.models.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
	User findByVerificationCode(String verificationCode);
}
