package com.mbl111.ld26.ai;

import com.mbl111.ld26.world.tile.util.MathHelper;

public class MoveJob extends Job {

	public int tx, ty;

	public MoveJob(int toX, int toY) {
		this.tx = toX;
		this.ty = toY;
	}

	@Override
	public void tick() {
		if (unit.life % unit.speedMod == 0) {
			int xd = tx - unit.x;
			int yd = ty - unit.y;
			unit.move(MathHelper.clamp(xd, 1), MathHelper.clamp(yd, 1));
		}
	}

	@Override
	public boolean finished() {
		if (MathHelper.distance(tx, ty, unit.x, unit.y) <= 2) {
			unit.walking = false;
			return true;
		}
		return false;
	}

}
