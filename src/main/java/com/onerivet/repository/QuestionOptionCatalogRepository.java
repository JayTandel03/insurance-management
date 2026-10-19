package com.onerivet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onerivet.model.entity.QuestionOptionCatalog;
import com.onerivet.model.projection.OptionLabelProjection;

@Repository
public interface QuestionOptionCatalogRepository extends JpaRepository<QuestionOptionCatalog, Integer> {

	@Query("""
			SELECT q
			FROM QuestionOptionCatalog q
			WHERE q.question.questionId = :questionId
			AND q.deletedDate IS NULL
			""")
	Optional<QuestionOptionCatalog> findActiveQuestionById(Integer questionId);


	@Modifying
	@Query("""
			UPDATE QuestionOptionCatalog q
			SET q.deletedDate = CURRENT_TIMESTAMP
			WHERE q.question.questionId = :questionId
			AND q.deletedDate IS NULL
			""")
	void softDeleteByQuestionId(Integer questionId);

	boolean existsByQuestionQuestionIdAndOptionOptionIdAndDeletedDateIsNull(Integer questionId, Integer optionId);

	@Query("""
		    SELECT new com.onerivet.model.projection.OptionLabelProjection(
		        o.optionLabel
		    )
		    FROM QuestionOptionCatalog qoc
		    JOIN qoc.option o
		    WHERE qoc.question.questionId = :questionId
		    AND qoc.deletedDate IS NULL
		""")
		List<OptionLabelProjection> findOptionLabelsByQuestionId(
		        @Param("questionId") Integer questionId
		);

	Optional<QuestionOptionCatalog>
	findByQuestionQuestionIdAndOptionOptionId(
			Integer questionId,
			Integer optionId
	);

	@Modifying
	@Query("""
       DELETE FROM QuestionOptionCatalog q
       WHERE q.question.questionId = :questionId
       """)
	void deleteByQuestionId(
			@Param("questionId") Integer questionId
	);
}
 