package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.onerivet.model.entity.Template;
import com.onerivet.model.projection.TemplateProjection;
import com.onerivet.model.projection.TemplateStatusProjection;

@Repository
public interface TemplateRepository extends JpaRepository<Template, String> {

	Optional<Template> findByTemplateIdAndDeletedDateIsNull(String templateId);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.TemplateProjection(
			    t.templateId,
			    t.name,
			    vt.vehicleTypeId,
			    vt.vehicle,
			    p.planId,
			    p.name
			)
			FROM Template t
			LEFT JOIN t.vehicleType vt
			LEFT JOIN TemplatePlan tp ON tp.template = t
			LEFT JOIN tp.plan p
			WHERE t.templateId = :templateId
			AND t.deletedDate IS NULL
			AND (tp.deletedDate IS NULL OR tp.id IS NULL)
			AND (p.deletedDate IS NULL OR p.planId IS NULL)
			ORDER BY p.planId
			""")
	List<TemplateProjection> getTemplateBasicDetails(@Param("templateId") String templateId);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.TemplateProjection(
			    c.coverageId,
			    c.name,
			    c.isStandard,
			    vcp.basePrice
			)
			FROM Template t
			JOIN VehicleTypeCoveragePrice vcp ON vcp.template = t
			JOIN vcp.plan p
			JOIN vcp.coverage c
			WHERE t.templateId = :templateId
			AND p.planId = :planId
			AND t.deletedDate IS NULL
			AND vcp.deletedDate IS NULL
			ORDER BY c.coverageId
			""")
	List<TemplateProjection> getCoveragesByPlan(@Param("templateId") String templateId,
			@Param("planId") Integer planId);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.TemplateProjection(
			    c.coverageId,
			    c.name,
			    c.isStandard,
			    vcp.basePrice,

			    ct.coverageTypeId,
			    ct.name,

			    comp.componentId,
			    comp.name,

			    cf.coverageFieldId,
			    cf.name,
			    tcfp.basePrice,

			    ao.addOnId,
			    ao.name,
			    taop.basePrice
			)

			FROM Template t

			JOIN VehicleTypeCoveragePrice vcp
			    ON vcp.template = t

			JOIN vcp.plan p
			JOIN vcp.coverage c

			LEFT JOIN CoverageTypeCatalog ctc
			    ON ctc.coverage = c

			LEFT JOIN ctc.coverageType ct

			LEFT JOIN ComponentCatalog cc
			    ON cc.coverageType = ct

			LEFT JOIN cc.component comp

			LEFT JOIN ComponentFieldCatalog cfc
			    ON cfc.component = comp

			LEFT JOIN cfc.coverageField cf

			LEFT JOIN TemplateCoverageFieldPrice tcfp
			    ON tcfp.coverageField = cf
			    AND tcfp.template = t
			    AND tcfp.plan = p
			    AND tcfp.deletedDate IS NULL

			LEFT JOIN TemplatePlan tp
			    ON tp.template = t
			    AND tp.plan = p

			LEFT JOIN TemplateCoverage tc
			    ON tc.templatePlan = tp
			    AND tc.coverageTypeCatalog = ctc

			LEFT JOIN tc.coverageAddOnCatalog cac

			LEFT JOIN cac.addOn ao

			LEFT JOIN TemplateAddOnPrice taop
			    ON taop.template = t
			    AND taop.plan = p
			    AND taop.addOn = ao
			    AND taop.deletedDate IS NULL

			WHERE t.templateId = :templateId
			    AND p.planId = :planId
			    AND c.coverageId = :coverageId
			    AND t.deletedDate IS NULL
			    AND vcp.deletedDate IS NULL

			ORDER BY
			    ct.coverageTypeId,
			    comp.componentId,
			    cf.coverageFieldId
			""")
	List<TemplateProjection> getCoverageDetails(@Param("templateId") String templateId, @Param("planId") Integer planId,
			@Param("coverageId") Integer coverageId);

	Template getTemplateByTemplateId(String templateId);

	@Query("""
			SELECT new com.onerivet.model.projection.TemplateProjection(
			    t.templateId,
			    t.name,
			    v.vehicleTypeId,
			    v.vehicle,
			    s.statusId,
			    s.name,
			    new com.onerivet.model.projection.AuditProjection(
			        t.createdDate,
			        t.modifiedDate,
			       CONCAT(COALESCE(u.firstName, ''), ' ', COALESCE(u.lastName, ''))
			    )
			)
			FROM Template t
			LEFT JOIN t.vehicleType v
			LEFT JOIN t.status s
			LEFT JOIN t.modifiedById u
			WHERE t.deletedDate IS NULL
			""")
	List<TemplateProjection> findAllTemplates();

	@Query("""
			SELECT COUNT(t) > 0
			FROM Template t
			WHERE LOWER(t.name) = LOWER(:name)
			AND t.deletedDate IS NULL
			""")
	boolean existsByNameIgnoreCase(String name);

	@Query("""
			SELECT t
			FROM Template t
			JOIN FETCH t.status s
			JOIN FETCH t.vehicleType vt
			WHERE t.templateId = :templateId
			AND t.deletedDate IS NULL
			""")
	Optional<Template> findByTemplateId(String templateId);

	@Query("""
			SELECT t
			FROM Template t
			JOIN FETCH t.status s
			JOIN FETCH t.vehicleType vt
			WHERE LOWER(t.name) = LOWER(:templateName)
			AND t.deletedDate IS NULL
			""")
	Optional<Template> findByNameIgnoreCase(String templateName);

	@Query("""
			SELECT t.status.name AS status
			FROM Template t
			WHERE t.templateId = :templateId
			AND t.deletedDate IS NULL
			""")
	Optional<TemplateStatusProjection> findStatusByTemplateId(@Param("templateId") String templateId);

	@Modifying
	@Query("""
			UPDATE Template t
			SET t.deletedDate = CURRENT_TIMESTAMP
			WHERE t.templateId = :templateId
			AND t.deletedDate IS NULL
			""")
	void softDeleteTemplate(@Param("templateId") String templateId);

	@Modifying
	@Query("""
			UPDATE TemplatePlan tp
			SET tp.deletedDate = CURRENT_TIMESTAMP
			WHERE tp.template.templateId = :templateId
			AND tp.deletedDate IS NULL
			""")
	void softDeleteTemplatePlans(@Param("templateId") String templateId);

	@Modifying
	@Query("""
			UPDATE TemplateCoverage tc
			SET tc.deletedDate = CURRENT_TIMESTAMP
			WHERE tc.templatePlan.template.templateId = :templateId
			AND tc.deletedDate IS NULL
			""")
	void softDeleteTemplateCoverages(@Param("templateId") String templateId);

	@Modifying
	@Query("""
			UPDATE VehicleTypeCoveragePrice vtcp
			SET vtcp.deletedDate = CURRENT_TIMESTAMP
			WHERE vtcp.template.templateId = :templateId
			AND vtcp.deletedDate IS NULL
			""")
	void softDeleteVehicleTypeCoveragePrices(@Param("templateId") String templateId);

	@Modifying
	@Query("""
			UPDATE TemplateAddOnPrice taop
			SET taop.deletedDate = CURRENT_TIMESTAMP
			WHERE taop.template.templateId = :templateId
			AND taop.deletedDate IS NULL
			""")
	void softDeleteTemplateAddOnPrices(@Param("templateId") String templateId);

	@Modifying
	@Query("""
			UPDATE TemplateCoverageFieldPrice tcfp
			SET tcfp.deletedDate = CURRENT_TIMESTAMP
			WHERE tcfp.template.templateId = :templateId
			AND tcfp.deletedDate IS NULL
			""")
	void softDeleteTemplateCoverageFieldPrices(@Param("templateId") String templateId);
}