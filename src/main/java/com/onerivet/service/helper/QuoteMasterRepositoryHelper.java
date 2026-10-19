package com.onerivet.service.helper;

import org.springframework.stereotype.Component;
import com.onerivet.repository.OptionRepository;
import com.onerivet.repository.QuestionOptionCatalogRepository;
import com.onerivet.repository.QuestionRepository;
import com.onerivet.repository.VehicleAgeFactorRepository;

@Component
public class QuoteMasterRepositoryHelper {

	public final QuestionRepository questionRepository;
	public final OptionRepository optionRepository;
	public final QuestionOptionCatalogRepository questionOptionCatalogRepository;
	public final VehicleAgeFactorRepository vehicleAgeFactorRepository;

	public QuoteMasterRepositoryHelper(QuestionRepository questionRepository, OptionRepository optionRepository,
	                                   QuestionOptionCatalogRepository questionOptionCatalogRepository,
	                                   VehicleAgeFactorRepository vehicleAgeFactorRepository) {
		this.questionRepository = questionRepository;
		this.optionRepository = optionRepository;
		this.questionOptionCatalogRepository = questionOptionCatalogRepository;
		this.vehicleAgeFactorRepository = vehicleAgeFactorRepository;
	}
}