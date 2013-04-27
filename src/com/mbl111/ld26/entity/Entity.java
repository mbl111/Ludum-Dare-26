package com.mbl111.ld26.entity;

import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;
import com.mbl111.ld26.world.tile.Tile;
import com.mbl111.ld26.world.tile.util.SyncedRandom;

public class Entity {

	public int x, y, xr, yr;
	public World world;
	public boolean removed = false;

	public Entity() {
		x = 10 + SyncedRandom.r.nextInt(5) * 4;
		y = 10;
		xr = yr = 5;
	}

	public void teleport(int x, int y) {

	}

	public void moveTo(int xa, int ya) {
		int steps = (int) (Math.sqrt(xa * xa + ya * ya) + 1);

		for (int i = 0; i < steps; i++) {
			movePart(xa / steps, 0);
			movePart(0, ya / steps);
		}
	}

	public void move(int xa, int ya) {
		movePart(xa, 0);
		movePart(0, ya);
	}

	private boolean movePart(int x, int y) {
		if (x != 0 && y != 0)
			return false;

		int yrc = (y < 0 ? -this.yr : this.yr);
		int xrc = (x < 0 ? -this.xr : this.xr);

		if (world.getTile((x + this.x + xrc) / Tile.WIDTH, (y + this.y + yrc) / Tile.HEIGHT).canPass(this)) {
			this.x += x;
			this.y += y;
		}

		return true;
	}

	public void remove() {
		this.removed = false;
	}

	public void init(World world) {
		this.world = world;
	}

	public void tick() {
	}

	public void render(Screen screen) {
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return !(x + xr < x0 || y + yr < y0 || x - xr > x1 || y - yr > y1);
	}
}
