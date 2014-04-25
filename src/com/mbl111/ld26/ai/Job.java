package com.mbl111.ld26.ai;

import com.mbl111.ld26.entity.Unit;

public abstract class Job {

	protected Unit unit;
	public Job subJob = null;

	public void init(Unit unit) {
		this.unit = unit;
	}

	public void tick() {
		if (subJob != null) {
			subJob.tick();
			return;
		}
	}

	public boolean finished() {
		return true;
	}

}
