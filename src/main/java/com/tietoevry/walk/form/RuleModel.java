package com.tietoevry.walk.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

public class RuleModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4105699973508481597L;
	
	private Long id;
    @NotBlank
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdated;
	
    public RuleModel() {
    }

    public RuleModel(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public RuleModel(final Long id, final String name, final LocalDateTime created, final LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.lastUpdated = lastUpdated;
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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(final LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(final LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
