package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("autumn")
public class AutumnRule extends SeasonRule {

	private static final int START_MONTH = 9;
	private static final int FINISH_MONTH = 12;

	public AutumnRule() {
	}

    public AutumnRule(final String name) {
        super(name);
    }

	@Override
	protected boolean overlap(final int startMonth, final int finishMonth) {
		return startMonth < FINISH_MONTH && finishMonth >= START_MONTH;
	}
}
