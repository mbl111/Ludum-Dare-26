package com.mbl111.ld26.entity;

import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;
import com.mbl111.ld26.world.tile.Tile;

public class Entity {

	public int id = -1;
	public int x, y, xr, yr, ox, oy;
	public World world;
	public boolean removed = false;
	public int dir = 0;
	protected double walkDist = 0;
	public boolean walking = false;

	public Entity() {
		x = ox = 10;
		y = oy = 10;
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
		if (xa != 0 || ya != 0) {
			boolean stopped = true;
			if (xa != 0 && movePart(xa, 0))
				stopped = false;
			if (ya != 0 && movePart(0, ya))
				stopped = false;
			walkDist += Math.sqrt(xa * xa + ya * ya);
			walking = !stopped;
			if (ya > 0)
				dir = 0;
			if (ya < 0)
				dir = 2;
			if (xa > 0)
				dir = 1;
			if (xa < 0)
				dir = 3;
		} else {
			walking = false;
		}
	}

	private boolean movePart(int x, int y) {
		if (x != 0 && y != 0)
			return false;
		if (x == 0 && y == 0) {
			return false;
		}

		int yrc = (y < 0 ? -this.yr : this.yr);
		int xrc = (x < 0 ? -this.xr : this.xr);

		if (world.getTile((x + this.x + xrc) / Tile.WIDTH, (y + this.y + yrc) / Tile.HEIGHT).canPass(this)) {
			this.ox = this.x;
			this.oy = this.y;
			this.x += x;
			this.y += y;
			return true;
		}

		return false;
	}

	public void remove() {
		this.removed = false;
	}

	public void init(World world, int lastEntityId) {
		this.world = world;
		this.id = lastEntityId;
	}

	public void tick() {
	}

	public void render(Screen screen) {
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return !(x + xr < x0 || y + yr < y0 || x - xr > x1 || y - yr > y1);
	}
}
