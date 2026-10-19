package com.onerivet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.Plan;
import com.onerivet.model.projection.PlanProjection;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

	@Query("""
			SELECT new com.onerivet.model.projection.PlanProjection(
			    p.planId,
			    p.name
			)
			FROM Plan p
			WHERE p.deletedDate IS NULL
			""")
	List<PlanProjection> findPlans();
}
