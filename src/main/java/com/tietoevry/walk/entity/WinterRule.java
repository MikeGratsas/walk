package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("winter")
public class WinterRule extends SeasonRule {
	
	private static final long START_MONTH = 0;
	private static final long FINISH_MONTH = 3;

	public WinterRule() {
	}

	public WinterRule(String name) {
		super(name);
	}

	@Override
	protected boolean overlap(int startMonth, int finishMonth) {
		return startMonth < FINISH_MONTH && finishMonth >= START_MONTH;
	}
}
