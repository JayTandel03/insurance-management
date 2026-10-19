package com.onerivet.model.entity;

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
@Table(name = "[TemplatePlan]", schema = Constants.TEMPLATE_SCHEMA)
public class TemplatePlan extends BaseEntity {

	@Id
	@Column(name = "[TemplatePlanId]")
	private String templatePlanId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[TemplateId]")
	private Template template;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[PlanId]")
	private Plan plan;
	
	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;
	
	public String getTemplatePlanId() {
		return templatePlanId;
	}

	public void setTemplatePlanId(String templatePlanId) {
		this.templatePlanId = templatePlanId;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
	
}