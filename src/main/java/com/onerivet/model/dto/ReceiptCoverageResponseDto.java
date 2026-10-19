package com.onerivet.model.dto;
import java.math.BigDecimal;
public class ReceiptCoverageResponseDto {
   private String coverageName;
   private String coverageLimit;
   private BigDecimal premium;
   public ReceiptCoverageResponseDto() {
   }
   public ReceiptCoverageResponseDto(String coverageName, String coverageLimit, BigDecimal premium) {
       this.coverageName = coverageName;
       this.coverageLimit = coverageLimit;
       this.premium = premium;
   }
   public String getCoverageName() {
       return coverageName;
   }
   public void setCoverageName(String coverageName) {
       this.coverageName = coverageName;
   }
   public String getCoverageLimit() {
       return coverageLimit;
   }
   public void setCoverageLimit(String coverageLimit) {
       this.coverageLimit = coverageLimit;
   }
   public BigDecimal getPremium() {
       return premium;
   }
   public void setPremium(BigDecimal premium) {
       this.premium = premium;
   }
}
