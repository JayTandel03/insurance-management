package com.onerivet.repository;

import java.util.Optional;
import java.util.List;
import com.onerivet.model.projection.CompletedQuoteProjection;
import com.onerivet.model.projection.InCompleteQuoteProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.Quote;
import com.onerivet.model.projection.PremiumBreakdownQuoteProjection;
import com.onerivet.model.projection.QuoteDetailsProjection;
import com.onerivet.model.projection.ReceiptDetailsProjection;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, String> {

    @Query("""
			SELECT new com.onerivet.model.projection.PremiumBreakdownQuoteProjection(
			    quote.quoteId,
			    quote.carRegistrationYear,
			    quote.motorCycleRegistrationYear,
			    plan.planFactor,
			    car.vehicleVariantFactor,
			    motor.vehicleVariantFactor
			)
			FROM Quote quote
			JOIN quote.plan plan
			LEFT JOIN quote.carVariantId car
			LEFT JOIN quote.motorCycleVariantId motor
			WHERE quote.quoteId = :quoteId
			AND quote.deletedDate IS NULL
			""")
    Optional<PremiumBreakdownQuoteProjection> findPremiumBreakdownQuote(@Param("quoteId") String quoteId);

    @Query("""
			SELECT new com.onerivet.model.projection.QuoteDetailsProjection(
			    q.quoteId,
			    vt.vehicleTypeId,
			    vt.vehicle,
			    q.carRegistrationYear,
			    q.motorCycleRegistrationYear,
			    cv.vehicleVariantId,
			    mv.vehicleVariantId,
			    t.templateId,
			    t.name,
			    p.planId,
			    p.name,
			    q.totalPremium
			)
			FROM Quote q
			JOIN q.vehicleType vt
			LEFT JOIN q.carVariantId cv
			LEFT JOIN q.motorCycleVariantId mv
			LEFT JOIN q.template t
			LEFT JOIN q.plan p
			WHERE q.quoteId = :quoteId
			  AND q.deletedDate IS NULL
			""")
    Optional<QuoteDetailsProjection> findQuoteDetailsByQuoteId(@Param("quoteId") String quoteId);



    @Query("""
			SELECT new com.onerivet.model.projection.InCompleteQuoteProjection(
				q.quoteId,
				cv.vehicleVariant,
				q.carRegistrationYear,
				mv.vehicleVariant,
				q.motorCycleRegistrationYear
			)
			FROM Quote q
			LEFT JOIN q.carVariantId cv
			LEFT JOIN q.motorCycleVariantId mv
			WHERE LOWER(q.status.name) = LOWER(:status)
			AND LOWER(q.user.email) = LOWER(:email)
			AND q.deletedDate IS NULL
			ORDER BY q.createdDate DESC
			""")
    List<InCompleteQuoteProjection> findInCompleteQuotes(
            @Param("status") String status,
            @Param("email") String email);
    @Query("""
			SELECT new com.onerivet.model.projection.CompletedQuoteProjection(
				q.quoteId,
				cv.vehicleVariant,
				q.carRegistrationYear,
				mv.vehicleVariant,
				q.motorCycleRegistrationYear,
				t.templateId,
				t.name,
				p.planId,
				p.name,
				pay.paymentId,
				pay.totalPremium,
				pay.transactionId
			)
			FROM Payment pay
			JOIN pay.quote q
			LEFT JOIN q.carVariantId cv
			LEFT JOIN q.motorCycleVariantId mv
			LEFT JOIN q.template t
			LEFT JOIN q.plan p
			WHERE LOWER(pay.status.name) = LOWER(:status)
			AND LOWER(q.user.email) = LOWER(:email)
			AND pay.deletedDate IS NULL
			AND q.deletedDate IS NULL
			ORDER BY pay.createdDate DESC
			""")
    List<CompletedQuoteProjection> findCompletedQuotes(
            @Param("status") String status,
            @Param("email") String email);

    @Query("""
			SELECT new com.onerivet.model.projection.ReceiptDetailsProjection(
			    q.quoteId,
			    p.createdDate,
			    p.transactionId,
			    p.paymentMethod,
			    p.totalPremium,
			    u.firstName,
			    u.lastName,
			    u.address,
			    g.name,
			    u.dateOfBirth,
			    vt.vehicleTypeId,
			    cv.vehicleVariant,
			    cv.vehicleVariantFactor,
			    q.carRegistrationYear,
			    mv.vehicleVariant,
			    mv.vehicleVariantFactor,
			    q.motorCycleRegistrationYear,
			    pl.name,
			    pl.planFactor
			)
			FROM Quote q
			LEFT JOIN q.user u
			LEFT JOIN u.gender g
			LEFT JOIN Payment p
			    ON p.quote.quoteId = q.quoteId
			LEFT JOIN p.status ps
			LEFT JOIN q.vehicleType vt
			LEFT JOIN q.carVariantId cv
			LEFT JOIN q.motorCycleVariantId mv
			LEFT JOIN q.plan pl
			WHERE q.quoteId = :quoteId
			AND ps.name = 'Successful'
			""")
    Optional<ReceiptDetailsProjection> getReceiptDetails(@Param("quoteId") String quoteId);

    @Query("""
			SELECT q
			FROM Quote q
			WHERE q.quoteId = :quoteId
			  AND q.deletedDate IS NULL
			""")
    Optional<Quote> findActiveQuoteByQuoteId(String quoteId);
    @Query("""
			SELECT CASE WHEN COUNT(q) > 0 THEN TRUE ELSE FALSE END
			FROM Quote q
			WHERE q.user.userId = :userId
			  AND q.vehicleType.vehicleTypeId = :vehicleTypeId
			  AND q.carRegistrationYear = :carRegistrationYear
			  AND q.carVariantId.vehicleVariantId = :carVariantId
			  AND q.deletedDate IS NULL
			""")
    boolean existsActiveCarQuote(String userId, Integer vehicleTypeId, Integer carRegistrationYear,
                                 Integer carVariantId);

    @Query("""
			SELECT CASE WHEN COUNT(q) > 0 THEN TRUE ELSE FALSE END
			FROM Quote q
			WHERE q.user.userId = :userId
			 AND q.vehicleType.vehicleTypeId = :vehicleTypeId
			AND q.motorCycleRegistrationYear = :motorcycleRegistrationYear
			AND q.motorCycleVariantId.vehicleVariantId = :motorcycleVariantId
			  AND q.deletedDate IS NULL
			""")
    boolean existsActiveMotorcycleQuote(String userId, Integer vehicleTypeId, Integer motorcycleRegistrationYear,
                                        Integer motorcycleVariantId);

    @Query("""
			SELECT CASE WHEN COUNT(q) > 0 THEN TRUE ELSE FALSE END
			FROM Quote q
			WHERE q.user.userId = :userId
			AND q.vehicleType.vehicleTypeId = :vehicleTypeId
			AND q.carRegistrationYear = :carRegistrationYear
			AND q.carVariantId.vehicleVariantId = :carVariantId
			AND q.motorCycleRegistrationYear = :motorcycleRegistrationYear
			AND q.motorCycleVariantId.vehicleVariantId = :motorcycleVariantId
			AND q.deletedDate IS NULL
			""")
    boolean existsActiveAllVehicleQuote(String userId, Integer vehicleTypeId, Integer carRegistrationYear,
                                        Integer carVariantId, Integer motorcycleRegistrationYear, Integer motorcycleVariantId);
}