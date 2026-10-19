package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.CoverageTypeCatalog;
import com.onerivet.model.projection.CoverageTypeCatalogProjection;

@Repository
public interface CoverageTypeCatalogRepository extends JpaRepository<CoverageTypeCatalog, Integer> {

	@Query("""
			SELECT ctc
			FROM CoverageTypeCatalog ctc
			JOIN FETCH ctc.coverageType ct
			WHERE ctc.coverage.coverageId = :coverageId
			AND ct.coverageTypeId = :coverageTypeId
			""")
	Optional<CoverageTypeCatalog> findByCoverageCoverageIdAndCoverageTypeCoverageTypeId(Integer coverageId,
	                                                                                    Integer coverageTypeId);

	Optional<CoverageTypeCatalog> findFirstByCoverageCoverageId(Integer coverageId);

	@Query("""
			SELECT ctc
			FROM CoverageTypeCatalog ctc
			JOIN FETCH ctc.coverageType ct
			WHERE ctc.coverage.coverageId = :coverageId
			""")
	List<CoverageTypeCatalog> findByCoverageCoverageId(Integer coverageId);

	@Query("""
			SELECT COUNT(ctc) > 0
			FROM CoverageTypeCatalog ctc
			WHERE ctc.coverage.coverageId = :coverageId
			AND ctc.coverageType.coverageTypeId = :coverageTypeId
			""")
	boolean existsByCoverageCoverageIdAndCoverageTypeCoverageTypeId(Integer coverageId, Integer coverageTypeId);

	@Query("""
			SELECT new com.onerivet.model.projection.CoverageTypeCatalogProjection(
			    ct.coverageTypeId,
			    ct.name
			)
			FROM CoverageTypeCatalog ctc
			JOIN ctc.coverageType ct
			WHERE ctc.coverage.coverageId = :coverageId
			""")
	List<CoverageTypeCatalogProjection> findProjectedByCoverageCoverageId(Integer coverageId);

	@Query("""
       SELECT ctc
       FROM CoverageTypeCatalog ctc
       WHERE ctc.coverageType.coverageTypeId=:coverageTypeId
       """)
	Optional<CoverageTypeCatalog>
	findFirstByCoverageTypeCoverageTypeId(
			Integer coverageTypeId);

}