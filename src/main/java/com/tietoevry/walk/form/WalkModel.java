package com.tietoevry.walk.form;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import com.tietoevry.walk.validator.LocalDateTimeRange;

@LocalDateTimeRange
public class WalkModel {

	@NotEmpty
	private Map<String, Long> subjects = new HashMap<>();
    @Positive
    private Double distance;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime start;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime finish;
    
	public Map<String, Long> getSubjects() {
		return subjects;
	}
	
	public void setSubjects(final Map<String, Long> subjects) {
		this.subjects = subjects;
	}
	
	public Double getDistance() {
		return distance;
	}
	
	public void setDistance(final Double distance) {
		this.distance = distance;
	}
	
	public LocalDateTime getStart() {
		return start;
	}
	
	public void setStart(final LocalDateTime start) {
		this.start = start;
	}
	
	public LocalDateTime getFinish() {
		return finish;
	}
	
	public void setFinish(final LocalDateTime finish) {
		this.finish = finish;
	}
}
