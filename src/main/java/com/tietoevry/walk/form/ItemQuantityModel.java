package com.tietoevry.walk.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

public class ItemQuantityModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3975649436503402360L;
	
	private Long id;
    private ItemModel item;
    @Positive
    private Double quantity;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdated;
	
    public ItemQuantityModel() {
    }

    public ItemQuantityModel(final Long id, final ItemModel item, final Double quantity) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
    }

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public ItemModel getItem() {
		return item;
	}

	public void setItem(final ItemModel item) {
		this.item = item;
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
