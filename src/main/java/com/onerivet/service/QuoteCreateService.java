package com.onerivet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceAlreadyExistsException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.QuoteAnswerRequestDto;
import com.onerivet.model.dto.QuoteCoverageSelectionRequestDto;
import com.onerivet.model.dto.QuoteCreateResponseDto;
import com.onerivet.model.dto.QuoteRequestDto;
import com.onerivet.model.entity.AddOn;
import com.onerivet.model.entity.Coverage;
import com.onerivet.model.entity.CoverageType;
import com.onerivet.model.entity.Option;
import com.onerivet.model.entity.Plan;
import com.onerivet.model.entity.Question;
import com.onerivet.model.entity.Quote;
import com.onerivet.model.entity.QuoteAnswer;
import com.onerivet.model.entity.QuoteCoverageSelection;
import com.onerivet.model.entity.Status;
import com.onerivet.model.entity.Template;
import com.onerivet.model.entity.User;
import com.onerivet.model.entity.VehicleType;
import com.onerivet.model.entity.VehicleVariant;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.service.helper.CoverageRepositoryHelper;
import com.onerivet.service.helper.MasterRepositoryHelper;
import com.onerivet.service.helper.QuoteMasterRepositoryHelper;
import com.onerivet.service.helper.QuoteWriteRepositoryHelper;
import com.onerivet.service.helper.TemplateRepositoryHelper;
import com.onerivet.util.AuthUtil;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class QuoteCreateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuoteCreateService.class);

	private final AuthUtil authUtil;
	private final QuoteMasterRepositoryHelper quoteMasterRepositoryHelper;
	private final QuoteWriteRepositoryHelper quoteWriteRepositoryHelper;
	private final TemplateRepositoryHelper templateRepositoryHelper;
	private final MasterRepositoryHelper masterRepositoryHelper;
	private final CoverageRepositoryHelper coverageRepositoryHelper;
	private final PremiumCalculationService premiumCalculationService;

	public QuoteCreateService(AuthUtil authUtil, QuoteMasterRepositoryHelper quoteMasterRepositoryHelper,
			QuoteWriteRepositoryHelper quoteWriteRepositoryHelper, TemplateRepositoryHelper templateRepositoryHelper,
			MasterRepositoryHelper masterRepositoryHelper, CoverageRepositoryHelper coverageRepositoryHelper,
			PremiumCalculationService premiumCalculationService) {
		this.authUtil = authUtil;
		this.quoteMasterRepositoryHelper = quoteMasterRepositoryHelper;
		this.quoteWriteRepositoryHelper = quoteWriteRepositoryHelper;
		this.templateRepositoryHelper = templateRepositoryHelper;
		this.masterRepositoryHelper = masterRepositoryHelper;
		this.coverageRepositoryHelper = coverageRepositoryHelper;
		this.premiumCalculationService = premiumCalculationService;
	}

	@Transactional
	public QuoteCreateResponseDto saveOrUpdateQuote(QuoteRequestDto quoteRequestDto) {

		boolean update = quoteRequestDto.getQuoteId() != null && !quoteRequestDto.getQuoteId().isBlank();

		User user = getLoggedInUser();

		validateQuoteGenerationFields(quoteRequestDto);
		validateBasicRules(quoteRequestDto); 

		return saveSingleVehicleQuote(quoteRequestDto, user, update);
	}

	@Transactional
	public QuoteCreateResponseDto submitQuote(String quoteId) {

	    LOGGER.info("Quote final submit started for quoteId: {}", quoteId);

	    User user = getLoggedInUser();

	    Quote quote = getQuoteById(quoteId);

	    validateQuoteNotSubmitted(quote);

	    validateQuoteReadyForSubmit(quote);

	    BigDecimal totalPremium =
	            premiumCalculationService.calculateTotalPremium(quote);

	    quote.setTotalPremium(totalPremium);

	    quote.setStatus(getStatusByName(TemplateStatus.COMPLETED));

	    quote.setModifiedById(user);
	    quote.setModifiedDate(LocalDateTime.now());

	    Quote savedQuote =
	            quoteWriteRepositoryHelper.quoteRepository.save(quote);

	    return buildQuoteResponse(
	            savedQuote,
	            true,
	            true,
	            savedQuote.getTotalPremium());
	}
	private QuoteCreateResponseDto saveSingleVehicleQuote(QuoteRequestDto quoteRequestDto, User user, boolean update) {

		Quote quote;

		if (update) {
			quote = getQuoteById(quoteRequestDto.getQuoteId());
			validateImmutableQuoteFields(quote, quoteRequestDto);
			validateQuoteNotSubmitted(quote);
			quote.setModifiedById(user);
			quote.setModifiedDate(LocalDateTime.now());
		} else {
			quote = new Quote();
			quote.setQuoteId(generateQuoteId());
			quote.setCreatedById(user);
			quote.setCreatedDate(LocalDateTime.now());
		}

		applyQuoteFields(quote, quoteRequestDto, user, false);

		quote.setStatus(getStatusByName(TemplateStatus.INCOMPLETE));
		quote.setTotalPremium(BigDecimal.ZERO);

		Quote savedQuote = quoteWriteRepositoryHelper.quoteRepository.save(quote);

		saveOrUpdateAnswers(savedQuote, quoteRequestDto, user, update);
		saveOrUpdateCoverages(savedQuote, quoteRequestDto, user, update);

		BigDecimal totalPremium = premiumCalculationService.calculateTotalPremium(savedQuote);

		savedQuote.setTotalPremium(totalPremium);
		savedQuote = quoteWriteRepositoryHelper.quoteRepository.save(savedQuote);

		return buildQuoteResponse(savedQuote, false, update, savedQuote.getTotalPremium());
	}
 
	private boolean isLandingPageQuestion(Question question) {

		String questionText = question.getQuestion() == null ? Constants.EMPTY_STRING
				: question.getQuestion().toLowerCase();

		return questionText.contains(Constants.REGISTRATION_YEAR) || questionText.contains(Constants.VEHICLE_VARIANT)
				|| questionText.contains(Constants.VEHICLE_TYPE);
	}

	private void validateMandatoryQuestionsAnswered(Quote quote) {

		List<Question> mandatoryQuestions = quoteMasterRepositoryHelper.questionRepository
				.findActiveMandatoryQuestionsByVehicleType(quote.getVehicleType().getVehicleTypeId());

		Set<Integer> answeredQuestionIds = quoteWriteRepositoryHelper.quoteAnswerRepository
				.findAnswersByQuote(quote.getQuoteId()).stream().map(answer -> answer.getQuestion().getQuestionId())
				.collect(Collectors.toSet());

		markLandingPageQuestionsAsAnswered(quote, mandatoryQuestions, answeredQuestionIds);

		List<Integer> missingQuestionIds = new ArrayList<>();

		for (Question question : mandatoryQuestions) {
			if (!answeredQuestionIds.contains(question.getQuestionId())) {
				missingQuestionIds.add(question.getQuestionId());
			}
		}

		if (!missingQuestionIds.isEmpty()) {
			throw new BadRequestException(ErrorMessageConstants.ALL_MANDATORY_QUESTIONS_REQUIRED + Constants.COLON_SPACE
					+ missingQuestionIds);
		}
	}

	private void markLandingPageQuestionsAsAnswered(Quote quote, List<Question> mandatoryQuestions,
			Set<Integer> answeredQuestionIds) {

		Integer vehicleTypeId = quote.getVehicleType().getVehicleTypeId();

		for (Question question : mandatoryQuestions) {

			String questionText = question.getQuestion() == null ? Constants.EMPTY_STRING
					: question.getQuestion().toLowerCase();

			if (Constants.VEHICLE_TYPE_CAR.equals(vehicleTypeId)) {

				if (questionText.contains(Constants.REGISTRATION_YEAR) && quote.getCarRegistrationYear() != null) {
					answeredQuestionIds.add(question.getQuestionId());
				}

				if (questionText.contains(Constants.VEHICLE_VARIANT) && quote.getCarVariantId() != null) {
					answeredQuestionIds.add(question.getQuestionId());
				}
			}

			if (Constants.VEHICLE_TYPE_MOTORBIKE.equals(vehicleTypeId)) {

				if (questionText.contains(Constants.REGISTRATION_YEAR)
						&& quote.getMotorCycleRegistrationYear() != null) {
					answeredQuestionIds.add(question.getQuestionId());
				}

				if (questionText.contains(Constants.VEHICLE_VARIANT) && quote.getMotorCycleVariantId() != null) {
					answeredQuestionIds.add(question.getQuestionId());
				}
			}

			if (Constants.VEHICLE_TYPE_ALL.equals(vehicleTypeId)) {

				if (questionText.contains("car") && questionText.contains(Constants.REGISTRATION_YEAR)
						&& quote.getCarRegistrationYear() != null) {
					answeredQuestionIds.add(question.getQuestionId());
				}

				if (questionText.contains("car") && questionText.contains(Constants.VEHICLE_VARIANT)
						&& quote.getCarVariantId() != null) {
					answeredQuestionIds.add(question.getQuestionId());
				}

				if ((questionText.contains("motorcycle") || questionText.contains("motorbike"))
						&& questionText.contains(Constants.REGISTRATION_YEAR)
						&& quote.getMotorCycleRegistrationYear() != null) {
					answeredQuestionIds.add(question.getQuestionId());
				}

				if ((questionText.contains("motorcycle") || questionText.contains("motorbike"))
						&& questionText.contains(Constants.VEHICLE_VARIANT) && quote.getMotorCycleVariantId() != null) {
					answeredQuestionIds.add(question.getQuestionId());
				}
			}
		}
	}

	private void validateQuoteReadyForSubmit(Quote quote) {

		Integer vehicleTypeId = quote.getVehicleType().getVehicleTypeId();

		if (Constants.VEHICLE_TYPE_CAR.equals(vehicleTypeId)) {

			if (quote.getCarRegistrationYear() == null || quote.getCarVariantId() == null) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_DETAILS_REQUIRED);
			}
		}

		if (Constants.VEHICLE_TYPE_MOTORBIKE.equals(vehicleTypeId)) {

			if (quote.getMotorCycleRegistrationYear() == null || quote.getMotorCycleVariantId() == null) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_DETAILS_REQUIRED);
			}
		}

		if (Constants.VEHICLE_TYPE_ALL.equals(vehicleTypeId)) {

			if (quote.getCarRegistrationYear() == null || quote.getCarVariantId() == null
					|| quote.getMotorCycleRegistrationYear() == null || quote.getMotorCycleVariantId() == null) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_DETAILS_REQUIRED);
			}
		}

		validateMandatoryQuestionsAnswered(quote);

		if (quote.getTemplate() == null) {
			throw new BadRequestException(ErrorMessageConstants.TEMPLATE_REQUIRED);
		}

		if (quote.getPlan() == null) {
			throw new BadRequestException(ErrorMessageConstants.PLAN_REQUIRED);
		}

		validateQuoteCoverageExists(quote);
	}

	private void validateQuoteCoverageExists(Quote quote) {

		boolean exists = quoteWriteRepositoryHelper.quoteCoverageSelectionRepository.existsByQuote(quote.getQuoteId());

		if (!exists) {
			throw new BadRequestException(ErrorMessageConstants.COVERAGE_REQUIRED);
		}
	}

	private void validateImmutableQuoteFields(Quote existingQuote, QuoteRequestDto requestDto) {

		if (!existingQuote.getVehicleType().getVehicleTypeId().equals(requestDto.getVehicleTypeId())) {
			throw new BadRequestException(ErrorMessageConstants.VEHICLE_TYPE_UPDATE_NOT_ALLOWED);
		}

		if (Constants.VEHICLE_TYPE_CAR.equals(requestDto.getVehicleTypeId())) {

			if (!existingQuote.getCarRegistrationYear().equals(requestDto.getCarRegistrationYear())) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_REGISTRATION_YEAR_UPDATE_NOT_ALLOWED);
			}

			if (!existingQuote.getCarVariantId().getVehicleVariantId().equals(requestDto.getCarVariantId())) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_VARIANT_UPDATE_NOT_ALLOWED);
			}
		}

		if (Constants.VEHICLE_TYPE_MOTORBIKE.equals(requestDto.getVehicleTypeId())) {

			if (!existingQuote.getMotorCycleRegistrationYear().equals(requestDto.getMotorCycleRegistrationYear())) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_REGISTRATION_YEAR_UPDATE_NOT_ALLOWED);
			}

			if (!existingQuote.getMotorCycleVariantId().getVehicleVariantId()
					.equals(requestDto.getMotorCycleVariantId())) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_VARIANT_UPDATE_NOT_ALLOWED);
			}
		}

		if (Constants.VEHICLE_TYPE_ALL.equals(requestDto.getVehicleTypeId())) {

			if (!existingQuote.getCarRegistrationYear().equals(requestDto.getCarRegistrationYear())
					|| !existingQuote.getCarVariantId().getVehicleVariantId().equals(requestDto.getCarVariantId())
					|| !existingQuote.getMotorCycleRegistrationYear().equals(requestDto.getMotorCycleRegistrationYear())
					|| !existingQuote.getMotorCycleVariantId().getVehicleVariantId()
							.equals(requestDto.getMotorCycleVariantId())) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_DETAILS_UPDATE_NOT_ALLOWED);
			}
		}
	}

	private void validateQuoteGenerationFields(QuoteRequestDto quoteRequestDto) {

		LOGGER.info("Quote Generation Validation started");

		getVehicleType(quoteRequestDto.getVehicleTypeId());

		if (Constants.VEHICLE_TYPE_CAR.equals(quoteRequestDto.getVehicleTypeId())) {
			validateCarOnly(quoteRequestDto);
			return;
		}

		if (Constants.VEHICLE_TYPE_MOTORBIKE.equals(quoteRequestDto.getVehicleTypeId())) {
			validateMotorcycleOnly(quoteRequestDto);
			return;
		}

		if (Constants.VEHICLE_TYPE_ALL.equals(quoteRequestDto.getVehicleTypeId())) {
			validateAllVehicleDetails(quoteRequestDto);
			return;
		}

		throw new BadRequestException(ErrorMessageConstants.VEHICLE_TYPE_NOT_FOUND);
	}

	private void validateCarOnly(QuoteRequestDto requestDto) {

		if (requestDto.getCarRegistrationYear() == null) {
			throw new BadRequestException(ErrorMessageConstants.CAR_REGISTRATION_YEAR_REQUIRED);
		}

		if (requestDto.getCarVariantId() == null) {
			throw new BadRequestException(ErrorMessageConstants.CAR_VARIANT_REQUIRED);
		}

		if (requestDto.getMotorCycleRegistrationYear() != null || requestDto.getMotorCycleVariantId() != null) {
			throw new BadRequestException(ErrorMessageConstants.MOTORCYCLE_DETAILS_NOT_ALLOWED_FOR_CAR);
		}

		validateVehicleYear(requestDto.getCarRegistrationYear());

		VehicleVariant carVariant = getVehicleVariant(requestDto.getCarVariantId());

		if (!Constants.VEHICLE_TYPE_CAR.equals(carVariant.getVehicleType().getVehicleTypeId())) {

			throw new BadRequestException(ErrorMessageConstants.CAR_VARIANT_NOT_VALID);
		}
	}

	private void validateMotorcycleOnly(QuoteRequestDto requestDto) {

		if (requestDto.getMotorCycleRegistrationYear() == null) {
			throw new BadRequestException(ErrorMessageConstants.MOTORCYCLE_REGISTRATION_YEAR_REQUIRED);
		}

		if (requestDto.getMotorCycleVariantId() == null) {
			throw new BadRequestException(ErrorMessageConstants.MOTORCYCLE_VARIANT_REQUIRED);
		}

		if (requestDto.getCarRegistrationYear() != null || requestDto.getCarVariantId() != null) {
			throw new BadRequestException(ErrorMessageConstants.CAR_DETAILS_NOT_ALLOWED_FOR_MOTORCYCLE);
		}

		validateVehicleYear(requestDto.getMotorCycleRegistrationYear());

		VehicleVariant motorcycleVariant = getVehicleVariant(requestDto.getMotorCycleVariantId());

		if (!Constants.VEHICLE_TYPE_MOTORBIKE.equals(motorcycleVariant.getVehicleType().getVehicleTypeId())) {

			throw new BadRequestException(ErrorMessageConstants.MOTORCYCLE_VARIANT_NOT_VALID);
		}
	}

	private void validateAllVehicleDetails(QuoteRequestDto requestDto) {

		if (requestDto.getCarRegistrationYear() == null) {
			throw new BadRequestException(ErrorMessageConstants.CAR_REGISTRATION_YEAR_REQUIRED);
		}

		if (requestDto.getCarVariantId() == null) {
			throw new BadRequestException(ErrorMessageConstants.CAR_VARIANT_REQUIRED);
		}

		if (requestDto.getMotorCycleRegistrationYear() == null) {
			throw new BadRequestException(ErrorMessageConstants.MOTORCYCLE_REGISTRATION_YEAR_REQUIRED);
		}

		if (requestDto.getMotorCycleVariantId() == null) {
			throw new BadRequestException(ErrorMessageConstants.MOTORCYCLE_VARIANT_REQUIRED);
		} 

		validateVehicleYear(requestDto.getCarRegistrationYear());
		validateVehicleYear(requestDto.getMotorCycleRegistrationYear());

		VehicleVariant carVariant = getVehicleVariant(requestDto.getCarVariantId());

		if (!Constants.VEHICLE_TYPE_CAR.equals(carVariant.getVehicleType().getVehicleTypeId())) {

			throw new BadRequestException(ErrorMessageConstants.CAR_VARIANT_NOT_VALID);
		}

		VehicleVariant motorcycleVariant = getVehicleVariant(requestDto.getMotorCycleVariantId());

		if (!Constants.VEHICLE_TYPE_MOTORBIKE.equals(motorcycleVariant.getVehicleType().getVehicleTypeId())) {

			throw new BadRequestException(ErrorMessageConstants.MOTORCYCLE_VARIANT_NOT_VALID);
		}
	}

	private void validateTemplatePlanRequiredForCoverage(QuoteRequestDto quoteRequestDto) {

		if (!hasCoverages(quoteRequestDto)) {
			return;
		}

		if (quoteRequestDto.getTemplateId() == null || quoteRequestDto.getTemplateId().isBlank()
				|| quoteRequestDto.getPlanId() == null) {

			throw new BadRequestException(ErrorMessageConstants.TEMPLATE_PLAN_REQUIRED_FOR_COVERAGE);
		}
	}

	private void validateBasicRules(QuoteRequestDto quoterequestDto) {

		LOGGER.info("Basic Quote Validation started");
		getVehicleType(quoterequestDto.getVehicleTypeId());

		if (quoterequestDto.getTemplateId() != null && !quoterequestDto.getTemplateId().isBlank()) {
			getPublishedTemplate(quoterequestDto.getTemplateId(), quoterequestDto.getVehicleTypeId());
		}

		if (quoterequestDto.getPlanId() != null) {
			getPlan(quoterequestDto.getPlanId());
		}

		if (quoterequestDto.getTemplateId() != null && quoterequestDto.getPlanId() != null) {

			validatePlanMappedWithTemplate(quoterequestDto.getTemplateId(), quoterequestDto.getPlanId());
		} 

		validateAnswers(quoterequestDto);
		validateTemplatePlanRequiredForCoverage(quoterequestDto); 

		if (hasCoverages(quoterequestDto)) {
			validateCoverageSelections(quoterequestDto);
		}
		LOGGER.info("Basic Quote Validation completed"); 
	}

	private void validateDuplicateAnswers(List<QuoteAnswerRequestDto> answers) {

		if (answers == null || answers.isEmpty()) {
			return;
		} 

		Set<Integer> questionIds = new HashSet<>();

		for (QuoteAnswerRequestDto answer : answers) {

			if (!questionIds.add(answer.getQuestionId())) {
				throw new BadRequestException(
						ErrorMessageConstants.DUPLICATE_QUESTION + Constants.COLON_SPACE + answer.getQuestionId());
			}
		}
	}

	private void validateAnswers(QuoteRequestDto quoterequestDto) {

		validateDuplicateAnswers(quoterequestDto.getAnswers());
		
		if (quoterequestDto.getAnswers() == null) {
			return;
		}

		for (QuoteAnswerRequestDto answerDto : quoterequestDto.getAnswers()) {

			Question question = getQuestion(answerDto.getQuestionId());

			validateQuestionIsActive(question);

			if (isLandingPageQuestion(question)) {
				throw new BadRequestException(ErrorMessageConstants.LANDING_PAGE_QUESTION + answerDto.getQuestionId());
			}

			validateQuestionBelongsToVehicleType(question, quoterequestDto.getVehicleTypeId());

			validateAnswerRequired(question, answerDto);

			List<Option> options = getSelectedOptions(answerDto);

			validateSingleSelectionQuestion(question, answerDto);

			validateOtherOptions(options, answerDto);

			validateQuestionValue(question, answerDto, null);
			
			validateDatePickerAnswer(question, answerDto);
		}
	}

	private void validateSingleSelectionQuestion(Question question, QuoteAnswerRequestDto answerDto) {

		Integer inputTypeId = question.getInputType().getInputTypeId();

		if (Constants.INPUT_TYPE_DROPDOWN.equals(inputTypeId)
				|| Constants.INPUT_TYPE_RADIO_BUTTON.equals(inputTypeId)) {

			if (answerDto.getOptionId() != null && answerDto.getOptionId().size() > 1) {

				throw new BadRequestException(
						"Only one option is allowed" + Constants.QUESTION_ID_LABEL + answerDto.getQuestionId());
			}
		}
	}
	
	private void validateDatePickerAnswer(
	        Question question,
	        QuoteAnswerRequestDto answerDto) {
	 
	    if (!Constants.DATE_PICKER_INPUT_TYPE_ID.equals(
	            question.getInputType().getInputTypeId())) {
	        return;
	    }
	 
	    String answerText = answerDto.getAnswerText();
	 
	    if (answerText == null || answerText.trim().isEmpty()) {
	        throw new BadRequestException(
	        		ErrorMessageConstants.DATE_REQUIRED);
	    }
	 
	    try {
	 
	        DateTimeFormatter formatter = DateTimeFormatter
	                .ofPattern("uuuu-MM-dd")
	                .withResolverStyle(ResolverStyle.STRICT);
	 
	        LocalDate.parse(answerText, formatter);
	 
	    } catch (DateTimeParseException ex) {
	 
	        if (answerText.matches("\\d{4}-02-29")) {
	            throw new BadRequestException(
	            		ErrorMessageConstants.DATE_INVALID_LEAP_YEAR);
	        }
	 
	        throw new BadRequestException(
	        		ErrorMessageConstants.DATE_INVALID_FORMAT);
	    }
	}

	private void validateQuestionOptionDependency(List<QuoteAnswerRequestDto> answers) {

		validateDependencyByQuestionIds(answers, Constants.CAR_MANUFACTURER_QUESTION_ID,
				Constants.CAR_MODEL_QUESTION_ID);

		validateDependencyByQuestionIds(answers, Constants.BIKE_MANUFACTURER_QUESTION_ID,
				Constants.BIKE_MODEL_QUESTION_ID);
	}

	private void validateDependencyByQuestionIds(List<QuoteAnswerRequestDto> answers, Integer parentQuestionId,
			Integer childQuestionId) {

		QuoteAnswerRequestDto parentAnswer = findAnswerByQuestionId(answers, parentQuestionId);

		QuoteAnswerRequestDto childAnswer = findAnswerByQuestionId(answers, childQuestionId);

		if (parentAnswer == null || childAnswer == null) {
			return;
		}

		if (parentAnswer.getOptionId() == null || childAnswer.getOptionId() == null) {
			return;
		}

		boolean exists = quoteWriteRepositoryHelper.optionDependencyRepository.existsByQuestionAndOptionDependency(
				parentQuestionId, parentAnswer.getOptionId(), childQuestionId, childAnswer.getOptionId());

		if (!exists) {
			throw new BadRequestException(ErrorMessageConstants.MODEL_NOT_VALID_FOR_MANUFACTURER);
		}
	}

	private QuoteAnswerRequestDto findAnswerByQuestionId(List<QuoteAnswerRequestDto> answers, Integer questionId) {

		return answers.stream().filter(answer -> questionId.equals(answer.getQuestionId())).findFirst().orElse(null);
	}

	private void validateQuestionBelongsToVehicleType(Question question, Integer vehicleTypeId) {

		if (question.getVehicleType() == null) {
			return;
		}

		if (!question.getVehicleType().getVehicleTypeId().equals(vehicleTypeId)) {
			throw new BadRequestException(ErrorMessageConstants.QUESTION_NOT_VALID_FOR_VEHICLE_TYPE);
		}
	}

	private void validateQuestionIsActive(Question question) {

		if (!Boolean.TRUE.equals(question.getIsActive()) || question.getDeletedDate() != null) {

			throw new BadRequestException(ErrorMessageConstants.INACTIVE_QUESTION_NOT_ALLOWED
					+ Constants.QUESTION_ID_LABEL + question.getQuestionId());
		}
	}

	private List<Option> getSelectedOptions(QuoteAnswerRequestDto answerDto) {

		if (answerDto.getOptionId() == null || answerDto.getOptionId().isEmpty()) {
			return List.of();
		}

		List<Option> options = new ArrayList<>();

		Set<Integer> uniqueOptionIds = new HashSet<>();

		for (Integer optionId : answerDto.getOptionId()) {

			if (!uniqueOptionIds.add(optionId)) {
				throw new BadRequestException(
						ErrorMessageConstants.DUPLICATE_OPTION + Constants.OPTION_ID_LABEL + optionId);
			}

			Option option = getOption(optionId);

			validateOptionMappedWithQuestion(answerDto.getQuestionId(), optionId);

			options.add(option);
		}

		return options;
	}

	private void validateAnswerRequired(Question question, QuoteAnswerRequestDto answerDto) {

		Integer inputTypeId = question.getInputType().getInputTypeId();

		boolean hasOption = answerDto.getOptionId() != null && !answerDto.getOptionId().isEmpty();

		boolean hasText = answerDto.getAnswerText() != null && !answerDto.getAnswerText().isBlank();

		if (Constants.INPUT_TYPE_DROPDOWN.equals(inputTypeId) || Constants.INPUT_TYPE_RADIO_BUTTON.equals(inputTypeId)
				|| Constants.INPUT_TYPE_CHECKBOX.equals(inputTypeId)) {

			if (!hasOption) {
				throw new BadRequestException(ErrorMessageConstants.OPTION_REQUIRED + Constants.QUESTION_ID_LABEL
						+ answerDto.getQuestionId());
			}

			return;
		}

		if (Constants.INPUT_TYPE_TEXT_FIELD.equals(inputTypeId)
				|| Constants.INPUT_TYPE_NUMERIC_FIELD.equals(inputTypeId)
				|| Constants.INPUT_TYPE_TEXT_AREA.equals(inputTypeId)) {

			if (!hasText) {
				throw new BadRequestException(ErrorMessageConstants.ANSWER_TEXT_REQUIRED + Constants.QUESTION_ID_LABEL
						+ answerDto.getQuestionId());
			}
		}
	}

	private void validateOtherOption(Option option, QuoteAnswerRequestDto quoteanswerDto) {
		if (option == null || !isOtherOption(option)) {
			return;
		}

		String answerText = quoteanswerDto.getAnswerText();

		if (answerText == null || answerText.isBlank()) {
			throw new BadRequestException(ErrorMessageConstants.OTHER_TEXT_REQUIRED + Constants.QUESTION_ID_LABEL
					+ quoteanswerDto.getQuestionId());
		}

		if (answerText.length() < Constants.OTHER_TEXT_MIN_LENGTH
				|| answerText.length() > Constants.OTHER_TEXT_MAX_LENGTH) {
			throw new BadRequestException(ErrorMessageConstants.OTHER_TEXT_INVALID + Constants.QUESTION_ID_LABEL
					+ quoteanswerDto.getQuestionId());
		}

		if (!answerText.matches(Constants.OTHER_TEXT_REGEX)) {
			throw new BadRequestException(ErrorMessageConstants.OTHER_TEXT_SPECIAL_CHARACTER
					+ Constants.QUESTION_ID_LABEL + quoteanswerDto.getQuestionId());
		}
	}

	private void validateOtherOptions(List<Option> options, QuoteAnswerRequestDto answerDto) {

		for (Option option : options) {
			validateOtherOption(option, answerDto);
		}
	}

	private void validateQuestionValue(Question question, QuoteAnswerRequestDto quoteanswerDto, Option option) {

		String answerText = quoteanswerDto.getAnswerText();

		if (answerText == null || answerText.isBlank()) {
			return;
		}

		validateAnswerTextByInputType(question, answerText, quoteanswerDto.getQuestionId());

		if (option != null && isOtherOption(option)) {
			validateRegex(answerText, Constants.OTHER_TEXT_REGEX, ErrorMessageConstants.OTHER_VALUE_INVALID,
					quoteanswerDto.getQuestionId());
			return;
		}

		String questionText = question.getQuestion() == null ? Constants.EMPTY_STRING
				: question.getQuestion().toLowerCase();

		String validationType = getValidationType(questionText);

		switch (validationType) {

		case Constants.VIN:
			validateRegex(answerText, Constants.VIN_REGEX, ErrorMessageConstants.INVALID_VIN,
					quoteanswerDto.getQuestionId());
			break;

		case Constants.FIRST_NAME:
		case Constants.LAST_NAME:
			validateRegex(answerText, Constants.NAME_REGEX, ErrorMessageConstants.INVALID_NAME,
					quoteanswerDto.getQuestionId());
			break;

		case Constants.EMAIL:
			validateRegex(answerText, Constants.EMAIL_REGEX, ErrorMessageConstants.INVALID_EMAIL,
					quoteanswerDto.getQuestionId());
			break;

		case Constants.PHONE:
			validateRegex(answerText, Constants.PHONE_REGEX, ErrorMessageConstants.INVALID_PHONE,
					quoteanswerDto.getQuestionId());
			break;

		case Constants.DOB:
			validateRegex(answerText, Constants.DOB_REGEX, ErrorMessageConstants.INVALID_DOB,
					quoteanswerDto.getQuestionId());
			validateDob(answerText, quoteanswerDto.getQuestionId());
			break;

		default:
			LOGGER.debug("No regex validation required for questionId: {}", quoteanswerDto.getQuestionId());
		}
	}

	private void validateAnswerTextByInputType(Question question, String answerText, Integer questionId) {

		Integer inputTypeId = question.getInputType().getInputTypeId();

		if (Constants.INPUT_TYPE_TEXT_FIELD.equals(inputTypeId)) {

			if (answerText.length() < Constants.TEXT_FIELD_MIN_LENGTH
					|| answerText.length() > Constants.TEXT_FIELD_MAX_LENGTH) {

				throw new BadRequestException(
						ErrorMessageConstants.INVALID_TEXT_FIELD_LENGTH + Constants.QUESTION_ID_LABEL + questionId);
			}
		}

		if (Constants.INPUT_TYPE_NUMERIC_FIELD.equals(inputTypeId)) {

			if (!answerText.matches(Constants.NUMERIC_FIELD_REGEX)) {

				throw new BadRequestException(
						ErrorMessageConstants.INVALID_NUMERIC_FIELD + Constants.QUESTION_ID_LABEL + questionId);
			}
		}

		if (Constants.INPUT_TYPE_TEXT_AREA.equals(inputTypeId)) {

			if (answerText.length() < Constants.TEXT_AREA_MIN_LENGTH
					|| answerText.length() > Constants.TEXT_AREA_MAX_LENGTH) {

				throw new BadRequestException(
						ErrorMessageConstants.INVALID_TEXT_AREA_LENGTH + Constants.QUESTION_ID_LABEL + questionId);
			}
		}

	}

	private String getValidationType(String questionText) {

		if (questionText.contains(Constants.VIN)) {
			return Constants.VIN;
		}

		if (questionText.contains(Constants.FIRST_NAME)) {
			return Constants.FIRST_NAME;
		}

		if (questionText.contains(Constants.LAST_NAME)) {
			return Constants.LAST_NAME;
		}

		if (questionText.contains(Constants.EMAIL)) {
			return Constants.EMAIL;
		}

		if (questionText.contains(Constants.PHONE)) {
			return Constants.PHONE;
		}

		if (questionText.contains(Constants.DOB) || questionText.contains(Constants.DATE_OF_BIRTH)) {
			return Constants.DOB;
		}

		return Constants.EMPTY_STRING;
	}

	private void validateRegex(String value, String regex, String message, Integer questionId) {

		if (!value.matches(regex)) {
			throw new BadRequestException(message + Constants.QUESTION_ID_LABEL + questionId);
		}
	}

	private void validateDob(String dob, Integer questionId) {

		try {

			LocalDate dateOfBirth = LocalDate.parse(dob,
					DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.STRICT));

			if (dateOfBirth.isAfter(LocalDate.now())) {
				throw new BadRequestException(
						ErrorMessageConstants.DOB_FUTURE_DATE + Constants.QUESTION_ID_LABEL + questionId);
			}

			int age = Period.between(dateOfBirth, LocalDate.now()).getYears();

			if (age < 18) {
				throw new BadRequestException(
						ErrorMessageConstants.DOB_AGE_LESS_THAN_18 + Constants.QUESTION_ID_LABEL + questionId);
			}

		} catch (DateTimeParseException ex) {

			throw new BadRequestException(
					ErrorMessageConstants.INVALID_LEAP_YEAR_DOB + Constants.QUESTION_ID_LABEL + questionId);
		}
	}

	private void validateOptionMappedWithQuestion(Integer questionId, Integer optionId) {

		boolean exists = quoteMasterRepositoryHelper.questionOptionCatalogRepository
				.existsByQuestionQuestionIdAndOptionOptionIdAndDeletedDateIsNull(questionId, optionId);

		if (!exists) {
			throw new BadRequestException(ErrorMessageConstants.OPTION_NOT_VALID_FOR_QUESTION
					+ Constants.QUESTION_ID_LABEL + questionId + Constants.OPTION_ID_LABEL + optionId);
		}
	}

	private void validateCoverageSelections(QuoteRequestDto quoterequestDto) {

		validateDuplicateCoverageSelections(quoterequestDto.getCoverageSelections());
		validateCoverageCountByPlan(quoterequestDto);

		for (QuoteCoverageSelectionRequestDto selectionDto : quoterequestDto.getCoverageSelections()) {

			getCoverage(selectionDto.getCoverageId());
			validateCoveragePriceExists(quoterequestDto, selectionDto.getCoverageId());
			validateCoverageTypeIds(selectionDto);
			validateAddOns(quoterequestDto.getVehicleTypeId(), selectionDto.getCoverageId(),
					selectionDto.getAddOnIds());
		}
	}

	private void validateCoverageCountByPlan(QuoteRequestDto requestDto) {

		if (requestDto.getPlanId() == null || requestDto.getCoverageSelections() == null) {
			return;
		}

		long standardCoverageCount = requestDto.getCoverageSelections().stream()
				.map(selection -> getCoverage(selection.getCoverageId()))
				.filter(coverage -> Boolean.TRUE.equals(coverage.getIsStandard())).count();

		if (Constants.BASIC_PLAN_ID.equals(requestDto.getPlanId()) && standardCoverageCount != 1) {
			throw new BadRequestException(ErrorMessageConstants.INVALID_BASIC_PLAN_COVERAGE);
		}

		if (Constants.STANDARD_PLAN_ID.equals(requestDto.getPlanId()) && standardCoverageCount != 2) {
			throw new BadRequestException(ErrorMessageConstants.INVALID_STANDARD_PLAN_COVERAGE);
		}

		if (Constants.ADVANCED_PLAN_ID.equals(requestDto.getPlanId()) && standardCoverageCount != 3) {
			throw new BadRequestException(ErrorMessageConstants.INVALID_ADVANCED_PLAN_COVERAGE);
		}
	}

	private void validateDuplicateCoverageSelections(List<QuoteCoverageSelectionRequestDto> selections) {
		if (selections == null || selections.isEmpty()) {
			return;
		}

		Set<Integer> coverageIds = new HashSet<>();

		for (QuoteCoverageSelectionRequestDto selection : selections) {
			if (!coverageIds.add(selection.getCoverageId())) {
				throw new BadRequestException(
						ErrorMessageConstants.DUPLICATE_COVERAGE + Constants.COLON_SPACE + selection.getCoverageId());
			}
		}
	}

	private void validateCoveragePriceExists(QuoteRequestDto requestDto, Integer coverageId) {

		if (requestDto.getTemplateId() == null || requestDto.getTemplateId().isBlank()
				|| requestDto.getPlanId() == null) {
			return;
		}

		boolean exists = coverageRepositoryHelper.vehicleTypeCoveragePriceRepository
				.findBasePrice(requestDto.getTemplateId(), requestDto.getPlanId(), coverageId).isPresent();

		if (!exists) {
			throw new BadRequestException(
					ErrorMessageConstants.COVERAGE_PRICE_NOT_FOUND + Constants.COVERAGE_ID_LABEL + coverageId);
		}
	}

	private void validateCoverageTypeIds(QuoteCoverageSelectionRequestDto quotecoverageselectionDto) {

		if (quotecoverageselectionDto.getCoverageTypeIds() == null
				|| quotecoverageselectionDto.getCoverageTypeIds().isEmpty()) {
			throw new BadRequestException(ErrorMessageConstants.COVERAGE_TYPE_REQUIRED);
		}
		validateDuplicateCoverageTypes(quotecoverageselectionDto.getCoverageTypeIds());

		for (Integer coverageTypeId : quotecoverageselectionDto.getCoverageTypeIds()) {

			getCoverageType(coverageTypeId);

			boolean mapped = coverageRepositoryHelper.coverageTypeCatalogRepository
					.existsByCoverageCoverageIdAndCoverageTypeCoverageTypeId(quotecoverageselectionDto.getCoverageId(),
							coverageTypeId);

			if (!mapped) {
				throw new BadRequestException(ErrorMessageConstants.COVERAGE_TYPE_NOT_MAPPED);
			}
		}
	}

	private void validateDuplicateCoverageTypes(List<Integer> coverageTypeIds) {

		Set<Integer> ids = new HashSet<>();

		for (Integer coverageTypeId : coverageTypeIds) {

			if (!ids.add(coverageTypeId)) {
				throw new BadRequestException(
						ErrorMessageConstants.DUPLICATE_COVERAGE_TYPE + Constants.COLON_SPACE + coverageTypeId);
			}
		}
	}

	private void validateAddOns(Integer vehicleTypeId, Integer coverageId, List<Integer> addOnIds) {

		if (addOnIds == null || addOnIds.isEmpty()) {
			return;
		}

		validateDuplicateAddOns(addOnIds);
		for (Integer addOnId : addOnIds) {

			getAddOn(addOnId);

			boolean mapped = coverageRepositoryHelper.coverageAddOnCatalogRepository
					.existsByCoverageCoverageIdAndAddOnAddOnId(coverageId, addOnId);

			if (!mapped) {
				throw new BadRequestException(ErrorMessageConstants.ADD_ON_NOT_AVAILABLE);
			}

			validateMotorbikeAddOn(vehicleTypeId, addOnId);
		}
	}

	private void validateDuplicateAddOns(List<Integer> addOnIds) {

		if (addOnIds == null || addOnIds.isEmpty()) {
			return;
		}

		Set<Integer> ids = new HashSet<>();

		for (Integer addOnId : addOnIds) {

			if (!ids.add(addOnId)) {
				throw new BadRequestException(ErrorMessageConstants.DUPLICATE_ADD_ON + Constants.COLON_SPACE + addOnId);
			}
		}
	}

	private void validateMotorbikeAddOn(Integer vehicleTypeId, Integer addOnId) {

		if (Constants.VEHICLE_TYPE_MOTORBIKE.equals(vehicleTypeId)
				&& !Constants.ROADSIDE_ASSISTANCE_ADD_ON_ID.equals(addOnId)) {
			throw new BadRequestException(ErrorMessageConstants.ADD_ON_NOT_ALLOWED_FOR_MOTORBIKE);
		}
	}

	private void validatePlanMappedWithTemplate(String templateId, Integer planId) {

		boolean exists = templateRepositoryHelper.templatePlanRepository.existsByTemplateAndPlan(templateId, planId);

		if (!exists) {

			throw new BadRequestException(ErrorMessageConstants.PLAN_NOT_VALID_FOR_TEMPLATE);
		}
	}

	private Template getPublishedTemplate(String templateId, Integer vehicleTypeId) {

		Template template = templateRepositoryHelper.templateRepository.findByTemplateIdAndDeletedDateIsNull(templateId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.TEMPLATE_NOT_FOUND));

		if (template.getStatus() == null
				|| !TemplateStatus.PUBLISHED.name().equalsIgnoreCase(template.getStatus().getName())) {
			throw new BadRequestException(ErrorMessageConstants.TEMPLATE_NOT_PUBLISHED);
		}

		if (template.getVehicleType() == null || !template.getVehicleType().getVehicleTypeId().equals(vehicleTypeId)) {
			throw new BadRequestException(ErrorMessageConstants.TEMPLATE_NOT_VALID_FOR_VEHICLE_TYPE);
		}

		return template;
	}

	private void applyQuoteFields(Quote quote, QuoteRequestDto requestDto, User user, boolean completed) {

		quote.setUser(user);
		quote.setVehicleType(getVehicleType(requestDto.getVehicleTypeId()));

		setVehicleFields(quote, requestDto);

		if (requestDto.getTemplateId() != null && !requestDto.getTemplateId().isBlank()) {
			quote.setTemplate(getPublishedTemplate(requestDto.getTemplateId(), requestDto.getVehicleTypeId()));
		}

		if (requestDto.getPlanId() != null) {
			quote.setPlan(getPlan(requestDto.getPlanId()));
		}

		quote.setStatus(
				completed ? getStatusByName(TemplateStatus.COMPLETED) : getStatusByName(TemplateStatus.INCOMPLETE));
	}

	private void setVehicleFields(Quote quote, QuoteRequestDto requestDto) {

		quote.setCarRegistrationYear(null);
		quote.setCarVariantId(null);
		quote.setMotorCycleRegistrationYear(null);
		quote.setMotorCycleVariantId(null);

		if (Constants.VEHICLE_TYPE_CAR.equals(requestDto.getVehicleTypeId())) {

			quote.setCarRegistrationYear(requestDto.getCarRegistrationYear());
			quote.setCarVariantId(getVehicleVariant(requestDto.getCarVariantId()));
			return;
		}

		if (Constants.VEHICLE_TYPE_MOTORBIKE.equals(requestDto.getVehicleTypeId())) {

			quote.setMotorCycleRegistrationYear(requestDto.getMotorCycleRegistrationYear());
			quote.setMotorCycleVariantId(getVehicleVariant(requestDto.getMotorCycleVariantId()));
			return;
		}

		if (Constants.VEHICLE_TYPE_ALL.equals(requestDto.getVehicleTypeId())) {

			quote.setCarRegistrationYear(requestDto.getCarRegistrationYear());
			quote.setCarVariantId(getVehicleVariant(requestDto.getCarVariantId()));

			quote.setMotorCycleRegistrationYear(requestDto.getMotorCycleRegistrationYear());
			quote.setMotorCycleVariantId(getVehicleVariant(requestDto.getMotorCycleVariantId()));
		}
	}

	private void saveOrUpdateAnswers(Quote quote, QuoteRequestDto requestDto, User user, boolean update) {

	    if (requestDto.getAnswers() == null) {
	        return;
	    }

	    LOGGER.info("saveOrUpdateAnswers called. update={}", update);

	    if (update) {
	        LOGGER.info("Soft delete started");
	        softDeleteExistingAnswers(quote);
	    }

	    for (QuoteAnswerRequestDto answerDto : requestDto.getAnswers()) {

	        Question question = getQuestion(answerDto.getQuestionId());

	        if (isLandingPageQuestion(question)) {
	            LOGGER.info("Skipping save for landing page questionId: {}", question.getQuestionId());
	            continue;
	        }

	        if (answerDto.getOptionId() != null && !answerDto.getOptionId().isEmpty()) {

	            for (Integer optionId : answerDto.getOptionId()) {
	                saveSingleAnswer(quote, answerDto, optionId, user);
	            }

	        } else {
	            saveSingleAnswer(quote, answerDto, null, user);
	        }
	    }
	}
	
	private void softDeleteExistingAnswers(Quote quote) {

	    List<QuoteAnswer> answers =
	            quoteWriteRepositoryHelper.quoteAnswerRepository
	                    .findAnswersByQuote(quote.getQuoteId());

	    LOGGER.info("Found {} existing answers for quote {}",
	            answers.size(), quote.getQuoteId());

	    for (QuoteAnswer answer : answers) {

	        LOGGER.info("Soft deleting answerId={}", answer.getQuoteAnswerId());

	        answer.setDeletedDate(LocalDateTime.now());

	        quoteWriteRepositoryHelper.quoteAnswerRepository.save(answer);
	    }
	}

	private void saveSingleAnswer(Quote quote, QuoteAnswerRequestDto answerDto, Integer optionId, User user) {

		QuoteAnswer answer = new QuoteAnswer();

		answer.setQuote(quote);
		answer.setQuestion(getQuestion(answerDto.getQuestionId()));
		answer.setOption(optionId == null ? null : getOption(optionId));
		answer.setAnswerText(answerDto.getAnswerText());
		answer.setCreatedById(user);
		answer.setCreatedDate(LocalDateTime.now());

		quoteWriteRepositoryHelper.quoteAnswerRepository.save(answer);
	}

	private void saveOrUpdateCoverages(Quote quote, QuoteRequestDto quoterequestDto, User user, boolean update) {

		if (!hasCoverages(quoterequestDto)) {
			return;
		}

		if (update) {
			softDeleteExistingCoverageSelections(quote);
		}

		saveCoverageSelections(quote, quoterequestDto, user);
	}

	private void saveCoverageSelections(Quote quote, QuoteRequestDto quoterequestDto, User user) {

		for (QuoteCoverageSelectionRequestDto selectionDto : quoterequestDto.getCoverageSelections()) {
			saveCoverageTypes(quote, selectionDto, user);
		}
	}

	private void saveCoverageTypes(Quote quote, QuoteCoverageSelectionRequestDto quotecoverageselectionDto, User user) {

		for (Integer coverageTypeId : quotecoverageselectionDto.getCoverageTypeIds()) {

			if (quotecoverageselectionDto.getAddOnIds() == null || quotecoverageselectionDto.getAddOnIds().isEmpty()) {
				saveSingleCoverageSelection(quote, user, quotecoverageselectionDto.getCoverageId(), coverageTypeId,
						null);
			} else {
				saveCoverageAddOns(quote, quotecoverageselectionDto, user, coverageTypeId);
			}
		}
	}

	private void saveCoverageAddOns(Quote quote, QuoteCoverageSelectionRequestDto quotecoverageselectionDto, User user,
			Integer coverageTypeId) {

		for (Integer addOnId : quotecoverageselectionDto.getAddOnIds()) {
			saveSingleCoverageSelection(quote, user, quotecoverageselectionDto.getCoverageId(), coverageTypeId,
					addOnId);
		}
	}

	private void saveSingleCoverageSelection(Quote quote, User user, Integer coverageId, Integer coverageTypeId,
			Integer addOnId) {

		QuoteCoverageSelection selection = new QuoteCoverageSelection();

		selection.setQuote(quote);
		selection.setCoverage(getCoverage(coverageId));
		selection.setCoverageType(getCoverageType(coverageTypeId));
		selection.setAddOn(addOnId == null ? null : getAddOn(addOnId));
		selection.setCreatedById(user);
		selection.setCreatedDate(LocalDateTime.now());

		quoteWriteRepositoryHelper.quoteCoverageSelectionRepository.save(selection);
	}

	private void softDeleteExistingCoverageSelections(Quote quote) {

		List<QuoteCoverageSelection> selections = quoteWriteRepositoryHelper.quoteCoverageSelectionRepository
				.findCoveragesByQuote(quote.getQuoteId());

		for (QuoteCoverageSelection selection : selections) {
			selection.setDeletedDate(LocalDateTime.now());
			quoteWriteRepositoryHelper.quoteCoverageSelectionRepository.save(selection);
		}
	}

	private QuoteCreateResponseDto buildQuoteResponse(Quote quote, boolean completed, boolean update,
			BigDecimal totalPremium) {

		return new QuoteCreateResponseDto(getResponseMessage(completed, update), quote.getQuoteId(), totalPremium);
	}

	private String getResponseMessage(boolean completed, boolean update) {

		if (completed) {
			return Constants.QUOTE_SUBMITTED_SUCCESSFULLY;
		}

		if (update) {
			return Constants.QUOTE_DRAFT_UPDATED_SUCCESSFULLY;
		}

		return Constants.QUOTE_DRAFT_SAVED_SUCCESSFULLY;
	}

	private void validateQuoteNotSubmitted(Quote quote) {

		if (quote.getStatus() != null
				&& TemplateStatus.COMPLETED.name().equalsIgnoreCase(quote.getStatus().getName())) {

			throw new ResourceAlreadyExistsException(ErrorMessageConstants.QUOTE_ALREADY_SUBMITTED);
		}
	}

	private boolean hasCoverages(QuoteRequestDto requestDto) {
		return requestDto.getCoverageSelections() != null && !requestDto.getCoverageSelections().isEmpty();
	}

	private boolean isOtherOption(Option option) {
		return getOptionLabel(option) != null && Constants.OTHER.equalsIgnoreCase(getOptionLabel(option).trim());
	}

	private String getOptionLabel(Option option) {
		return option.getOptionLabel();
	}

	private void validateVehicleYear(Integer year) {

		int currentYear = Year.now().getValue();

		if (year < Constants.MIN_YEAR || year > currentYear) {
			throw new BadRequestException(ErrorMessageConstants.INVALID_YEAR);
		}
	}

	private Quote getQuoteById(String quoteId) {
		return quoteWriteRepositoryHelper.quoteRepository.findActiveQuoteByQuoteId(quoteId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.QUOTE_NOT_FOUND));
	}
 
	private VehicleType getVehicleType(Integer id) {
		return masterRepositoryHelper.vehicleTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_TYPE_NOT_FOUND));
	}

	private VehicleVariant getVehicleVariant(Integer id) {
		return quoteWriteRepositoryHelper.vehicleVariantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_VARIANT_NOT_FOUND));
	}

	private Plan getPlan(Integer id) {
		return masterRepositoryHelper.planRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.PLAN_NOT_FOUND));
	}

	private Status getStatusByName(TemplateStatus status) {

		return masterRepositoryHelper.statusRepository.findByNameIgnoreCaseAndDeletedDateIsNull(status.name())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.STATUS_NOT_FOUND));
	}

	private Question getQuestion(Integer id) {
		return quoteMasterRepositoryHelper.questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.QUESTION_NOT_FOUND));
	}

	private Option getOption(Integer id) {
		return quoteMasterRepositoryHelper.optionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.OPTION_NOT_FOUND));
	}

	private Coverage getCoverage(Integer id) {
		return coverageRepositoryHelper.coverageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_NOT_FOUND));
	}

	private CoverageType getCoverageType(Integer id) {
		return coverageRepositoryHelper.coverageTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_SUBTYPE_NOT_FOUND));
	}

	private AddOn getAddOn(Integer id) {
		return coverageRepositoryHelper.addOnRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.ADD_ON_NOT_FOUND));
	}

	private String generateQuoteId() {
		return Constants.QUOTE_PREFIX + UUID.randomUUID().toString().toUpperCase();
	}

	public User getLoggedInUser() {

		String email = authUtil.getLoggedInUserEmail();

		return masterRepositoryHelper.userRepository.findByEmail(email).orElseThrow(() -> {
			LOGGER.error("User not found for email: {}", email);
			throw new ResourceNotFoundException(ErrorMessageConstants.USER_NOT_FOUND);
		});
	}
}
