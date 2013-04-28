package com.mbl111.ld26.world.tile;

import com.mbl111.ld26.Game;
import com.mbl111.ld26.entity.Entity;
import com.mbl111.ld26.screen.Art;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.util.MathHelper;

public class WheatTile extends Tile {

	public WheatTile(int id) {
		super(id);
	}

	public void update(int x, int y) {

	}

	public void tick(int x, int y) {

	}

	public void render(Screen screen, int x, int y) {
		int ticks = Game.instance.tickCount;
		int step = (ticks) % 500 / 100;
		screen.draw(Art.TILES[0][0], x * Tile.WIDTH, y * Tile.HEIGHT);
		screen.draw(Art.TILES[4 + step][1], (x * Tile.WIDTH), (y * Tile.HEIGHT));
	}

	@Override
	public boolean canPass(Entity entity) {
		return true;
	}

}
