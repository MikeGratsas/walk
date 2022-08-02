package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("spring")
public class SpringRule extends SeasonRule {

	private static final int START_MONTH = 6;
	private static final int FINISH_MONTH = 9;

	public SpringRule() {
	}

    public SpringRule(String name) {
        super(name);
    }

	@Override
	protected boolean overlap(int startMonth, int finishMonth) {
		return startMonth < FINISH_MONTH && finishMonth >= START_MONTH;
	}
}
