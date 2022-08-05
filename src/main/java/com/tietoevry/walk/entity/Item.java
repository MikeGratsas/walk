package com.tietoevry.walk.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable=true)
    @Column(unique = true, nullable = false, length = 200)
    private String name;

    @ManyToOne
    private MeasuringUnit measuringUnit;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RuleItem> ruleItems = new ArrayList<>();

    public Item() {
    }

    public Item(final String name) {
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

    public MeasuringUnit getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(final MeasuringUnit measuringUnit) {
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

    public void addRule(final RuleItem ruleItem) {
    	ruleItems.add(ruleItem);
    	ruleItem.setItem(this);
	}

	public void removeRule(final RuleItem ruleItem) {
		ruleItems.remove(ruleItem);
		ruleItem.setItem(null);
	}

	public List<RuleItem> getItemRules() {
		return ruleItems;
	}

	public void setItemRules(final List<RuleItem> items) {
		this.ruleItems = items;
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        	return true;
        if (obj == null || getClass() != obj.getClass())
        	return false;
        final Item other = (Item) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
