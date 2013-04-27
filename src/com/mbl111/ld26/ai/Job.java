package com.mbl111.ld26.ai;

import com.mbl111.ld26.entity.Unit;

public abstract class Job {

	protected Unit unit;

	public void init(Unit unit) {
		this.unit = unit;
	}

	public void tick() {
	}

	public boolean finished() {
		return true;
	}

}
