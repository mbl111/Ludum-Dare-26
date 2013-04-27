package com.mbl111.ld26.entity;

import com.mbl111.ld26.screen.Screen;

public class Unit extends Entity {
	
	public int color = 0xFFFFFFFF;

	public void tick() {
	}

	@Override
	public void render(Screen screen) {
		screen.fill(x - this.xr, y - this.yr, this.xr, this.yr, color);
	}

}
