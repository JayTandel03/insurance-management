package com.onerivet.model.projection;

public class QuoteAnswerProjection {
	private final Integer questionId;
	private final String question;
	private final String answerText;
	private final Integer optionId;
	private final String optionLabel;

	public QuoteAnswerProjection(Integer questionId, String question, String answerText, Integer optionId,
			String optionLabel) {
		this.questionId = questionId;
		this.question = question;
		this.answerText = answerText;
		this.optionId = optionId;
		this.optionLabel = optionLabel;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswerText() {
		return answerText;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public String getOptionLabel() {
		return optionLabel;
	}
}