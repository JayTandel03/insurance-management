package com.onerivet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onerivet.model.entity.InputType;
import com.onerivet.model.projection.InputTypeProjection;
@Repository
public interface InputTypeRepository extends JpaRepository<InputType, Integer> {
	@Query("""
			SELECT i FROM InputType i
			WHERE i.inputTypeId = :id
			  AND i.deletedDate IS NULL
			""")
	Optional<InputType> findInputTypeById(@Param("id") Integer id);

	@Query("""
			SELECT new com.onerivet.model.projection.InputTypeProjection(
				it.inputTypeId,
				it.inputType
			)
			FROM InputType it
			ORDER BY it.inputTypeId
			""")
	List<InputTypeProjection> getAllInputTypes();

}