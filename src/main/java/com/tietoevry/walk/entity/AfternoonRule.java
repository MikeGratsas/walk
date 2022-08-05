package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("afternoon")
public class AfternoonRule extends DayTimeRule {
	
	private static final int START_HOUR = 12;
	private static final int FINISH_HOUR = 18;

	public AfternoonRule() {
	}

	public AfternoonRule(final String name) {
		super(name);
	}

	@Override
	protected boolean overlap(final int startHour, final int finishHour) {
		return startHour < FINISH_HOUR && finishHour >= START_HOUR;
	}
}
