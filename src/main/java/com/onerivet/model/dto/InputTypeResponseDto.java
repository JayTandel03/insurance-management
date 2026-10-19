package com.onerivet.model.dto;

public class InputTypeResponseDto {
	 private Integer id;
	 private String name;
	 
	 public InputTypeResponseDto() {
		
	 }
	 
	public InputTypeResponseDto(Integer id, String name) {
			this.id = id;
			this.name = name;
	}
		
	 public Integer getId() {
	      return id;
	 }
	 public void setId(Integer id) {
	      this.id = id;
	 }
	 public String getName() {
	      return name;
	 }
	 public void setName(String name) {
	      this.name = name;
	 }	
}
