package com.onerivet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

	@Query("""
			SELECT s
			FROM Status s
			WHERE LOWER(s.name) = LOWER(:name)
			""")
	Optional<Status> findByNameIgnoreCase(String name);
	Optional<Status> findByName(String Name);
	
	Optional<Status> findByNameIgnoreCaseAndDeletedDateIsNull(String name);

}