package com.onerivet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query("""
			SELECT u
			FROM User u
			JOIN FETCH u.role r
			WHERE u.userId = :userId
			AND u.deletedDate IS NULL
			""")
	Optional<User> findByUserIdAndDeletedDateIsNull(String userId);
	
	

	@Query("""
			SELECT u
			FROM User u
			JOIN FETCH u.role r
			WHERE LOWER(u.email) = LOWER(:email)
			AND u.deletedDate IS NULL
			""")
	Optional<User> findByEmail(String email);

	@Query("""
			SELECT u
			FROM User u
			JOIN FETCH u.role r
			WHERE u.userId = :userId
			AND u.deletedDate IS NULL
			""")
	Optional<User> findByUserId(String userId);
	


}