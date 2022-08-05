package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("evening")
public class EveningRule extends DayTimeRule {
	
	private static final int START_HOUR = 18;
	private static final int FINISH_HOUR = 24;

	public EveningRule() {
	}

	public EveningRule(final String name) {
		super(name);
	}

	@Override
	protected boolean overlap(final int startHour, final int finishHour) {
		return startHour < FINISH_HOUR && finishHour >= START_HOUR;
	}
}
