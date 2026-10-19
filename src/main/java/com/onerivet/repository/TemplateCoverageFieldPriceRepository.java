package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.TemplateCoverageFieldPrice;

@Repository
public interface TemplateCoverageFieldPriceRepository extends JpaRepository<TemplateCoverageFieldPrice, String> {

	@Query("""
			SELECT tcfp
			FROM TemplateCoverageFieldPrice tcfp
			JOIN FETCH tcfp.coverageField cf
			WHERE tcfp.template.templateId = :templateId
			AND tcfp.plan.planId = :planId
			AND cf.coverageFieldId = :coverageFieldId
			AND tcfp.deletedDate IS NULL
			""")
	Optional<TemplateCoverageFieldPrice> findByTemplateTemplateIdAndPlanPlanIdAndCoverageFieldCoverageFieldId(
			String templateId, Integer planId, Integer coverageFieldId);

	@Query("""
			SELECT tcfp
			FROM TemplateCoverageFieldPrice tcfp
			JOIN FETCH tcfp.coverageField cf
			WHERE tcfp.template.templateId = :templateId
			AND tcfp.plan.planId = :planId
			AND tcfp.deletedDate IS NULL
			""")
	List<TemplateCoverageFieldPrice> findByTemplateTemplateIdAndPlanPlanId(String templateId, Integer planId);
}