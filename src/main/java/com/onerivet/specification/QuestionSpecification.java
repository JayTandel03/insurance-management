package com.onerivet.specification;

import org.springframework.data.jpa.domain.Specification;

import com.onerivet.exception.BadRequestException;

import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.entity.Question;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;

public class QuestionSpecification {

	public static Specification<Question> searchByQuestion(String search) {

		return (root, query, criteriaBuilder) -> {

			if (search == null || search.trim().isEmpty()) {
				return null;
			} else {
				return criteriaBuilder.like(criteriaBuilder.lower(root.get("question")),
						"%" + search.toLowerCase() + "%");
			}
		};
	}

	public static Specification<Question> filterByVehicleType(Integer vehicleTypeId) {

		return (root, query, criteriaBuilder) -> {

			if (vehicleTypeId == null) {
				return null;
			}

			else {
				return criteriaBuilder.equal(root.get("vehicleType").get("vehicleTypeId"), vehicleTypeId);
			}
		};
	}


	public static Specification<Question> filterByStatus(Integer statusId) {

		return (root, query, criteriaBuilder) -> {

			if (statusId == null) {
				return null;
			}
			if (statusId != 3 && statusId != 4) {

				throw new BadRequestException(ErrorMessageConstants.INVALID_STATUS_ID);
			}
			else {
				return criteriaBuilder.equal(root.get("status").get("statusId"), statusId);
			}
		};
	}

	public static Specification<Question> filterByMandatory(Boolean mandatory) {

		return (root, query, criteriaBuilder) -> {

			if (mandatory == null) {
				return null;
			} else {
				return criteriaBuilder.equal(root.get("isMandatory"), mandatory);
			}
		};
	}

	public static Specification<Question> filterByActive(Boolean active) {

		return (root, query, criteriaBuilder) -> {

			if (active == null) {
				return null;
			} else {
				return criteriaBuilder.equal(root.get("isActive"), active);
			}
		};
	}

	public static Specification<Question> notDeleted() {

		return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedDate"));
	}

	public static Specification<Question> filterByQuestionType(Integer questionType) {
		return (root, query, criteriaBuilder) -> {
			if (questionType == null) {

				return criteriaBuilder.conjunction();
			}
			if (questionType != 1 && questionType != 2) {

				throw new BadRequestException(ErrorMessageConstants.INVALID_QUESTION_TYPE);
			}
			return criteriaBuilder.equal(root.get("questionType"), questionType);
		};
	}
}