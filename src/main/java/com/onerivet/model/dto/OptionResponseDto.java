package com.onerivet.model.dto;

public class OptionResponseDto {
	private Integer optionId;
    private String optionText;
    
    public OptionResponseDto() {
		
	}
    
	public OptionResponseDto(Integer optionId, String optionText) {
		this.optionId = optionId;
		this.optionText = optionText;
	}
	
	public Integer getOptionId() {
       return optionId;
   }
	
   public void setOptionId(Integer optionId) {
       this.optionId = optionId;
   }
   public String getOptionText() {
       return optionText;
   }
   public void setOptionText(String optionText) {
       this.optionText = optionText;
   }
}
