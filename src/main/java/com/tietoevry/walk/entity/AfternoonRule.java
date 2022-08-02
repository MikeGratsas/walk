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

	public AfternoonRule(String name) {
		super(name);
	}

	@Override
	protected boolean overlap(int startHour, int finishHour) {
		return startHour < FINISH_HOUR && finishHour >= START_HOUR;
	}
}
