package com.onerivet.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "[TemplateCoverageFieldPrice]", schema = Constants.TEMPLATE_SCHEMA)
public class TemplateCoverageFieldPrice extends BaseEntity {

    @Id
    @Column(name = "[TemplateCoverageFieldPriceId]")
    private String templateCoverageFieldPriceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[TemplateId]")
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[CoverageFieldId]")
    private CoverageField coverageField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[PlanId]")
    private Plan plan;

    @Column(name = "[BasePrice]")
    private BigDecimal basePrice;

    @Column(name = "[DeletedDate]")
    private LocalDateTime deletedDate;

    public String getTemplateCoverageFieldPriceId() {
        return templateCoverageFieldPriceId;
    }

    public void setTemplateCoverageFieldPriceId(String templateCoverageFieldPriceId) {
        this.templateCoverageFieldPriceId = templateCoverageFieldPriceId;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public CoverageField getCoverageField() {
        return coverageField;
    }

    public void setCoverageField(CoverageField coverageField) {
        this.coverageField = coverageField;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
    
}