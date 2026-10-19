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
@Table(name = "[TemplateAddOnPrice]", schema = Constants.TEMPLATE_SCHEMA)
public class TemplateAddOnPrice extends BaseEntity {

	@Id
	@Column(name = "[TemplateAddOnPriceId]")
	private String templateAddOnPriceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[TemplateId]")
	private Template template;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[AddOnId]")
	private AddOn addOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[PlanId]")
	private Plan plan;

	@Column(name = "[BasePrice]")
	private BigDecimal basePrice;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public String getTemplateAddOnPriceId() {
		return templateAddOnPriceId;
	}

	public void setTemplateAddOnPriceId(String templateAddOnPriceId) {
		this.templateAddOnPriceId = templateAddOnPriceId;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
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