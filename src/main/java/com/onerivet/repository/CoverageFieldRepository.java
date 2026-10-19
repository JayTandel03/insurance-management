package com.onerivet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.CoverageField;
import com.onerivet.model.projection.CoverageFieldProjection;

@Repository
public interface CoverageFieldRepository extends JpaRepository<CoverageField, Integer> {

	@Query("""
			SELECT cf
			FROM CoverageField cf
			WHERE LOWER(cf.name) = LOWER(:name)
			""")
	Optional<CoverageField> findByNameIgnoreCase(String name);

	@Query("""
			SELECT new com.onerivet.model.projection.CoverageFieldProjection(
			    cf.coverageFieldId,
			    cf.name
			)
			FROM CoverageField cf
			WHERE cf.coverageFieldId = :coverageFieldId
			""")
	Optional<CoverageFieldProjection> findProjectedByCoverageFieldId(Integer coverageFieldId);

	@Query("""
        SELECT COUNT(cf) > 0
        FROM ComponentFieldCatalog cfc
        JOIN cfc.coverageField cf
        JOIN cfc.component comp
        JOIN ComponentCatalog cc
            ON cc.component=comp
        JOIN cc.coverageType ct
        JOIN CoverageTypeCatalog ctc
            ON ctc.coverageType=ct
        JOIN ctc.coverage cov
        WHERE LOWER(cf.name)=LOWER(:name)
        AND cov.isStandard=true
        """)
	boolean existsStandardField(
			String name
	);
}