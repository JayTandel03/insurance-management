package com.onerivet.service.helper;

import org.springframework.stereotype.Component;
import com.onerivet.repository.OptionDependencyRepository;
import com.onerivet.repository.QuoteAnswerRepository;
import com.onerivet.repository.QuoteCoverageSelectionRepository;
import com.onerivet.repository.QuoteRepository;
import com.onerivet.repository.VehicleVariantRepository;

@Component
public class QuoteWriteRepositoryHelper {

	public final QuoteRepository quoteRepository;
	public final QuoteAnswerRepository quoteAnswerRepository;
	public final QuoteCoverageSelectionRepository quoteCoverageSelectionRepository;
	public final OptionDependencyRepository optionDependencyRepository;
	public final VehicleVariantRepository vehicleVariantRepository;

	public QuoteWriteRepositoryHelper(QuoteRepository quoteRepository, QuoteAnswerRepository quoteAnswerRepository,
	                                  QuoteCoverageSelectionRepository quoteCoverageSelectionRepository,
	                                  OptionDependencyRepository optionDependencyRepository, VehicleVariantRepository vehicleVariantRepository) {
		this.quoteRepository = quoteRepository;
		this.quoteAnswerRepository = quoteAnswerRepository;
		this.quoteCoverageSelectionRepository = quoteCoverageSelectionRepository;
		this.optionDependencyRepository = optionDependencyRepository;
		this.vehicleVariantRepository = vehicleVariantRepository;
	}
}