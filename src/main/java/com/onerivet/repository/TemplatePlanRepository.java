package com.onerivet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onerivet.model.entity.TemplatePlan;

@Repository
public interface TemplatePlanRepository extends JpaRepository<TemplatePlan, String> {

	@Query("""
			SELECT tp
			FROM TemplatePlan tp
			JOIN FETCH tp.plan p
			WHERE tp.template.templateId = :templateId
			AND p.planId = :planId
			AND tp.deletedDate IS NULL
			""")
	Optional<TemplatePlan> findByTemplateTemplateIdAndPlanPlanId(String templateId, Integer planId);

	@Query("""
			SELECT tp
			FROM TemplatePlan tp
			JOIN FETCH tp.plan p
			WHERE tp.template.templateId = :templateId
			AND tp.deletedDate IS NULL
			""")
	List<TemplatePlan> findByTemplateTemplateId(String templateId);

	@Query("""
			SELECT tp
			FROM TemplatePlan tp
			WHERE tp.template.templateId = :templateId
			""")
	List<TemplatePlan> getPlans(String templateId);

	@Query("SELECT COUNT(tp) > 0 FROM TemplatePlan tp WHERE tp.template.templateId = :templateId AND tp.plan.planId = :planId AND tp.deletedDate IS NULL")
	boolean existsByTemplateAndPlan(@Param("templateId") String templateId, @Param("planId") Integer planId);

}