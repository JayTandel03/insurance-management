package com.onerivet.model.projection;

public class AddOnProjection {

    private Integer addOnId;
    private String name;

    
	public AddOnProjection() {}

	public AddOnProjection(Integer addOnId, String name) {
		this.addOnId = addOnId;
		this.name = name;
	}

    public Integer getAddOnId() {
        return addOnId;
    }

    public String getName() {
        return name;
    }

}