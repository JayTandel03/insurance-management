package com.onerivet.model.dto;

import java.math.BigDecimal;

public class TemplateAddOnResponseDto {

        private Integer addOnId;
        private String addOn;
        private BigDecimal basePrice;

    public TemplateAddOnResponseDto() {}

    public TemplateAddOnResponseDto(Integer addOnId, String addOn, BigDecimal basePrice) {
        this.addOnId = addOnId;
        this.addOn = addOn;
        this.basePrice = basePrice;
    }

    public Integer getAddOnId() {
            return addOnId;
        }

        public void setAddOnId(Integer addOnId) {
            this.addOnId = addOnId;
        }

        public String getAddOn() {
            return addOn;
        }


        public void setAddOn(String addOn) {
            this.addOn = addOn;
        }

        public BigDecimal getBasePrice() {
            return basePrice;
        }

        public void setBasePrice(BigDecimal bigDecimal) {
            this.basePrice = bigDecimal;
        }

}
