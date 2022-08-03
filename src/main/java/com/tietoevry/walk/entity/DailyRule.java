package com.tietoevry.walk.entity;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import com.tietoevry.walk.form.WalkModel;

@Entity
@DiscriminatorValue("daily")
public class DailyRule extends SubjectRule {
	
	private static final long HOURS_PER_DAY = 24;

	public DailyRule() {
	}

	public DailyRule(String name) {
		super(name);
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
		long days = start.until(finish, ChronoUnit.DAYS);
		int startHour = start.getHour();
		int finishHour = finish.getHour();
		if (startHour > finishHour) {
			startHour -= HOURS_PER_DAY;
		}
		if (finishHour - startHour > HOURS_PER_DAY / 4)
			days++;
		if (days != 0) {
			return days * super.check(walk);
		}
		return 0;
	}
}
