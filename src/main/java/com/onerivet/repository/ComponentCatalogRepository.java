package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.ComponentCatalog;
import com.onerivet.model.projection.ComponentCatalogProjection;

@Repository
public interface    ComponentCatalogRepository
        extends JpaRepository<ComponentCatalog, Integer> {

    @Query("""
            SELECT cc
            FROM ComponentCatalog cc
            WHERE cc.coverageType.coverageTypeId = :coverageTypeId
            AND cc.component.componentId = :componentId
            """)
    Optional<ComponentCatalog>
    findByCoverageTypeCoverageTypeIdAndComponentComponentId(
            Integer coverageTypeId,
            Integer componentId
    );

    @Query("""
            SELECT cc
            FROM ComponentCatalog cc
            JOIN FETCH cc.component c
            WHERE cc.coverageType.coverageTypeId = :coverageTypeId
            """)
    List<ComponentCatalog>
    findByCoverageTypeCoverageTypeId(
            Integer coverageTypeId
    );

    @Query("""
            SELECT COUNT(cc) > 0
            FROM ComponentCatalog cc
            WHERE cc.coverageType.coverageTypeId = :coverageTypeId
            AND cc.component.componentId = :componentId
            """)
    boolean existsByCoverageTypeCoverageTypeIdAndComponentComponentId(
            Integer coverageTypeId,
            Integer componentId
    );
    
    @Query("""
 	       SELECT new com.onerivet.model.projection.ComponentCatalogProjection(
 	           c.componentId,
 	           c.name
 	       )
 	       FROM ComponentCatalog cc
 	       JOIN cc.component c
 	       WHERE cc.coverageType.coverageTypeId = :coverageTypeId
 	       """)
 	List<ComponentCatalogProjection>
 	findProjectedByCoverageTypeCoverageTypeId(
 	       Integer coverageTypeId
 	);

    @Query("""
       SELECT cc
       FROM ComponentCatalog cc
       WHERE cc.component.componentId = :componentId
       """)
    Optional<ComponentCatalog>
    findFirstByComponentComponentId(
            Integer componentId
    );

    @Query("""
      SELECT cc
      FROM ComponentCatalog cc
      JOIN FETCH cc.coverageType ct
      WHERE cc.component.componentId = :componentId
      """)
    List<ComponentCatalog>
    findByComponentComponentId(
            Integer componentId
    );
}