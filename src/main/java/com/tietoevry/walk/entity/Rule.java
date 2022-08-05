package com.tietoevry.walk.entity;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tietoevry.walk.form.WalkModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="rule_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("null")
public class Rule implements ICheckRule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(unique = true, nullable = false, length = 100)
	private String name;

	@OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RuleItem> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    public Rule() {
    }

    public Rule(final String name) {
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

    public void addItem(final RuleItem item) {
		items.add(item);
		item.setRule(this);
	}

	public void removeItem(final RuleItem item) {
		items.remove(item);
		item.setRule(null);
	}

	public List<RuleItem> getItems() {
		return items;
	}

	public void setItems(final List<RuleItem> items) {
		this.items = items;
	}

	@Override
	public long check(final WalkModel walk) {
		return 1;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        	return true;
        if (obj == null || getClass() != obj.getClass())
        	return false;
        final Rule other = (Rule) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
