package com.mbl111.ld26.world.tile;

import com.mbl111.ld26.screen.Art;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;

public class GrassTile extends Tile {

	public GrassTile(int id) {
		super(id);
	}

	public void update(World world, int x, int y) {

	}

	public void tick(World world, int x, int y) {

	}

	public void render(Screen screen,World world, int x, int y) {
		screen.draw(Art.TILES[0][0], x * Tile.WIDTH, y * Tile.HEIGHT);
	}

}
