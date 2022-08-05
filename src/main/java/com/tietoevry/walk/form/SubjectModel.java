package com.tietoevry.walk.form;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

public class SubjectModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1089315514492624439L;
	
	private Long id;
    @NotBlank
    private String name;
	
    public SubjectModel() {
    }

    public SubjectModel(final Long id, final String name) {
        this.id = id;
        this.name = name;
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


}
