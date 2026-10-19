package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.ComponentFieldCatalog;
import com.onerivet.model.projection.ComponentFieldCatalogProjection;

@Repository
public interface ComponentFieldCatalogRepository
        extends JpaRepository<ComponentFieldCatalog, Integer> {

    @Query("""
            SELECT cfc
            FROM ComponentFieldCatalog cfc
            WHERE cfc.component.componentId = :componentId
            AND cfc.coverageField.coverageFieldId = :coverageFieldId
            """)
    Optional<ComponentFieldCatalog>
    findByComponentComponentIdAndCoverageFieldCoverageFieldId(
            Integer componentId,
            Integer coverageFieldId
    );

    @Query("""
            SELECT cfc
            FROM ComponentFieldCatalog cfc
            JOIN FETCH cfc.coverageField cf
            WHERE cfc.component.componentId = :componentId
            """)
    List<ComponentFieldCatalog>
    findByComponentComponentId(
            Integer componentId
    );

    @Query("""
            SELECT COUNT(cfc) > 0
            FROM ComponentFieldCatalog cfc
            WHERE cfc.component.componentId = :componentId
            AND cfc.coverageField.coverageFieldId = :coverageFieldId
            """)
    boolean existsByComponentComponentIdAndCoverageFieldCoverageFieldId(
            Integer componentId,
            Integer coverageFieldId
    );
    
    @Query("""
 	       SELECT new com.onerivet.model.projection.ComponentFieldCatalogProjection(
 	           cf.coverageFieldId,
 	           cf.name
 	       )
 	       FROM ComponentFieldCatalog cfc
 	       JOIN cfc.coverageField cf
 	       WHERE cfc.component.componentId = :componentId
 	       """)
 	List<ComponentFieldCatalogProjection>
 	findProjectedByComponentComponentId(
 	       Integer componentId
 	);

    @Query("""
       SELECT cfc
       FROM ComponentFieldCatalog cfc
       JOIN FETCH cfc.component c
       WHERE cfc.coverageField.coverageFieldId = :coverageFieldId
       """)
    List<ComponentFieldCatalog>
    findByCoverageFieldCoverageFieldId(
            Integer coverageFieldId
    );
}