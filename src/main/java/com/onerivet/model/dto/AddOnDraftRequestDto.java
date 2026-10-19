package com.onerivet.model.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class AddOnDraftRequestDto {
    private Integer addOnId;
    @Size(min = 3, max = 50, message = "Add On Name should be between 3 to 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$", message = "Add On Name accepts alphanumeric characters and spaces only")
    private String addOnName;
    private BigDecimal basePrice;
    private Integer towingLimitInMiles;
    public Integer getAddOnId() {
        return addOnId;
    }
    public void setAddOnId(Integer addOnId) {
        this.addOnId = addOnId;
    }
    public String getAddOnName() {
        return addOnName;
    }
    public void setAddOnName(String addOnName) {
        this.addOnName = addOnName;
    }
 
    public BigDecimal getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
    public Integer getTowingLimitInMiles() {
        return towingLimitInMiles;
    }
    public void setTowingLimitInMiles(Integer towingLimitInMiles) {
        this.towingLimitInMiles = towingLimitInMiles;
    }
}