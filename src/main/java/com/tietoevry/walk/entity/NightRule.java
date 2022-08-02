package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("night")
public class NightRule extends DayTimeRule {
	
	private static final int START_HOUR = 0;
	private static final int FINISH_HOUR = 6;

	public NightRule() {
	}

	public NightRule(String name) {
		super(name);
	}

	@Override
	protected boolean overlap(int startHour, int finishHour) {
		return startHour < FINISH_HOUR && finishHour >= START_HOUR;
	}
}
