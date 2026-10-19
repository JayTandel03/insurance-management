package com.onerivet.model.dto;
public class TemplateDraftResponseDto {
    private String templateId;
    private String message;
    public TemplateDraftResponseDto(String templateId, String message) {

        this.templateId = templateId;
        this.message = message;
    }
    public String getTemplateId() {
        return templateId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}