package com.tietoevry.walk.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WalkItemModel {
	
	private Long id;
    @NotBlank
    private String name;
    @NotNull
    private String measuringUnit;
    @NotNull
    private Double quantity;
	
    public WalkItemModel() {
    }

    public WalkItemModel(Long id, String name, String measuringUnit) {
        this.id = id;
        this.name = name;
        this.measuringUnit = measuringUnit;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
}
