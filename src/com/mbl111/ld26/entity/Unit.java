package com.mbl111.ld26.entity;

import com.mbl111.ld26.Game;
import com.mbl111.ld26.ai.IdleJob;
import com.mbl111.ld26.ai.Job;
import com.mbl111.ld26.screen.Art;
import com.mbl111.ld26.screen.PlayerView;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;

public class Unit extends Entity {

	public int color = 0xFFFFFFFF;
	private boolean selected;
	private Job job;
	public int speedMod = 2;
	public int life = 0;

	public Unit(int i) {
		super();
		x = i * 20;
		xr = 3;
		yr = 6;
		job = new IdleJob();
	}

	public void tick() {
		life++;
		job.tick();
		if (job.finished()) {
			job = new IdleJob();
		}
	}

	@Override
	public void render(Screen screen) {
		if (!walking) {
			screen.draw(Art.UNIT[dir * 3][0], x - this.xr, y - this.yr);
		} else {
			int part = (int) (walkDist % 16) / 4;
			int sub = 0;
			if (part == 0)
				sub = 0;
			if (part == 1)
				sub = 1;
			if (part == 2)
				sub = 0;
			if (part == 3)
				sub = 2;
			screen.draw(Art.UNIT[(dir * 3) + sub][0], x - this.xr, y - this.yr);
		}
		if (selected)
			screen.fill(this.x, y - this.yr - 2, 1, 1, 0xFFFF0000);
	}

	public void init(World world, int lastEntityId) {
		PlayerView pv = Game.instance.getPlayerView();
		pv.addUnit(this, lastEntityId);
		super.init(world, lastEntityId);
	}

	public void unSelect() {
		this.selected = false;
	}

	public void select() {
		this.selected = true;
	}

	public void setJob(Job job) {
		this.job = job;
		this.job.init(this);
	}

}
