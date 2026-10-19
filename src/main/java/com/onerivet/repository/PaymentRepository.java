package com.onerivet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onerivet.model.entity.Payment;
import com.onerivet.model.projection.PaymentProjection;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	@Query("""
		    SELECT new com.onerivet.model.projection.PaymentProjection(
		        p.transactionId,
		        p.totalPremium
		    )
		    FROM Payment p
		    WHERE p.quote.quoteId = :quoteId
		    AND p.deletedDate IS NULL
		    AND p.status.name = 'Successful'
		    """)
			Optional<PaymentProjection> findPaymentDetailsByQuoteId(
					@Param("quoteId") String quoteId);


	boolean existsByTransactionId(String transactionId);

	Optional<Payment> findByQuote_QuoteId(String quoteId);

	boolean existsByQuoteQuoteIdAndStatusNameIgnoreCase(String quoteId, String statusName);

}
