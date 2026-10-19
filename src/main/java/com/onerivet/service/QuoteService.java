package com.onerivet.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.AddOnResponseDto;
import com.onerivet.model.dto.QuoteAnswerResponseDto;
import com.onerivet.model.dto.QuoteCoverageSelectionResponseDto;
import com.onerivet.model.dto.QuoteCoverageTypeResponseDto;
import com.onerivet.model.dto.QuoteDetailsResponseDto;
import com.onerivet.model.dto.QuoteOptionResponseDto;
import com.onerivet.model.dto.QuoteResponseDto;
import com.onerivet.model.dto.TemplateDetailsResponseDto;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.model.mapper.QuoteMapper;
import com.onerivet.model.projection.CompletedQuoteProjection;
import com.onerivet.model.projection.InCompleteQuoteProjection;
import com.onerivet.model.projection.QuoteAnswerProjection;
import com.onerivet.model.projection.QuoteCoverageSelectionProjection;
import com.onerivet.model.projection.QuoteDetailsProjection;
import com.onerivet.repository.QuoteAnswerRepository;
import com.onerivet.repository.QuoteCoverageSelectionRepository;
import com.onerivet.repository.QuoteRepository;
import com.onerivet.util.AuthUtil;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class QuoteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuoteService.class);

	private final QuoteRepository quoteRepository;
	private final QuoteAnswerRepository quoteAnswerRepository;
	private final QuoteCoverageSelectionRepository quoteCoverageSelectionRepository;
	private final QuoteMapper quoteMapper;
	private final AuthUtil authUtil;

	public QuoteService(QuoteRepository quoteRepository, QuoteAnswerRepository quoteAnswerRepository,
			QuoteCoverageSelectionRepository quoteCoverageSelectionRepository, QuoteMapper quoteMapper,
			AuthUtil authUtil) {

		this.quoteRepository = quoteRepository;
		this.quoteAnswerRepository = quoteAnswerRepository;
		this.quoteCoverageSelectionRepository = quoteCoverageSelectionRepository;
		this.quoteMapper = quoteMapper;
		this.authUtil = authUtil;
	}

	public QuoteDetailsResponseDto getQuoteById(String quoteId) {

		LOGGER.info("Fetching quote details. quoteId={}", quoteId);

		QuoteDetailsProjection quote = quoteRepository.findQuoteDetailsByQuoteId(quoteId).orElseThrow(() -> {
			LOGGER.error("Quote not found. quoteId={}", quoteId);
			return new ResourceNotFoundException(ErrorMessageConstants.QUOTE_NOT_FOUND);
		});
		QuoteDetailsResponseDto response = new QuoteDetailsResponseDto();
		response.setQuoteId(quote.getQuoteId());
		response.setTotalPremium(quote.getTotalPremium());
		response.setVehicleTypeId(quote.getVehicleTypeId());
		response.setCarRegistrationYear(quote.getCarRegistrationYear());
		response.setCarVariantId(quote.getCarVariantId());
		response.setMotorCycleRegistrationYear(quote.getMotorCycleRegistrationYear());
		response.setMotorCycleVariantId(quote.getMotorCycleVariantId());
		response.setAnswers(getAnswers(quoteId));
		response.setVehicleTypeId(quote.getVehicleTypeId());
		response.setVehicle(quote.getVehicle());
		response.setCarRegistrationYear(quote.getCarRegistrationYear());
		response.setCarVariantId(quote.getCarVariantId());
		response.setMotorCycleRegistrationYear(quote.getMotorCycleRegistrationYear());
		response.setMotorCycleVariantId(quote.getMotorCycleVariantId());
		response.setTemplate(new TemplateDetailsResponseDto(quote.getTemplateId(), quote.getTemplateName(),
				quote.getPlanId(), quote.getPlanName()));
		response.setCoverageSelections(getCoverageSelections(quoteId));
		return response; 
	}

	private List<QuoteAnswerResponseDto> getAnswers(String quoteId) {

	    List<QuoteAnswerProjection> rows =
	            quoteAnswerRepository.findAnswersByQuoteId(quoteId);

	    Map<Integer, List<QuoteAnswerProjection>> grouped =
	            new LinkedHashMap<>();

	    for (QuoteAnswerProjection row : rows) {

	        grouped.computeIfAbsent(
	                row.getQuestionId(),
	                k -> new ArrayList<>())
	               .add(row);
	    }

	    List<QuoteAnswerResponseDto> response =
	            new ArrayList<>();

	    for (Map.Entry<Integer, List<QuoteAnswerProjection>> entry
	            : grouped.entrySet()) {

	        QuoteAnswerProjection firstRow =
	                entry.getValue().get(0);

	        List<QuoteOptionResponseDto> selectedOptions =
	                new ArrayList<>();

	        for (QuoteAnswerProjection row : entry.getValue()) {

	            if (row.getOptionId() != null) {

	                selectedOptions.add(
	                        new QuoteOptionResponseDto(
	                                row.getOptionId(),
	                                row.getOptionLabel()));
	            }
	        }

	        response.add(
	                new QuoteAnswerResponseDto(
	                        firstRow.getQuestionId(),
	                        firstRow.getQuestion(),
	                        firstRow.getAnswerText(),
	                        selectedOptions.isEmpty()
	                                ? null
	                                : selectedOptions));
	    }

	    return response;
	}
	
	private List<QuoteCoverageSelectionResponseDto> getCoverageSelections(String quoteId) {

	    List<QuoteCoverageSelectionProjection> rows =
	            quoteCoverageSelectionRepository
	                    .findCoverageSelectionsByQuoteId(quoteId);

	    Map<Integer, List<QuoteCoverageSelectionProjection>> grouped =
	            new LinkedHashMap<>();

	    for (QuoteCoverageSelectionProjection row : rows) {
	        grouped.computeIfAbsent(
	                row.getCoverageId(),
	                k -> new ArrayList<>())
	               .add(row);
	    }

	    List<QuoteCoverageSelectionResponseDto> response =
	            new ArrayList<>();

	    for (Map.Entry<Integer, List<QuoteCoverageSelectionProjection>> entry
	            : grouped.entrySet()) {

	        Integer coverageId = entry.getKey();

	        QuoteCoverageSelectionProjection firstRow =
	                entry.getValue().get(0);

	        List<QuoteCoverageTypeResponseDto> coverageTypes =
	                new ArrayList<>();

	        List<AddOnResponseDto> addOns =
	                new ArrayList<>();

	        for (QuoteCoverageSelectionProjection row
	                : entry.getValue()) {

	            if (row.getCoverageTypeId() != null) {

	                boolean exists = coverageTypes.stream()
	                        .anyMatch(type ->
	                                type.getCoverageTypeId()
	                                        .equals(row.getCoverageTypeId()));

	                if (!exists) {
	                    coverageTypes.add(
	                            new QuoteCoverageTypeResponseDto(
	                                    row.getCoverageTypeId(),
	                                    row.getCoverageType()));
	                }
	            }

	            if (row.getAddOnId() != null) {

	                boolean exists = addOns.stream()
	                        .anyMatch(addOn ->
	                                addOn.getAddOnId()
	                                        .equals(row.getAddOnId()));

	                if (!exists) {
	                    addOns.add(
	                            new AddOnResponseDto(
	                                    row.getAddOnId(),
	                                    row.getAddOn()));
	                }
	            }
	        }

	        response.add(
	                new QuoteCoverageSelectionResponseDto(
	                        coverageId,
	                        firstRow.getCoverage(),
	                        firstRow.getBasePrice(),
	                        coverageTypes,
	                        addOns.isEmpty() ? null : addOns));
	    }

	    return response;
	}
	
	public List<QuoteResponseDto> getQuotesByStatus(TemplateStatus status) {
		String email = authUtil.getLoggedInUserEmail();

		LOGGER.info("Logged in email: {}", email);

		LOGGER.info("Fetching quotes by status: {}", status);

		if (TemplateStatus.INCOMPLETE.equals(status)) {

			LOGGER.info("Fetching pending quotes from Quote table");

			List<InCompleteQuoteProjection> quotes = quoteRepository.findInCompleteQuotes(status.name(), email);

			return quoteMapper.toInCompleteQuoteResponses(quotes);
		}

		if (TemplateStatus.SUCCESSFUL.equals(status)) {

			LOGGER.info("Fetching successful payment quotes from Payment table");

			List<CompletedQuoteProjection> quotes = quoteRepository.findCompletedQuotes(status.name(), email);

			LOGGER.info("Completed quotes size: {}", quotes.size());

			return quoteMapper.toCompletedQuoteResponses(quotes);
		}
		throw new BadRequestException(ErrorMessageConstants.INVALID_QUOTE_STATUS);
	}
}