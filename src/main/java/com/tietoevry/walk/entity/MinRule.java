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
@DiscriminatorValue("min")
public class MinRule extends Rule {

	@OneToMany(orphanRemoval = true)
	@JoinTable(name="min_rule")
	private List<Rule> rules = new ArrayList<>();

	public MinRule() {
	}

    public MinRule(final String name) {
        super(name);
    }

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(final List<Rule> rules) {
		this.rules = rules;
	}
	
	@Override
	public long check(final WalkModel walk) {		
		final OptionalLong result = rules.stream().mapToLong(r -> r.check(walk)).min();
		return result.orElse(0L);
	}
}
