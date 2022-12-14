package com.tietoevry.walk.form;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class ItemModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4352861710681982893L;
	
	private Long id;
    @NotBlank
    private String name;
    @NotNull
    private String measuringUnit;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdated;
	
    public ItemModel() {
    }

    public ItemModel(final String name, final String measuringUnit) {
        this.name = name;
        this.measuringUnit = measuringUnit;
    }

    public ItemModel(final Long id, final String name, final String measuringUnit) {
        this.id = id;
        this.name = name;
        this.measuringUnit = measuringUnit;
    }

    public ItemModel(final Long id, final String name, final String measuringUnit, final LocalDateTime created, final LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.measuringUnit = measuringUnit;
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

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(final String measuringUnit) {
		this.measuringUnit = measuringUnit;
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

	@Override
	public int hashCode() {
        return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ItemModel)) {
			return false;
		}
		final ItemModel other = (ItemModel) obj;
        return Objects.equals(name, other.name);
	}
}
