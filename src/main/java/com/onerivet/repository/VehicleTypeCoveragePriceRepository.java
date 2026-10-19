package com.onerivet.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onerivet.model.entity.VehicleTypeCoveragePrice;

@Repository
public interface VehicleTypeCoveragePriceRepository extends JpaRepository<VehicleTypeCoveragePrice, String> {

	@Query("""
			SELECT vtcp
			FROM VehicleTypeCoveragePrice vtcp
			JOIN FETCH vtcp.coverage c
			WHERE vtcp.template.templateId = :templateId
			AND vtcp.plan.planId = :planId
			AND c.coverageId = :coverageId
			AND vtcp.deletedDate IS NULL
			""")
	Optional<VehicleTypeCoveragePrice> findByTemplateTemplateIdAndPlanPlanIdAndCoverageCoverageId(String templateId,
			Integer planId, Integer coverageId);

	@Query("""
			SELECT vtcp
			FROM VehicleTypeCoveragePrice vtcp
			JOIN FETCH vtcp.coverage c
			WHERE vtcp.template.templateId = :templateId
			AND vtcp.plan.planId = :planId
			AND vtcp.deletedDate IS NULL
			""")
	List<VehicleTypeCoveragePrice> findByTemplateTemplateIdAndPlanPlanId(String templateId, Integer planId);

	Optional<VehicleTypeCoveragePrice> findFirstByCoverageCoverageIdAndDeletedDateIsNullOrderByCreatedDateAsc(
			Integer coverageId);

	@Query("""
			SELECT vtcp
			FROM VehicleTypeCoveragePrice vtcp
			JOIN FETCH vtcp.template t
			WHERE vtcp.coverage.coverageId = :coverageId
			AND vtcp.deletedDate IS NULL
			""")
	List<VehicleTypeCoveragePrice> findActiveByCoverageId(Integer coverageId);

	@Query("""
			SELECT vtcp.basePrice
			FROM VehicleTypeCoveragePrice vtcp
			WHERE vtcp.template.templateId = :templateId
			AND vtcp.plan.planId = :planId
			AND vtcp.coverage.coverageId = :coverageId
			AND vtcp.deletedDate IS NULL
			""")
	Optional<BigDecimal> findBasePrice(@Param("templateId") String templateId, @Param("planId") Integer planId,
			@Param("coverageId") Integer coverageId);

}
