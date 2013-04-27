package com.mbl111.ld26.ai;

import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.util.MathHelper;

public class FollowJob extends Job {

	private Unit follow;

	public FollowJob(Unit unit) {
		this.follow = unit;
	}

	@Override
	public void tick() {
		if (follow != null) {
			if (unit.life % unit.speedMod == 0) {
				double dist = MathHelper.distance(follow.x, follow.y, unit.x, unit.y);
				if (dist > 20) {
					int xd = follow.x - unit.x;
					int yd = follow.y - unit.y;
					unit.move(MathHelper.clamp(xd, 1), MathHelper.clamp(yd, 1));
				}
			}
		}
	}

	@Override
	public boolean finished() {
		return follow == null;
	}

}
