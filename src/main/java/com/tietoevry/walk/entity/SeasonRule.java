package com.tietoevry.walk.entity;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import com.tietoevry.walk.form.WalkModel;

public abstract class SeasonRule extends DistanceRule {
	
	private static final long MONTHS_PER_YEAR = 12;

	private boolean toEvery;

	public SeasonRule() {
	}

	public SeasonRule(String name) {
		super(name);
	}

	public boolean isToEvery() {
		return toEvery;
	}

	public void setToEvery(boolean toEvery) {
		this.toEvery = toEvery;
	}

	@Override
	public long check(WalkModel walk) {
		if (walk == null)
			return 0;
		final LocalDateTime start = walk.getStart();
		final LocalDateTime finish = walk.getFinish();
		if (start == null)
			throw new DateTimeException("Start time is required");
		if (finish == null)
			throw new DateTimeException("Finish time is required");
		if (finish.isBefore(start))
			throw new DateTimeException("Finish time must not be earlier than start time");
		long years = start.until(finish, ChronoUnit.YEARS);
		int startMonth = start.getMonthValue();
		int finishMonth = finish.getMonthValue();
		if (startMonth > finishMonth) {
			startMonth -= MONTHS_PER_YEAR;
		}
		if (overlap(startMonth, finishMonth))
			years++;
		if (years != 0) {
			return toEvery? years * super.check(walk): super.check(walk);
		}
		return 0;
	}

	protected abstract boolean overlap(int startMonth, int finishMonth);
}
