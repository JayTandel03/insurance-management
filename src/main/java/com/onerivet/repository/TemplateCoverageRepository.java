package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.TemplateCoverage;

@Repository
public interface TemplateCoverageRepository extends JpaRepository<TemplateCoverage, String> {

	@Query("""
			SELECT tc
			FROM TemplateCoverage tc
			JOIN FETCH tc.coverageTypeCatalog ctc
			WHERE tc.templatePlan.templatePlanId = :templatePlanId
			AND ctc.coverageTypeCatalogId = :coverageTypeCatalogId
			""")
	List<TemplateCoverage> findAllByTemplatePlanTemplatePlanIdAndCoverageTypeCatalogCoverageTypeCatalogId(
			String templatePlanId, Integer coverageTypeCatalogId);

	@Query("""
			SELECT tc
			FROM TemplateCoverage tc
			JOIN FETCH tc.coverageAddOnCatalog caoc
			WHERE tc.templatePlan.templatePlanId = :templatePlanId
			AND caoc.coverageAddonCatalogId = :coverageAddonCatalogId
			""")
	Optional<TemplateCoverage> findByTemplatePlanTemplatePlanIdAndCoverageAddOnCatalogCoverageAddonCatalogId(
			String templatePlanId, Integer coverageAddonCatalogId);

	@Query("""
			SELECT tc
			FROM TemplateCoverage tc
			JOIN FETCH tc.coverageTypeCatalog ctc
			LEFT JOIN FETCH tc.coverageAddOnCatalog caoc
			WHERE tc.templatePlan.templatePlanId = :templatePlanId
			""")
	List<TemplateCoverage> findByTemplatePlanTemplatePlanId(String templatePlanId);

	@Query("""
			SELECT tc
			FROM TemplateCoverage tc
			JOIN FETCH tc.coverageTypeCatalog ctc
			JOIN FETCH tc.coverageAddOnCatalog caoc
			WHERE tc.templatePlan.templatePlanId = :templatePlanId
			AND ctc.coverageTypeCatalogId = :coverageTypeCatalogId
			AND caoc.coverageAddonCatalogId = :coverageAddonCatalogId
			""")
	Optional<TemplateCoverage> findByTemplatePlanTemplatePlanIdAndCoverageTypeCatalogCoverageTypeCatalogIdAndCoverageAddOnCatalogCoverageAddonCatalogId(
			String templatePlanId, Integer coverageTypeCatalogId, Integer coverageAddonCatalogId);

	@Query("""
			SELECT tc
			FROM TemplateCoverage tc
			JOIN FETCH tc.coverageTypeCatalog ctc
			WHERE tc.templatePlan.templatePlanId = :templatePlanId
			AND ctc.coverageTypeCatalogId = :coverageTypeCatalogId
			AND tc.coverageAddOnCatalog IS NULL
			""")
	Optional<TemplateCoverage> findByTemplatePlanTemplatePlanIdAndCoverageTypeCatalogCoverageTypeCatalogIdAndCoverageAddOnCatalogIsNull(
			String templatePlanId, Integer coverageTypeCatalogId);
}