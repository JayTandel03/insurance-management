package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.Question;
import com.onerivet.model.projection.QuestionProjection;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question> {

	boolean existsByQuestionIgnoreCase(String question);

	boolean existsByQuestionIgnoreCaseAndQuestionIdNot(String question, Integer questionId);

	@Query("""
			    SELECT NEW com.onerivet.model.projection.QuestionProjection(
			        q.questionId,
			        q.question,
			        q.helpText,
			        q.questionType,
			        vt.vehicleTypeId,
			        vt.vehicle,
			        it.inputTypeId,
			        it.inputType,
			        q.isMandatory,
			        q.isActive,
			        op.optionId,
			        op.optionLabel
			    )
			    FROM Question q
			    LEFT JOIN q.vehicleType vt
			    LEFT JOIN q.inputType it
			    LEFT JOIN QuestionOptionCatalog qoc
			        ON qoc.question.questionId = q.questionId
			    LEFT JOIN qoc.option op
			    WHERE q.questionId = :questionId
			    AND q.deletedDate IS NULL
			""")
	List<QuestionProjection> findQuestionDetails(@Param("questionId") Integer questionId);

	@Query("""
			SELECT
			    q.questionId AS questionId,
			    q.question AS question,
			    q.isActive AS isActive,
			    q.deletedDate AS deletedDate
			FROM Question q
			WHERE q.questionId = :questionId
			AND q.deletedDate IS NULL
			""")
	Optional<QuestionProjection> findQuestionById(@Param("questionId") Integer questionId);

	@Query("""
			SELECT q
			FROM Question q
			WHERE q.questionId = :questionId
			AND q.deletedDate IS NULL
			""")
	Optional<Question> findActiveQuestionById(Integer questionId);


	@Query("""
			    SELECT q
			    FROM Question q
			    WHERE q.isActive = true
			      AND q.isMandatory = true
			      AND q.deletedDate IS NULL
			      AND (
			            q.vehicleType.vehicleTypeId = :vehicleTypeId
			            OR q.vehicleType IS NULL
			          )
			""")
	List<Question> findActiveMandatoryQuestionsByVehicleType(@Param("vehicleTypeId") Integer vehicleTypeId);

}
