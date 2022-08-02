package com.tietoevry.walk.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("summer")
public class SummerRule extends SeasonRule {

	private static final int START_MONTH = 6;
	private static final int FINISH_MONTH = 9;

	public SummerRule() {
	}

    public SummerRule(String name) {
        super(name);
    }

	@Override
	protected boolean overlap(int startMonth, int finishMonth) {
		return startMonth < FINISH_MONTH && finishMonth >= START_MONTH;
	}
}
