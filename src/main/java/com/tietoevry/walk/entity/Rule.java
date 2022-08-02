package com.tietoevry.walk.entity;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tietoevry.walk.form.WalkModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="rule_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("null")
public class Rule implements ICheckRule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(unique = true, nullable = false)
	private String name;

	@OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RuleItem> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    public Rule() {
    }

    public Rule(String name) {
        this.name = name;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void addItem(RuleItem item) {
		items.add(item);
		item.setRule(this);
	}

	public void removeItem(RuleItem item) {
		items.remove(item);
		item.setRule(null);
	}

	public List<RuleItem> getItems() {
		return items;
	}

	public void setItems(List<RuleItem> items) {
		this.items = items;
	}

	@Override
	public long check(WalkModel walk) {
		return 1;
	}
}
