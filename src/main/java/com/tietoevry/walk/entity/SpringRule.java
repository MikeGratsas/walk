package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("spring")
public class SpringRule extends SeasonRule {

	private static final int START_MONTH = 3;
	private static final int FINISH_MONTH = 6;

	public SpringRule() {
	}

    public SpringRule(final String name) {
        super(name);
    }

	@Override
	protected boolean overlap(final int startMonth, final int finishMonth) {
		return startMonth < FINISH_MONTH && finishMonth >= START_MONTH;
	}
}
