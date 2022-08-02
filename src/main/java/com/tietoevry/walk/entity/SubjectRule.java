package com.tietoevry.walk.entity;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.tietoevry.walk.form.WalkModel;

@Entity
@DiscriminatorValue("subject")
public class SubjectRule extends Rule {

	@ManyToOne
	private Subject subject;
	private Long subjectCount;

	public SubjectRule() {
	}

    public SubjectRule(String name) {
        super(name);
    }

    public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public Long getSubjectCount() {
		return subjectCount;
	}

	public void setSubjectCount(Long subjectCount) {
		this.subjectCount = subjectCount;
	}

	@Override
	public long check(WalkModel walk) {
		if (walk == null)
			return 0;
		final Map<String, Long> subjects = walk.getSubjects();
		final Long count = subjects.get(subject.getName());
		if (count == null)
			return 0;
		if (subjectCount != null && subjectCount != 0)
			return (count + subjectCount - 1) / subjectCount;
		return 1;
	}
}
