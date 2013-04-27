package com.mbl111.ld26.world.tile;

import com.mbl111.ld26.screen.Art;
import com.mbl111.ld26.screen.Screen;

public class GrassTile extends Tile {

	public GrassTile(int id) {
		super(id);
	}

	public void update(int x, int y) {

	}

	public void tick(int x, int y) {

	}

	public void render(Screen screen, int x, int y) {
		screen.draw(Art.TILES[0][0], x * Tile.WIDTH, y * Tile.HEIGHT);
	}

}
