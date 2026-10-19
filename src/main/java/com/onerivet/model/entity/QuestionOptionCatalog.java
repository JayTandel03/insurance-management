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
@Table(name = "[QuestionOptionCatalog]", schema = Constants.QUESTION_SCHEMA)
public class QuestionOptionCatalog extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[QuestionOptionCatalogId]")
	private Integer questionOptionCatalogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[OptionId]", nullable = false)
	private Option option;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[QuestionId]")
	private Question question;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public Integer getQuestionOptionCatalogId() {
		return questionOptionCatalogId;
	}

	public void setQuestionOptionCatalogId(Integer questionOptionCatalogId) {
		this.questionOptionCatalogId = questionOptionCatalogId;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
}