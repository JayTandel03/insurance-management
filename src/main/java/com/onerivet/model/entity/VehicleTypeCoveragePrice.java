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
@Table(name = "[VehicleTypeCoveragePrice]", schema = Constants.TEMPLATE_SCHEMA)
public class VehicleTypeCoveragePrice extends BaseEntity{
	
	@Id
	@Column(name = "[VehicleTypeCoveragePriceId]")
	private String vehicleTypeCoveragePriceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[TemplateId]")
	private Template template;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[PlanId]")
    private Plan plan;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[CoverageId]")
    private Coverage coverage; 

    @Column(name = "[BasePrice]")
    private BigDecimal basePrice;
    
	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public String getVehicleTypeCoveragePriceId() {
		return vehicleTypeCoveragePriceId;
	}

	public void setVehicleTypeCoveragePriceId(String vehicleTypeCoveragePriceId) {
		this.vehicleTypeCoveragePriceId = vehicleTypeCoveragePriceId;
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

	public Coverage getCoverage() {
		return coverage;
	}

	public void setCoverage(Coverage coverage) {
		this.coverage = coverage;
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