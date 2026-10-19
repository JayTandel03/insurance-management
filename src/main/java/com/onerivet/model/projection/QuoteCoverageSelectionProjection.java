package com.onerivet.model.projection;
import java.math.BigDecimal;
public class QuoteCoverageSelectionProjection {
   private Integer coverageId;
   private String coverage;
   private BigDecimal basePrice;
   private Integer coverageTypeId;
   private String coverageType;
   private Integer addOnId;
   private String addOn;
   public QuoteCoverageSelectionProjection(
           Integer coverageId,
           String coverage,
           BigDecimal basePrice,
           Integer coverageTypeId,
           String coverageType,
           Integer addOnId,
           String addOn) {
       this.coverageId = coverageId;
       this.coverage = coverage;
       this.basePrice = basePrice;
       this.coverageTypeId = coverageTypeId;
       this.coverageType = coverageType;
       this.addOnId = addOnId;
       this.addOn = addOn;
   }
   public Integer getCoverageId() {
       return coverageId;
   }
   public String getCoverage() {
       return coverage;
   }
   public BigDecimal getBasePrice() {
       return basePrice;
   }
   public Integer getCoverageTypeId() {
       return coverageTypeId;
   }
   public String getCoverageType() {
       return coverageType;
   }
   public Integer getAddOnId() {
       return addOnId;
   }
   public String getAddOn() {
       return addOn;
   }
}