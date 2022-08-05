package com.tietoevry.walk.form;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MeasuringUnitModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9019235401434472341L;
	
	private Long id;
    @NotNull
    private String name;
    @NotBlank
    private String description;
	
    public MeasuringUnitModel() {
    }

    public MeasuringUnitModel(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public MeasuringUnitModel(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
