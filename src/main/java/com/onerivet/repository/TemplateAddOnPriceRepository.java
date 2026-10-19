package com.onerivet.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onerivet.model.entity.TemplateAddOnPrice;

@Repository
public interface TemplateAddOnPriceRepository extends JpaRepository<TemplateAddOnPrice, String> {

	@Query("""
			SELECT tap
			FROM TemplateAddOnPrice tap
			JOIN FETCH tap.template t
			JOIN FETCH tap.addOn ao
			WHERE t.templateId = :templateId
			AND tap.plan.planId = :planId
			AND ao.addOnId = :addOnId
			AND tap.deletedDate IS NULL
			""")
	Optional<TemplateAddOnPrice> findByTemplateTemplateIdAndPlanPlanIdAndAddOnAddOnId(String templateId, Integer planId,
			Integer addOnId);

	@Query("""
			SELECT tap
			FROM TemplateAddOnPrice tap
			JOIN FETCH tap.addOn ao
			WHERE tap.template.templateId = :templateId
			AND tap.plan.planId = :planId
			AND tap.deletedDate IS NULL
			""")
	List<TemplateAddOnPrice> findByTemplateTemplateIdAndPlanPlanId(String templateId, Integer planId);

	@Query("""
			SELECT tap
			FROM TemplateAddOnPrice tap
			JOIN FETCH tap.template t
			WHERE tap.addOn.addOnId = :addOnId
			AND tap.deletedDate IS NULL
			""")
	List<TemplateAddOnPrice> findActiveByAddOnId(Integer addOnId);

	@Query("""
			SELECT tap.basePrice
			FROM TemplateAddOnPrice tap
			WHERE tap.template.templateId = :templateId
			AND tap.plan.planId = :planId
			AND tap.addOn.addOnId = :addOnId
			AND tap.deletedDate IS NULL
			""")
	Optional<BigDecimal> findBasePrice(@Param("templateId") String templateId, @Param("planId") Integer planId,
			@Param("addOnId") Integer addOnId);
}