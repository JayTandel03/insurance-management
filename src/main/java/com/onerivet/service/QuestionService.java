package com.onerivet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceAlreadyExistsException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.InputTypeResponseDto;
import com.onerivet.model.dto.OptionRequestDto;
import com.onerivet.model.dto.OptionResponseDto;
import com.onerivet.model.dto.QuestionAllResponseDto;
import com.onerivet.model.dto.QuestionRequestDto;
import com.onerivet.model.dto.QuestionResponseDto;
import com.onerivet.model.dto.StatusRequestDto;
import com.onerivet.model.dto.VehicleTypeResponseDto;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.model.dto.StatusResponseDto;
import com.onerivet.model.entity.InputType;
import com.onerivet.model.entity.Option;
import com.onerivet.model.entity.Question;
import com.onerivet.model.entity.QuestionOptionCatalog;
import com.onerivet.model.entity.Status;
import com.onerivet.model.entity.VehicleType;
import com.onerivet.model.projection.OptionLabelProjection;
import com.onerivet.model.projection.QuestionProjection;
import com.onerivet.repository.InputTypeRepository;
import com.onerivet.repository.OptionRepository;
import com.onerivet.repository.QuestionOptionCatalogRepository;
import com.onerivet.repository.QuestionRepository;
import com.onerivet.repository.StatusRepository;
import com.onerivet.repository.VehicleTypeRepository;
import com.onerivet.specification.QuestionSpecification;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class QuestionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);
	private final QuestionRepository questionRepository;
	private final QuestionOptionCatalogRepository catalogRepository;
	private final VehicleTypeRepository vehicleTypeRepository;
	private final InputTypeRepository inputTypeRepository;
	private final StatusRepository statusRepository;
	private final OptionRepository optionRepository;

	public QuestionService(QuestionRepository questionRepository, QuestionOptionCatalogRepository catalogRepository,VehicleTypeRepository vehicleTypeRepository, InputTypeRepository inputTypeRepository,
			StatusRepository statusRepository, OptionRepository optionRepository) {
		this.questionRepository = questionRepository;
		this.catalogRepository = catalogRepository;
		this.vehicleTypeRepository = vehicleTypeRepository;
		this.inputTypeRepository = inputTypeRepository;
		this.statusRepository = statusRepository;
		this.optionRepository = optionRepository;
	}

	@Transactional
	public String createQuestion(QuestionRequestDto questionRequestDto) {
		
		LOGGER.info("Create question request received");
		
		validateBaseRequest(questionRequestDto);
		applyQuestionTypeRules(questionRequestDto);
		String questionText = questionRequestDto.getQuestion().trim();
		
		if (questionRepository.existsByQuestionIgnoreCase(questionText)) {
			throw new ResourceAlreadyExistsException(ErrorMessageConstants.QUESTION_ALREADY_EXISTS);
		}
		
		InputType inputType = getInputType(questionRequestDto.getInputTypeId());
		validateInputTypeRules(inputType.getInputTypeId(), questionRequestDto.getOptions());
		VehicleType vehicleType = fetchVehicleType(questionRequestDto.getVehicleTypeId());
		Status status = getCreatedStatus();
		Question question = buildQuestionEntity(questionRequestDto, questionText, inputType, vehicleType, status);
		Question savedQuestion = questionRepository.save(question);
		validateDuplicateOptions(questionRequestDto.getOptions());
		saveOptions(savedQuestion, questionRequestDto.getOptions(), inputType.getInputTypeId());
		
		LOGGER.debug("Question created successfully with id : {}", savedQuestion.getQuestionId());
		return Constants.QUESTION_CREATED;
	}

	private Question buildQuestionEntity(QuestionRequestDto dto, String questionText, InputType inputType,
			VehicleType vehicleType, Status status) {
		
		Question question = new Question();
		question.setVehicleType(vehicleType);
		question.setQuestionType(dto.getQuestionType());
		question.setQuestion(questionText);
		question.setHelpText(dto.getHelpText());
		question.setInputType(inputType);
		question.setIsMandatory(dto.getIsMandatory());
		question.setIsActive(false);
		question.setStatus(status);
		return question;
	}

	@Transactional
	public String editQuestion(Integer questionId, QuestionRequestDto questionRequestDto) {
		
		LOGGER.info("Edit question request received. QuestionId : {}", questionId);
		
		validateBaseRequest(questionRequestDto);
		applyQuestionTypeRules(questionRequestDto);
		
		Question existingQuestion = questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.QUESTION_NOT_FOUND));

		if (existingQuestion.getDeletedDate() != null) {
			return ErrorMessageConstants.DELETED_QUESTION_CANNOT_BE_EDITED;
		}

		if (!Constants.CREATED.equalsIgnoreCase(existingQuestion.getStatus().getName())) {
			return ErrorMessageConstants.ONLY_CREATED_CAN_EDIT;
		}
		
		if (Boolean.TRUE.equals(existingQuestion.getIsActive())) {
			return ErrorMessageConstants.PUBLISHED_QUESTION_MODIFIED;
		}
		
		if (isNoChange(existingQuestion, questionRequestDto)) {
			return Constants.QUESTION_NO_CHANGES;
		}
		 
		String updatedQuestion = questionRequestDto.getQuestion().trim();
		
		if (questionRepository.existsByQuestionIgnoreCaseAndQuestionIdNot(updatedQuestion, questionId)) {
			throw new ResourceAlreadyExistsException(ErrorMessageConstants.DUPLICATE_QUESTION_FOUND);
		}
		
		InputType inputType = getInputType(questionRequestDto.getInputTypeId());
		validateInputTypeRules(inputType.getInputTypeId(), questionRequestDto.getOptions());
		existingQuestion.setVehicleType(fetchVehicleType(questionRequestDto.getVehicleTypeId()));
		existingQuestion.setQuestionType(questionRequestDto.getQuestionType());
		existingQuestion.setQuestion(updatedQuestion);
		existingQuestion.setHelpText(questionRequestDto.getHelpText());
		existingQuestion.setInputType(inputType);
		existingQuestion.setIsMandatory(questionRequestDto.getIsMandatory());
		existingQuestion.setIsActive(false);
		questionRepository.save(existingQuestion);
		validateDuplicateOptions(questionRequestDto.getOptions());
		catalogRepository.deleteByQuestionId(questionId);
		saveOptions(existingQuestion, questionRequestDto.getOptions(), inputType.getInputTypeId());
		LOGGER.debug("Question updated successfully with id : {}", existingQuestion.getQuestionId());
		return Constants.QUESTION_UPDATED;
	}

	public QuestionResponseDto getQuestionById(Integer questionId) {
		
		LOGGER.info("Fetch question request received. QuestionId : {}", questionId);
		
		if (questionId == null || questionId <= 0) {
			throw new BadRequestException(ErrorMessageConstants.INVALID_QUESTION_ID);
		}
		
		List<QuestionProjection> questionData = questionRepository.findQuestionDetails(questionId);
		
		if (questionData == null || questionData.isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessageConstants.QUESTION_NOT_FOUND);
		}
		
		QuestionProjection row = questionData.get(0);
		QuestionResponseDto response = new QuestionResponseDto();
		response.setQuestionId(row.getQuestionId());
		response.setQuestion(row.getQuestion());
		response.setHelpText(row.getHelpText());
		response.setQuestionType(row.getQuestionType());
		response.setIsMandatory(row.getIsMandatory());
		response.setIsActive(row.getIsActive());
		VehicleTypeResponseDto vehicleTypeResponse = new VehicleTypeResponseDto();
		vehicleTypeResponse.setVehicleTypeId(row.getVehicleTypeId());
		vehicleTypeResponse.setVehicle(row.getVehicleTypeName());
		response.setVehicleType(vehicleTypeResponse);
		InputTypeResponseDto inputTypeResponse = new InputTypeResponseDto();
		inputTypeResponse.setId(row.getInputTypeId());
		inputTypeResponse.setName(row.getInputTypeName());
		response.setInputType(inputTypeResponse);
		List<OptionResponseDto> options = new ArrayList<>();
		
		for (QuestionProjection projection : questionData) {
			if (projection.getOptionId() != null) {
				OptionResponseDto option = new OptionResponseDto();
				option.setOptionId(projection.getOptionId());
				option.setOptionText(projection.getOptionText());
				options.add(option);
			}
		}
		response.setOptions(options);
		return response;
	}

	@Transactional
	public String updateQuestionStatus(Integer questionId, StatusRequestDto statusRequestDto) {
		
		LOGGER.info("Update question status request received. QuestionId : {}", questionId);
		
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.QUESTION_NOT_FOUND));
		String requestedStatus = statusRequestDto.getStatus().trim().toUpperCase();

		if (question.getDeletedDate() != null) {
			return ErrorMessageConstants.DELETED_QUESTION_CANNOT_CHANGE_STATUS;
		}

		if (Constants.PUBLISHED.equals(requestedStatus) && Boolean.TRUE.equals(question.getIsActive())) {
			throw new ResourceAlreadyExistsException(ErrorMessageConstants.QUESTION_ALREADY_PUBLISHED);
		}
		
		if (Constants.DRAFT.equals(requestedStatus) && Boolean.FALSE.equals(question.getIsActive())) {
			throw new ResourceAlreadyExistsException(ErrorMessageConstants.QUESTION_ALREADY_DRAFT);
		}
		
		if (!Constants.CREATED.equalsIgnoreCase(question.getStatus().getName())) {
			return ErrorMessageConstants.ONLY_CREATED_CAN_CHANGE_STATUS;
		}
		
		switch (requestedStatus) {
		case Constants.PUBLISHED:
			question.setIsActive(true);
			questionRepository.save(question);
			LOGGER.info("Question published. QuestionId : {}", questionId);
			return Constants.QUESTION_PUBLISHED;
		case Constants.DRAFT:
			question.setIsActive(false);
			questionRepository.save(question);
			LOGGER.info("Question moved to draft. QuestionId : {}", questionId);
			return Constants.QUESTION_MOVED_TO_DRAFT;
		default:
			throw new BadRequestException(Constants.INVALID_STATUS);
		}
	}

	@Transactional
	public String deleteQuestionById(Integer questionId) {
		
		LOGGER.info("Delete question request received. QuestionId : {}", questionId);
		Question question = questionRepository.findActiveQuestionById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.QUESTION_NOT_FOUND));
		
		if (Boolean.TRUE.equals(question.getIsActive())) {
			return ErrorMessageConstants.PUBLISHED_QUESTION_DELETE;
		}
		
		question.setDeletedDate(LocalDateTime.now());
		catalogRepository.softDeleteByQuestionId(questionId);
		questionRepository.save(question);
		
		LOGGER.info("Question deleted successfully. QuestionId : {}", questionId);
		return Constants.QUESTION_DELETED;
	}

	@Transactional
	public GenericResponse<List<QuestionAllResponseDto>> getAllQuestions(int page, int size, String search,
			Integer vehicleTypeId, Integer statusId, Integer questionType, Boolean isMandatory, Boolean isActive,
			Map<String, String> requestParams) {
		
		LOGGER.info("GetAllQuestions API called");
		
		validateRequestParams(requestParams);
		Pageable pageable = PageRequest.of(page - 1, size,
				org.springframework.data.domain.Sort.by("questionId").ascending());
		Specification<Question> spec = Specification.where(QuestionSpecification.notDeleted())
				.and(QuestionSpecification.searchByQuestion(search))
				.and(QuestionSpecification.filterByVehicleType(vehicleTypeId))
				.and(QuestionSpecification.filterByStatus(statusId))
				.and(QuestionSpecification.filterByQuestionType(questionType))
				.and(QuestionSpecification.filterByMandatory(isMandatory))
				.and(QuestionSpecification.filterByActive(isActive));
		Page<Question> pageResult = questionRepository.findAll(spec, pageable);
		List<QuestionAllResponseDto> questionsResponse = pageResult.getContent().stream().map(this::mapToDto).toList();
		return new GenericResponse<>(questionsResponse);
	}

	private QuestionAllResponseDto mapToDto(Question question) {
		QuestionAllResponseDto questionResponseDto = new QuestionAllResponseDto();
		questionResponseDto.setQuestionId(question.getQuestionId());
		questionResponseDto.setQuestion(question.getQuestion());
		questionResponseDto.setVehicleType(Optional.ofNullable(question.getVehicleType()).map(
				vehicleType -> new VehicleTypeResponseDto(vehicleType.getVehicleTypeId(), vehicleType.getVehicle()))
				.orElse(null));
		questionResponseDto.setStatus(Optional.ofNullable(question.getStatus())
				.map(status -> new StatusResponseDto(status.getStatusId(), status.getName())).orElse(null));
		questionResponseDto.setIsMandatory(question.getIsMandatory());
		questionResponseDto.setIsActive(question.getIsActive());
		return questionResponseDto;
	}

	private void validateRequestParams(Map<String, String> requestParams) {
		List<String> allowedParams = List.of("page", "size", "questionType", "search", "vehicleTypeId", "statusId",
				"isMandatory", "isActive");
		for (String param : requestParams.keySet()) {
			if (!allowedParams.contains(param)) {
				throw new BadRequestException("Invalid request parameter : " + param);
			}
		}
	}

	private void validateBaseRequest(QuestionRequestDto questionRequestDto) {
		
		if (questionRequestDto.getQuestionType() == null) {
			throw new BadRequestException(ErrorMessageConstants.QUESTION_TYPE_REQUIRED);
		}
		
		if (!Constants.PERSONAL_QUESTION_TYPE.equals(questionRequestDto.getQuestionType())
				&& !Constants.VEHICLE_QUESTION_TYPE.equals(questionRequestDto.getQuestionType())) {
			throw new BadRequestException(ErrorMessageConstants.INVALID_QUESTION_TYPE);
		}
		
		if (questionRequestDto.getIsMandatory() == null) {
			throw new BadRequestException(ErrorMessageConstants.MANDATORY_FIELD_REQUIRED);
		}
	}

	private void applyQuestionTypeRules(QuestionRequestDto questionRequestDto) {
		
		if (Constants.PERSONAL_QUESTION_TYPE.equals(questionRequestDto.getQuestionType())) {
			if (questionRequestDto.getVehicleTypeId() != null) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_TYPE_NOT_REQUIRED);
			}
			questionRequestDto.setVehicleTypeId(null);
		}
		
		if (Constants.VEHICLE_QUESTION_TYPE.equals(questionRequestDto.getQuestionType())) {
			if (questionRequestDto.getVehicleTypeId() == null || questionRequestDto.getVehicleTypeId() <= 0) {
				throw new BadRequestException(ErrorMessageConstants.VEHICLE_TYPE_REQUIRED);
			}
			boolean isValidVehicleType = Constants.CAR.equals(questionRequestDto.getVehicleTypeId())
					|| Constants.MOTORCYCLE.equals(questionRequestDto.getVehicleTypeId())
					|| Constants.BOTH.equals(questionRequestDto.getVehicleTypeId());
			if (!isValidVehicleType) {
				throw new BadRequestException(ErrorMessageConstants.INVALID_VEHICLE_TYPE_ID);
			}
		}
	}

	private void saveOptions(Question question, List<OptionRequestDto> options, Integer inputTypeId) {
		
		boolean isOptionRequired = isOptionRequired(inputTypeId);
		if (isOptionRequired && (options == null || options.isEmpty())) {
			throw new BadRequestException(ErrorMessageConstants.OPTIONS_REQUIRED);
		}
		
		if (options != null && options.size() > 10) {
			throw new BadRequestException(ErrorMessageConstants.MAX_OPTIONS_ALLOWED);
		}
		
		if (!isOptionRequired || options == null) {
			return;
		}

		for (OptionRequestDto optionRequest : options) {
			String optionLabel = optionRequest.getOptionLabel().trim();
			Option savedOption = optionRepository.findByOptionLabelIgnoreCase(optionLabel).orElseGet(() -> {
				Option newOption = new Option();
				newOption.setOptionLabel(optionLabel);
				return optionRepository.save(newOption);
			});
			QuestionOptionCatalog catalog = new QuestionOptionCatalog();
			catalog.setQuestion(question);
			catalog.setOption(savedOption);
			catalogRepository.save(catalog);
		}
	}

	private boolean isOptionRequired(Integer inputTypeId) {
		return Constants.RADIO_INPUT_TYPE.equals(inputTypeId) || Constants.DROPDOWN_INPUT_TYPE.equals(inputTypeId)
				|| Constants.CHECKBOX_INPUT_TYPE.equals(inputTypeId);
	}

	private void validateInputTypeRules(Integer inputTypeId, List<OptionRequestDto> options) {
		boolean isOptionAllowed = Constants.RADIO_INPUT_TYPE.equals(inputTypeId)
				|| Constants.DROPDOWN_INPUT_TYPE.equals(inputTypeId)
				|| Constants.CHECKBOX_INPUT_TYPE.equals(inputTypeId);
		
		if (!isOptionAllowed && options != null && !options.isEmpty()) {
			throw new BadRequestException(ErrorMessageConstants.OPTIONS_NOT_ALLOWED);
		}
	}

	private VehicleType fetchVehicleType(Integer vehicleTypeId) {
		
		if (vehicleTypeId == null) {
			return null;
		}
		return vehicleTypeRepository.findById(vehicleTypeId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_TYPE_NOT_FOUND));
	}

	private InputType getInputType(Integer inputTypeId) {
		return inputTypeRepository.findById(inputTypeId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.INPUT_TYPE_NOT_FOUND));
	}

	private Status getCreatedStatus() {
		return statusRepository.findByName(Constants.CREATED)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.CREATED_STATUS_NOT_FOUND));
	}

	private boolean isNoChange(Question existing, QuestionRequestDto request) {
		var existingVehicleTypeId = Optional.ofNullable(existing.getVehicleType()).map(VehicleType::getVehicleTypeId)
				.orElse(null); 

		var existingInputTypeId = Optional.ofNullable(existing.getInputType()).map(InputType::getInputTypeId)
				.orElse(null);

		boolean questionSame = Objects.equals(existing.getQuestion(), request.getQuestion().trim())
				&& Objects.equals(existing.getQuestionType(), request.getQuestionType())
				&& Objects.equals(existing.getHelpText(), request.getHelpText())
				&& Objects.equals(existingInputTypeId, request.getInputTypeId())
				&& Objects.equals(existing.getIsMandatory(), request.getIsMandatory())
				&& Objects.equals(existingVehicleTypeId, request.getVehicleTypeId());

		boolean optionsSame = isOptionsSame(existing.getQuestionId(), request.getOptions());
		return questionSame && optionsSame;
	}

	private boolean isOptionsSame(Integer questionId, List<OptionRequestDto> requestOptions) {
		
		if (requestOptions == null || requestOptions.isEmpty()) {
			return true;
		}
		
		List<OptionLabelProjection> existingOptions = catalogRepository.findOptionLabelsByQuestionId(questionId);
		
		if (existingOptions.size() != requestOptions.size()) {
			return false;
		}
		
		List<String> existingOptionTexts = existingOptions.stream().map(OptionLabelProjection::getOptionLabel)
				.filter(Objects::nonNull).map(option -> option.trim().toLowerCase()).sorted().toList();
		List<String> requestOptionTexts = requestOptions.stream().map(OptionRequestDto::getOptionLabel)
				.filter(Objects::nonNull).map(option -> option.trim().toLowerCase()).sorted().toList();
		return Objects.equals(existingOptionTexts, requestOptionTexts);
	}

	private void validateDuplicateOptions(List<OptionRequestDto> options) {
		
		if (options == null || options.isEmpty()) {
			return;
		}
		
		List<String> optionLabels = options.stream().filter(option -> option.getOptionLabel() != null)
				.map(option -> option.getOptionLabel().trim().toLowerCase()).toList();
		long uniqueCount = optionLabels.stream().distinct().count();
		
		if (uniqueCount != optionLabels.size()) {
			throw new ResourceAlreadyExistsException(ErrorMessageConstants.DUPLICATE_OPTION_FOUND);
		}
	}
}
