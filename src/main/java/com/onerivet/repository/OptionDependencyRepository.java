package com.onerivet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.OptionDependency;
import com.onerivet.model.projection.OptionDependencyProjection;

@Repository
public interface OptionDependencyRepository extends JpaRepository<OptionDependency, Integer> {

    @Query("""
		SELECT DISTINCT new com.onerivet.model.projection.OptionDependencyProjection(
			po.optionId,
			po.optionLabel
		)
		FROM OptionDependency od
		JOIN od.parentOption po
		WHERE od.parentQuestion.questionId = :questionId
		ORDER BY po.optionId
		""")
    List<OptionDependencyProjection> getManufacturersByQuestionId(Integer questionId);

    @Query("""
		SELECT DISTINCT new com.onerivet.model.projection.OptionDependencyProjection(
			co.optionId,
			co.optionLabel
		)
		FROM OptionDependency od 
		JOIN od.childOption co
		WHERE od.parentQuestion.questionId = :questionId
		AND od.parentOption.optionId = :manufacturerId
		ORDER BY co.optionId
		""")
    
    List<OptionDependencyProjection> getModelsByManufacturer(
            Integer questionId,
            Integer manufacturerId);
    @Query("""
 			SELECT CASE WHEN COUNT(od) > 0 THEN TRUE ELSE FALSE END
 			FROM OptionDependency od
 			WHERE od.parentQuestion.questionId = :parentQuestionId
 			  AND od.parentOption.optionId = :parentOptionId
 			  AND od.childQuestion.questionId = :childQuestionId
 			  AND od.childOption.optionId = :childOptionId
 			""")
    boolean existsByQuestionAndOptionDependency(Integer parentQuestionId, List<Integer> parentOptionId,
                                                Integer childQuestionId, List<Integer> childOptionId);
}