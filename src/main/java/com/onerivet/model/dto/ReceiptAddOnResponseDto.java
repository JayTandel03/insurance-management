package com.onerivet.model.dto;
import java.math.BigDecimal;
public class ReceiptAddOnResponseDto {
   private String addOnName; 
   private BigDecimal premium;
   public ReceiptAddOnResponseDto() {
   }
   public ReceiptAddOnResponseDto(String addOnName, BigDecimal premium) {
       this.addOnName = addOnName;
       this.premium = premium;
   }
   public String getAddOnName() {
       return addOnName;
   }
   public void setAddOnName(String addOnName) {
       this.addOnName = addOnName;
   }
   public BigDecimal getPremium() {
       return premium;
   }
   public void setPremium(BigDecimal premium) {
       this.premium = premium;
   }
}
