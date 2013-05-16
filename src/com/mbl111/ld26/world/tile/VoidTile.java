package com.mbl111.ld26.world.tile;

import com.mbl111.ld26.entity.Entity;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;

public class VoidTile extends Tile {

	public VoidTile(int id) {
		super(id);
	}

	public void update(World world, int x, int y) {
	}

	public void tick(World world, int x, int y) {
	}

	public void render(Screen screen,World world, int x, int y) {
		screen.fill(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT, 0xFF000000);
	}

	@Override
	public boolean canPass(Entity entity) {
		return false;
	}

}
