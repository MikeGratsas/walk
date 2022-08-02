package com.tietoevry.walk.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.tietoevry.walk.form.WalkModel;

@Entity
@DiscriminatorValue("distance")
public class DistanceRule extends SubjectRule {

    @Column(precision = 13, scale = 2)
	private Double distance;

	public DistanceRule() {
	}

    public DistanceRule(String name) {
        super(name);
    }

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Override
	public long check(WalkModel walk) {
		if (walk == null)
			return 0;
		final Double walkDistance = walk.getDistance();
		if (walkDistance != null) {
			if (distance == null || distance > walkDistance)
				return 0;
		}
		return super.check(walk);
	}
}
