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

    public WalkItemModel(final Long id, final String name, final String measuringUnit) {
        this.id = id;
        this.name = name;
        this.measuringUnit = measuringUnit;
    }

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(final String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(final Double quantity) {
		this.quantity = quantity;
	}
}
