package com.onerivet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.CoverageAddOnCatalog;

@Repository
public interface CoverageAddOnCatalogRepository extends JpaRepository<CoverageAddOnCatalog, Integer> {

	@Query("""
			SELECT cac
			FROM CoverageAddOnCatalog cac
			WHERE cac.coverage.coverageId = :coverageId
			AND cac.addOn.addOnId = :addOnId
			""")
	Optional<CoverageAddOnCatalog> findByCoverageCoverageIdAndAddOnAddOnId(Integer coverageId, Integer addOnId);

	@Query("""
			SELECT COUNT(cac) > 0
			FROM CoverageAddOnCatalog cac
			WHERE cac.coverage.coverageId = :coverageId
			AND cac.addOn.addOnId = :addOnId
			""")
	boolean existsByCoverageCoverageIdAndAddOnAddOnId(Integer coverageId, Integer addOnId);

	@Query(""" 
			SELECT COUNT(cac) > 0
			FROM CoverageAddOnCatalog cac
			WHERE cac.addOn.addOnId = :addOnId
			""")
	boolean existsByAddOnAddOnIdAndCoverageIsNotNull(Integer addOnId);

	@Query(""" 
			SELECT cac
			FROM CoverageAddOnCatalog cac
			JOIN FETCH cac.addOn ao
			WHERE cac.coverage.coverageId = :coverageId
			""")
	List<CoverageAddOnCatalog> findByCoverageCoverageId(Integer coverageId);

}