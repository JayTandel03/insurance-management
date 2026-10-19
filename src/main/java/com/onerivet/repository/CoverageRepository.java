package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.Coverage;
import com.onerivet.model.projection.CoverageComponentProjection;
import com.onerivet.model.projection.CoverageProjection;
import com.onerivet.model.projection.StandardCoverageProjection;

@Repository
public interface CoverageRepository extends JpaRepository<Coverage, Integer> {

	@Query("""
			SELECT c
			FROM Coverage c
			WHERE LOWER(c.name) = LOWER(:name)
			""")
	Optional<Coverage> findByNameIgnoreCase(String name);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.CoverageProjection(
			    c.coverageId,
			    c.name,
			    c.isStandard,
			    ct.coverageTypeId,
			    ct.name
			)
			FROM CoverageTypeCatalog ctc
			INNER JOIN ctc.coverage c
			INNER JOIN ctc.coverageType ct
			WHERE c.isStandard = true
			ORDER BY
			    c.coverageId,
			    ct.coverageTypeId
			""")
	List<CoverageProjection> findStandardCoverages();

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.CoverageComponentProjection(
			    ct.coverageTypeId,
			    comp.componentId,
			    comp.name,
			    cf.coverageFieldId,
			    cf.name
			)
			FROM ComponentCatalog cc
			INNER JOIN cc.coverageType ct
			INNER JOIN cc.component comp
			LEFT JOIN ComponentFieldCatalog cfc ON cfc.component = comp
			LEFT JOIN cfc.coverageField cf
			ORDER BY
			    ct.coverageTypeId,
			    comp.componentId,
			    cf.coverageFieldId
			""")
	List<CoverageComponentProjection> findStandardCoverageComponents();

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.CoverageProjection(
			    c.coverageId,
			    c.name,
			    c.isStandard,
			    ct.coverageTypeId,
			    ct.name
			)
			FROM Template t
			INNER JOIN TemplatePlan tp ON tp.template = t
			INNER JOIN VehicleTypeCoveragePrice vtcp ON vtcp.template = t
			INNER JOIN vtcp.coverage c
			INNER JOIN CoverageTypeCatalog ctc ON ctc.coverage = c
			INNER JOIN ctc.coverageType ct
			WHERE t.templateId = :templateId
			AND LOWER(t.status.name) = LOWER(:status)
			AND vtcp.plan = tp.plan
			ORDER BY
			    c.coverageId,
			    ct.coverageTypeId
			""")
	List<CoverageProjection> findCoverageForDraft(String templateId, String status);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.CoverageComponentProjection(
			    ct.coverageTypeId,
			    comp.componentId,
			    comp.name,
			    cf.coverageFieldId,
			    cf.name
			)
			FROM Template t
			INNER JOIN TemplatePlan tp ON tp.template = t
			INNER JOIN VehicleTypeCoveragePrice vtcp ON vtcp.template = t
			INNER JOIN vtcp.coverage c
			INNER JOIN CoverageTypeCatalog ctc ON ctc.coverage = c
			INNER JOIN ctc.coverageType ct
			INNER JOIN ComponentCatalog cc ON cc.coverageType = ct
			INNER JOIN cc.component comp
			LEFT JOIN ComponentFieldCatalog cfc ON cfc.component = comp
			LEFT JOIN cfc.coverageField cf
			WHERE t.templateId = :templateId
			AND LOWER(t.status.name) = LOWER(:status)
			AND vtcp.plan = tp.plan
			ORDER BY
			    ct.coverageTypeId,
			    comp.componentId,
			    cf.coverageFieldId
			""")
	List<CoverageComponentProjection> findCoverageComponentsForDraft(String templateId, String status);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.CoverageProjection(
			    c.coverageId,
			    c.name,
			    c.isStandard,
			    ct.coverageTypeId,
			    ct.name
			)
			FROM Template t
			INNER JOIN TemplatePlan tp ON tp.template = t
			INNER JOIN VehicleTypeCoveragePrice vtcp ON vtcp.template = t
			INNER JOIN vtcp.coverage c
			INNER JOIN CoverageTypeCatalog ctc ON ctc.coverage = c
			INNER JOIN ctc.coverageType ct
			WHERE LOWER(t.status.name) = LOWER(:status)
			AND t.vehicleType.vehicleTypeId = :vehicleTypeId
			AND vtcp.plan = tp.plan
			ORDER BY
			    c.coverageId,
			    ct.coverageTypeId
			""")
	List<CoverageProjection> findCoveragesForPublished(String templateId, Integer vehicleTypeId, String status);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.CoverageComponentProjection(
			    ct.coverageTypeId,
			    comp.componentId,
			    comp.name,
			    cf.coverageFieldId,
			    cf.name
			)
			FROM Template t
			INNER JOIN TemplatePlan tp ON tp.template = t
			INNER JOIN VehicleTypeCoveragePrice vtcp ON vtcp.template = t
			INNER JOIN vtcp.coverage c
			INNER JOIN CoverageTypeCatalog ctc ON ctc.coverage = c
			INNER JOIN ctc.coverageType ct
			INNER JOIN ComponentCatalog cc ON cc.coverageType = ct
			INNER JOIN cc.component comp
			LEFT JOIN ComponentFieldCatalog cfc ON cfc.component = comp
			LEFT JOIN cfc.coverageField cf
			WHERE LOWER(t.status.name) = LOWER(:status)
			AND t.vehicleType.vehicleTypeId = :vehicleTypeId
			AND vtcp.plan = tp.plan
			ORDER BY
			    ct.coverageTypeId,
			    comp.componentId,
			    cf.coverageFieldId
			""")
	List<CoverageComponentProjection> findCoverageComponentsForPublished(String templateId, Integer vehicleTypeId,
			String status);

	@Query("""
			SELECT new com.onerivet.model.projection.StandardCoverageProjection(
			    c.coverageId,
			    c.name,
			    c.isStandard
			)
			FROM Coverage c
			WHERE c.isStandard = true
			""")
	List<StandardCoverageProjection> findByIsStandardTrue();

	@Query("""
			SELECT DISTINCT c
			FROM VehicleTypeCoveragePrice vtcp
			INNER JOIN vtcp.coverage c
			WHERE vtcp.template.templateId = :templateId
			AND c.isStandard = false
			""")
	List<Coverage> findCustomCoveragesByTemplateId(String templateId);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.CoverageProjection(
			    c.coverageId,
			    c.name,
			    c.isStandard,
			    ct.coverageTypeId,
			    ct.name
			)
			FROM CoverageTypeCatalog ctc
			INNER JOIN ctc.coverage c
			INNER JOIN ctc.coverageType ct
			WHERE LOWER(c.status.name) = LOWER(:status)
			OR c.isStandard = true
			ORDER BY
			    c.coverageId,
			    ct.coverageTypeId
			""")
	List<CoverageProjection> findPublishedCoverages(String status);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.CoverageComponentProjection(
			    ct.coverageTypeId,
			    comp.componentId,
			    comp.name,
			    cf.coverageFieldId,
			    cf.name
			)
			FROM CoverageTypeCatalog ctc
			INNER JOIN ctc.coverage c
			INNER JOIN ctc.coverageType ct
			INNER JOIN ComponentCatalog cc
			    ON cc.coverageType = ct
			INNER JOIN cc.component comp
			LEFT JOIN ComponentFieldCatalog cfc
			    ON cfc.component = comp
			LEFT JOIN cfc.coverageField cf
			WHERE LOWER(c.status.name) = LOWER(:status)
			OR c.isStandard = true
			ORDER BY
			    ct.coverageTypeId,
			    comp.componentId,
			    cf.coverageFieldId
			""")
	List<CoverageComponentProjection> findPublishedCoverageComponents(String status);

}