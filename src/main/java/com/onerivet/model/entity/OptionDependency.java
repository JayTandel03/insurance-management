package com.onerivet.model.entity;

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
@Table(name = "[OptionDependency]", schema = Constants.QUESTION_SCHEMA)
public class OptionDependency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[OptionDependencyId]")
    private Integer optionDependencyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[ParentQuestionId]", nullable = false)
    private Question parentQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[ParentOptionId]", nullable = false)
    private Option parentOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[ChildQuestionId]", nullable = false)
    private Question childQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[ChildOptionId]", nullable = false)
    private Option childOption;

    public Integer getOptionDependencyId() {
        return optionDependencyId;
    }

    public void setOptionDependencyId(Integer optionDependencyId) {
        this.optionDependencyId = optionDependencyId;
    }

    public Question getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(Question parentQuestion) {
        this.parentQuestion = parentQuestion;
    }

    public Option getParentOption() {
        return parentOption;
    }

    public void setParentOption(Option parentOption) {
        this.parentOption = parentOption;
    }

    public Question getChildQuestion() {
        return childQuestion;
    }

    public void setChildQuestion(Question childQuestion) {
        this.childQuestion = childQuestion;
    }

    public Option getChildOption() {
        return childOption;
    }

    public void setChildOption(Option childOption) {
        this.childOption = childOption;
    }
}