package com.tietoevry.walk.entity;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.MappedSuperclass;

import com.tietoevry.walk.form.WalkModel;

@MappedSuperclass
public abstract class DayTimeRule extends DistanceRule {
	
	private static final long HOURS_PER_DAY = 24;

	private boolean toEvery;

	public DayTimeRule() {
	}

	public DayTimeRule(final String name) {
		super(name);
	}

	public boolean isToEvery() {
		return toEvery;
	}

	public void setToEvery(final boolean toEvery) {
		this.toEvery = toEvery;
	}

	@Override
	public long check(final WalkModel walk) {
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
		long days = start.until(finish, ChronoUnit.DAYS);
		int startHour = start.getHour();
		int finishHour = finish.getHour();
		if (startHour > finishHour) {
			startHour -= HOURS_PER_DAY;
		}
		if (overlap(startHour, finishHour))
			days++;
		if (days != 0) {
			return toEvery? days * super.check(walk): super.check(walk);
		}
		return 0;
	}

	protected abstract boolean overlap(int startHour, int finishHour);
}
