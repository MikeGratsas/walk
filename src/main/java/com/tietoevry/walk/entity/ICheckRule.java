package com.tietoevry.walk.entity;

import com.tietoevry.walk.form.WalkModel;

public interface ICheckRule {
	long check(WalkModel walk);
}
