package com.onerivet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.CoverageType;
import com.onerivet.model.projection.CoverageTypeProjection;

@Repository
public interface CoverageTypeRepository extends JpaRepository<CoverageType, Integer> {

	@Query("""
			SELECT ct
			FROM CoverageType ct
			WHERE LOWER(ct.name) = LOWER(:name)
			""")
	Optional<CoverageType> findByNameIgnoreCase(String name);

	@Query("""
			SELECT new com.onerivet.model.projection.CoverageTypeProjection(
			    ct.coverageTypeId,
			    ct.name
			)
			FROM CoverageType ct
			WHERE ct.coverageTypeId = :coverageTypeId
			""")
	Optional<CoverageTypeProjection> findProjectedByCoverageTypeId(Integer coverageTypeId);

	@Query("""
        SELECT COUNT(ct) > 0
        FROM CoverageTypeCatalog ctc
        JOIN ctc.coverageType ct
        JOIN ctc.coverage c
        WHERE LOWER(ct.name)=LOWER(:name)
        AND c.isStandard=true
        """)
	boolean existsStandardCoverageType(
			String name
	);

}