package com.onerivet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.Component;
import com.onerivet.model.projection.ComponentProjection;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {

	@Query("""
			SELECT c
			FROM Component c
			WHERE LOWER(c.name) = LOWER(:name)
			""")
	Optional<Component> findByNameIgnoreCase(String name);

	@Query("""
			SELECT new com.onerivet.model.projection.ComponentProjection(
			    c.componentId,
			    c.name
			)
			FROM Component c
			WHERE c.componentId = :componentId
			""")
	Optional<ComponentProjection> findProjectedByComponentId(Integer componentId);

	@Query("""
        SELECT COUNT(c) > 0
        FROM ComponentCatalog cc
        JOIN cc.component c
        JOIN cc.coverageType ct
        JOIN CoverageTypeCatalog ctc
            ON ctc.coverageType=ct
        JOIN ctc.coverage cov
        WHERE LOWER(c.name)=LOWER(:name)
        AND cov.isStandard=true
        """)
	boolean existsStandardComponent(
			String name
	);
}