package com.onerivet.model.dto;

import java.util.List;

public class QuoteAnswerResponseDto {

    private Integer questionId;
    private String question;
    private String answerText;

    private List<QuoteOptionResponseDto> selectedOptions;

	public QuoteAnswerResponseDto(Integer questionId, String question, String answerText,
			List<QuoteOptionResponseDto> selectedOptions) {
		this.questionId = questionId;
		this.question = question;
		this.answerText = answerText;
		this.selectedOptions = selectedOptions;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public List<QuoteOptionResponseDto> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(List<QuoteOptionResponseDto> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}
    
    
}
	
