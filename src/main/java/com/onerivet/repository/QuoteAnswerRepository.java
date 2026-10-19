package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.QuoteAnswer;
import com.onerivet.model.projection.QuoteAnswerProjection;
import com.onerivet.util.ReceiptPdfConstants;

@Repository
public interface QuoteAnswerRepository extends JpaRepository<QuoteAnswer, Integer> {

	@Query("""
			SELECT new com.onerivet.model.projection.QuoteAnswerProjection(
			    q.question.questionId,
			    q.question.question,
			    q.answerText,
			    o.optionId,
			    o.optionLabel
			)
			FROM QuoteAnswer q
			LEFT JOIN q.option o
			WHERE q.quote.quoteId = :quoteId
			  AND q.deletedDate IS NULL
			ORDER BY q.question.questionId
			""")
	List<QuoteAnswerProjection> findAnswersByQuoteId(@Param("quoteId") String quoteId);

	@Query("""
		    SELECT qa
		    FROM QuoteAnswer qa
		    WHERE qa.quote.quoteId = :quoteId
		      AND qa.deletedDate IS NULL
		""")
		List<QuoteAnswer> findActiveByQuoteId(@Param("quoteId") String quoteId);
	@Query("""
			SELECT qa.answerText
			FROM QuoteAnswer qa
			WHERE qa.quote.quoteId = :quoteId
			AND LOWER(qa.question.question) LIKE %:vin%
			""") 
	Optional<String> getVinNumber(@Param("quoteId") String quoteId, @Param(ReceiptPdfConstants.VIN) String vin);

	@Query("SELECT qa FROM QuoteAnswer qa WHERE qa.quote.quoteId = :quoteId AND qa.question.questionId = :questionId AND qa.deletedDate IS NULL")
	Optional<QuoteAnswer> findAnswerByQuoteAndQuestion(@Param("quoteId") String quoteId,
			@Param("questionId") Integer questionId);

	@Query("SELECT qa FROM QuoteAnswer qa WHERE qa.quote.quoteId = :quoteId AND qa.deletedDate IS NULL")

	List<QuoteAnswer> findAnswersByQuote(@Param("quoteId") String quoteId);

}