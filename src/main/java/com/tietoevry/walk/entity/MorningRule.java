package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("morning")
public class MorningRule extends DayTimeRule {
	
	private static final int START_HOUR = 6;
	private static final int FINISH_HOUR = 12;

	public MorningRule() {
	}

	public MorningRule(String name) {
		super(name);
	}

	@Override
	protected boolean overlap(int startHour, int finishHour) {
		return startHour < FINISH_HOUR && finishHour >= START_HOUR;
	}
}
