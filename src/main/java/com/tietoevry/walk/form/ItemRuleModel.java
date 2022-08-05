package com.tietoevry.walk.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

public class ItemRuleModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7078076862063410248L;
	
	private Long id;
    private String rule;
    @Positive
    private Double quantity;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdated;
	
    public ItemRuleModel() {
    }

    public ItemRuleModel(final Long id, final String rule, final Double quantity) {
        this.id = id;
        this.rule = rule;
        this.quantity = quantity;
    }

    public ItemRuleModel(final Long id, final String rule, final Double quantity, final LocalDateTime created, final LocalDateTime lastUpdated) {
        this.id = id;
        this.rule = rule;
        this.quantity = quantity;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(final String rule) {
		this.rule = rule;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(final Double quantity) {
		this.quantity = quantity;
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
