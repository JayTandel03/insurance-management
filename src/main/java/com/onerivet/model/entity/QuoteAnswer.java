package com.onerivet.model.entity;

import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "[QuoteAnswer]", schema = Constants.QUOTE_SCHEMA)
public class QuoteAnswer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[QuoteAnswerId]")
	private Integer quoteAnswerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[QuoteId]")
	private Quote quote;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[QuestionId]")
	private Question question;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[OptionId]")
	private Option option;

	@Column(name = "[AnswerText]")
	private String answerText;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public Integer getQuoteAnswerId() {
		return quoteAnswerId;
	}

	public void setQuoteAnswerId(Integer quoteAnswerId) {
		this.quoteAnswerId = quoteAnswerId;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
}
