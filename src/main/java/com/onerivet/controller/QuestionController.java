package com.onerivet.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.model.dto.QuestionAllResponseDto;
import com.onerivet.model.dto.QuestionRequestDto;
import com.onerivet.model.dto.QuestionResponseDto;
import com.onerivet.model.dto.StatusRequestDto;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.service.QuestionService;
import com.onerivet.util.Constants;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

	private final QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GenericResponse<String> createQuestion(@Valid @RequestBody QuestionRequestDto questionRequestDto) {

		LOGGER.info("Create Question API called");

		String response = questionService.createQuestion(questionRequestDto);
		GenericResponse<String> genericResponse = new GenericResponse<>();
		genericResponse.setData(response);
		return genericResponse;
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@PutMapping("/{questionId}")
	public GenericResponse<String> editQuestion( @PathVariable Integer questionId,
			@Valid @RequestBody QuestionRequestDto questionRequestDto) {

		LOGGER.info("Edit Question API called for questionId : {}", questionId);

		questionRequestDto.setQuestionId(questionId);
		String response = questionService.editQuestion(questionId, questionRequestDto);
		GenericResponse<String> genericResponse = new GenericResponse<>();
		genericResponse.setData(response);
		return genericResponse;
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@GetMapping("/{questionId}")
	public GenericResponse<QuestionResponseDto> getQuestionById(@PathVariable Integer questionId) {

		LOGGER.info("Get Question API called for questionId : {}", questionId);

		QuestionResponseDto response = questionService.getQuestionById(questionId);
		return new GenericResponse<>(response);
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@PatchMapping("/{questionId}")
	public GenericResponse<String> updateQuestionStatus(@PathVariable Integer questionId,
			@Valid @RequestBody StatusRequestDto statusRequestDto) {

		LOGGER.info("Update Question Status API called for questionId : {}", questionId);

		String response = questionService.updateQuestionStatus(questionId, statusRequestDto);
		GenericResponse<String> genericResponse = new GenericResponse<>();
		genericResponse.setData(response);
		return genericResponse;
	}

	@PreAuthorize(Constants.HAS_ANY_ROLE)
	@GetMapping
	public GenericResponse<List<QuestionAllResponseDto>> getAllQuestions(

			@RequestParam(defaultValue = "1") int page,

			@RequestParam(defaultValue = "10") int size,

			@RequestParam(required = false) String search,

			@RequestParam(required = false) Integer vehicleTypeId,

			@RequestParam(required = false) Integer statusId,

			@RequestParam(defaultValue = "1", required = false) Integer questionType,

			@RequestParam(required = false) Boolean isMandatory,

			@RequestParam(required = false) Boolean isActive,@RequestParam Map<String, String> requestParams) {

		LOGGER.info(
				"Get All Questions API called with filters: page={}, size={}, search={}, vehicleTypeId={}, statusId={}, questionType={}, isMandatory={}, isActive={}",
				page, size, search, vehicleTypeId, statusId, questionType, isMandatory, isActive);

		GenericResponse<List<QuestionAllResponseDto>> questionPageResponse = questionService.getAllQuestions(page, size, search,
				vehicleTypeId, statusId, questionType, isMandatory, isActive,requestParams);

		LOGGER.info("Questions fetched successfully"); 

		return questionPageResponse; 
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@DeleteMapping("/{questionId}")
	public GenericResponse<String> deleteQuestionById(@PathVariable Integer questionId) {

		LOGGER.info("Delete Question API called for questionId : {}", questionId);

		String response = questionService.deleteQuestionById(questionId);

		GenericResponse<String> genericResponse = new GenericResponse<>();

		genericResponse.setData(response);

		return genericResponse;
	}
}