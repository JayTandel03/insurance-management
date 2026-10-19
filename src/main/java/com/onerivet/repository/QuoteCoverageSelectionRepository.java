package com.onerivet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.onerivet.model.entity.QuoteCoverageSelection;
import com.onerivet.model.projection.AddOnPremiumProjection;
import com.onerivet.model.projection.CoveragePremiumProjection;
import com.onerivet.model.projection.QuoteCoverageSelectionProjection;
import com.onerivet.model.projection.ReceiptCoverageProjection;

public interface QuoteCoverageSelectionRepository
		extends JpaRepository<QuoteCoverageSelection, Integer> {

	@Query("""
		    SELECT new com.onerivet.model.projection.CoveragePremiumProjection(
		        c.coverageId,
		        c.name,
		        ct.name,
		        vtcp.basePrice
		    )
		    FROM QuoteCoverageSelection qcs
		    JOIN qcs.quote q
		    JOIN qcs.coverage c
		    JOIN qcs.coverageType ct
		    JOIN VehicleTypeCoveragePrice vtcp
		        ON vtcp.template.templateId = q.template.templateId
		        AND vtcp.plan.planId = q.plan.planId
		        AND vtcp.coverage.coverageId = c.coverageId
		    WHERE q.quoteId = :quoteId
		      AND qcs.deletedDate IS NULL
		      AND vtcp.deletedDate IS NULL
		""")
		List<CoveragePremiumProjection> getSelectedCoveragePremiums(@Param("quoteId") String quoteId);


	@Query("""
		    SELECT new com.onerivet.model.projection.AddOnPremiumProjection(
		        a.addOnId,
		        a.name,
		        tap.basePrice 
		    )
		    FROM QuoteCoverageSelection qcs
		    JOIN qcs.quote q
		    JOIN qcs.addOn a 
		    JOIN TemplateAddOnPrice tap
		        ON tap.template.templateId = q.template.templateId
		        AND tap.plan.planId = q.plan.planId
		        AND tap.addOn.addOnId = a.addOnId
		    WHERE q.quoteId = :quoteId
		    AND qcs.addOn IS NOT NULL
		    AND qcs.deletedDate IS NULL 
		    AND tap.deletedDate IS NULL
		    """)
	List<AddOnPremiumProjection> getSelectedAddOns(
			@Param("quoteId") String quoteId);

	@Query("""
	        SELECT new com.onerivet.model.projection.QuoteCoverageSelectionProjection(
	            c.coverageId,
	            c.name,
	            price.basePrice,
	            ct.coverageTypeId,
	            ct.name,
	            a.addOnId,
	            a.name
	        )
	        FROM QuoteCoverageSelection qcs
	        JOIN qcs.quote q
	        LEFT JOIN qcs.coverage c
	        LEFT JOIN qcs.coverageType ct
	        LEFT JOIN qcs.addOn a
	        LEFT JOIN VehicleTypeCoveragePrice price
	               ON price.template.templateId = q.template.templateId
	              AND price.plan.planId = q.plan.planId
	              AND price.coverage.coverageId = c.coverageId
	              AND price.deletedDate IS NULL
	        WHERE q.quoteId = :quoteId
	          AND qcs.deletedDate IS NULL
	        ORDER BY c.coverageId, ct.coverageTypeId, a.addOnId
	        """)
	List<QuoteCoverageSelectionProjection> findCoverageSelectionsByQuoteId(@Param("quoteId") String quoteId);

	List<QuoteCoverageSelection> findByQuote_QuoteId(String quoteId);

	@Query("""
			SELECT new com.onerivet.model.projection.ReceiptCoverageProjection(
			    c.coverageId,
			    c.name,
			    ct.coverageTypeId,
			    ct.name,
			    vtcp.basePrice
			)
			FROM QuoteCoverageSelection qcs
			JOIN qcs.quote q
			JOIN qcs.coverage c
			JOIN qcs.coverageType ct
			JOIN VehicleTypeCoveragePrice vtcp
			    ON vtcp.template.templateId = q.template.templateId
			    AND vtcp.plan.planId = q.plan.planId
			    AND vtcp.coverage.coverageId = c.coverageId
			WHERE q.quoteId = :quoteId
			AND qcs.coverage IS NOT NULL
			AND qcs.deletedDate IS NULL
			AND vtcp.deletedDate IS NULL
			""")
	List<ReceiptCoverageProjection> getReceiptSelectedCoveragePremiums(
			@Param("quoteId") String quoteId);

	@Query("""
			SELECT new com.onerivet.model.projection.AddOnPremiumProjection(
			    a.addOnId,
			    a.name,
			    tap.basePrice
			)
			FROM QuoteCoverageSelection qcs
			JOIN qcs.quote q
			JOIN qcs.addOn a
			JOIN TemplateAddOnPrice tap
			    ON tap.template.templateId = q.template.templateId
			    AND tap.plan.planId = q.plan.planId
			    AND tap.addOn.addOnId = a.addOnId
			WHERE q.quoteId = :quoteId
			AND qcs.addOn IS NOT NULL
			AND qcs.deletedDate IS NULL
			AND tap.deletedDate IS NULL
			""")
	List<AddOnPremiumProjection> getReceiptSelectedAddOns(
			@Param("quoteId") String quoteId);


	@Query("SELECT qcs FROM QuoteCoverageSelection qcs WHERE qcs.quote.quoteId = :quoteId AND qcs.deletedDate IS NULL")
	List<QuoteCoverageSelection> findCoveragesByQuote(@Param("quoteId") String quoteId);

	@Query("SELECT CASE WHEN COUNT(qcs) > 0 THEN true ELSE false END FROM QuoteCoverageSelection qcs WHERE qcs.quote.quoteId = :quoteId AND qcs.deletedDate IS NULL")
	boolean existsByQuote(@Param("quoteId") String quoteId);
}
