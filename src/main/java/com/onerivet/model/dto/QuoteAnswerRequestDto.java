package com.onerivet.model.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class QuoteAnswerRequestDto {

	@NotNull(message = Constants.QUESTION_REQUIRED)
	@Positive(message = ErrorMessageConstants.INVALID_QUESTION_ID)
	private Integer questionId;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<@Positive(message = Constants.INVALID_OPTION_ID) Integer> optionId;

	private String answerText;

	public QuoteAnswerRequestDto() { 
	}

	public QuoteAnswerRequestDto(Integer questionId, List<Integer> optionId, String answerText) {
		this.questionId = questionId;
		this.optionId = optionId;
		this.answerText = answerText;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public List<Integer> getOptionId() {
		return optionId;
	}

	public void setOptionId(List<Integer> optionId) {
		this.optionId = optionId;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
}