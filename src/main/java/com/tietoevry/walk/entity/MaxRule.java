package com.tietoevry.walk.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.tietoevry.walk.form.WalkModel;

@Entity
@DiscriminatorValue("max")
public class MaxRule extends Rule {

	@OneToMany(orphanRemoval = true)
	@JoinTable(name="max_rules")
	private List<Rule> rules = new ArrayList<>();

	public MaxRule() {
	}

    public MaxRule(String name) {
        super(name);
    }

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	@Override
	public long check(WalkModel walk) {		
		final OptionalLong result = rules.stream().mapToLong(r -> r.check(walk)).max();
		return result.orElse(0L);
	}
}
