package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.AddOn;
import com.onerivet.model.projection.AddOnProjection;

@Repository
public interface AddOnRepository extends JpaRepository<AddOn, Integer> {

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.AddOnProjection(
			    a.addOnId,
			    a.name
			)
			FROM CoverageAddOnCatalog cac
			INNER JOIN cac.addOn a
			INNER JOIN cac.coverage c
			WHERE c.isStandard = true
			ORDER BY a.addOnId
			""")
	List<AddOnProjection> findStandardAddOns();

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.AddOnProjection(
			    a.addOnId,
			    a.name
			)
			FROM CoverageAddOnCatalog cac
			INNER JOIN cac.addOn a
			INNER JOIN TemplateAddOnPrice tap ON tap.addOn.addOnId = a.addOnId
			INNER JOIN cac.coverage c
			WHERE c.coverageId = :coverageId
			AND tap.template.templateId = :templateId
			AND LOWER(tap.template.status.name) = LOWER(:status)
			ORDER BY a.addOnId
			""")
	List<AddOnProjection> findAddOnsForDraft(String templateId, Integer coverageId, String status);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.AddOnProjection(
			    a.addOnId,
			    a.name
			)
			FROM CoverageAddOnCatalog cac
			INNER JOIN cac.addOn a
			INNER JOIN TemplateAddOnPrice tap ON tap.addOn.addOnId = a.addOnId
			INNER JOIN cac.coverage c
			WHERE c.coverageId = :coverageId
			AND tap.template.vehicleType.vehicleTypeId = :vehicleTypeId
			AND LOWER(tap.template.status.name) = LOWER(:status)
			AND tap.plan.planId IN :planIds
			ORDER BY a.addOnId
			""")
	List<AddOnProjection> findAddOnsForPublished(Integer coverageId, Integer vehicleTypeId, List<Integer> planIds,
			String status);

	@Query("""
			SELECT a
			FROM AddOn a
			WHERE LOWER(a.name) = LOWER(:name)
			""")
	Optional<AddOn> findByNameIgnoreCase(String name);

	@Query("""
			SELECT new com.onerivet.model.projection.AddOnProjection(
			    a.addOnId,
			    a.name
			)
			FROM AddOn a
			WHERE a.addOnId = :addOnId
			""")
	Optional<AddOnProjection> findProjectedByAddOnId(Integer addOnId);

	@Query("""
			SELECT DISTINCT a
			FROM TemplateAddOnPrice tap
			INNER JOIN tap.addOn a
			WHERE tap.template.templateId = :templateId
			""")
	List<AddOn> findCustomAddOnsByTemplateId(String templateId);

	@Query("""
			SELECT DISTINCT new com.onerivet.model.projection.AddOnProjection(
			    a.addOnId,
			    a.name
			)
			FROM CoverageAddOnCatalog cac
			INNER JOIN cac.addOn a
			INNER JOIN cac.coverage c
			WHERE (
			c.coverageId = :coverageId
			AND LOWER(a.status.name) = LOWER(:status)
			)
			OR (
			c.coverageId = :coverageId
			AND c.isStandard = true
			)
			ORDER BY a.addOnId
			""")
	List<AddOnProjection> findAddOns(Integer coverageId, String status);

	@Query("""
        SELECT COUNT(a) > 0
        FROM CoverageAddOnCatalog cac
        JOIN cac.addOn a
        JOIN cac.coverage cov
        WHERE LOWER(a.name)=LOWER(:name)
        AND cov.isStandard=true
        """)
	boolean existsStandardAddOn(
			String name
	);
}
