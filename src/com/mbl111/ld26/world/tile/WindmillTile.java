package com.mbl111.ld26.world.tile;

import java.util.ArrayList;
import java.util.List;

import com.mbl111.ld26.Game;
import com.mbl111.ld26.entity.Entity;
import com.mbl111.ld26.screen.Art;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;

public class WindmillTile extends BuildingTile {

	public WindmillTile(int id) {
		super(id);
	}

	public void update(World world, int x, int y) {

	}

	public void tick(World world, int x, int y) {

	}

	public void render(Screen screen, World world, int x, int y) {
		if ((world.getData(x, y) & 100) == 100) {
			int ticks = Game.instance.tickCount;
			int step = (ticks) % 60 / 30;
			screen.draw(Art.TILES[0][0], x * Tile.WIDTH, y * Tile.HEIGHT);
			screen.draw(Art.BUILDINGS[step][1], (x * Tile.WIDTH), (y * Tile.HEIGHT));
		} else {
			screen.draw(Art.TILES[0][0], x * Tile.WIDTH, y * Tile.HEIGHT);
			screen.draw(Art.BUILDINGS[BuildState.getState(world.getData(x, y)).value][3], (x * Tile.WIDTH), (y * Tile.HEIGHT));
		}
	}

	@Override
	public void postEntityRender(Screen screen, World world, int x, int y) {
		if ((world.getData(x, y) & 0xFF) == 100) {
			int ticks = Game.instance.tickCount;
			int step = (ticks) % 60 / 30;
			screen.draw(Art.BUILDINGS[step][1], (x * Tile.WIDTH), (y * Tile.HEIGHT));
			screen.draw(Art.BUILDINGS[step][0], (x * Tile.WIDTH), (y * Tile.HEIGHT) - Tile.HEIGHT);
		} else {
			screen.draw(Art.BUILDINGS[BuildState.getState(world.getData(x, y) & 0xFF).value][3], (x * Tile.WIDTH), (y * Tile.HEIGHT));
		}
	}

	@Override
	public void onBuildingFinished(World world, int x, int y) {
		world.setTile(Tile.WHEATTILE.id, x - 1, y - 1, true);
		world.setTile(Tile.WHEATTILE.id, x - 1, y, true);
		world.setTile(Tile.WHEATTILE.id, x - 1, y + 1, true);
		world.setTile(Tile.WHEATTILE.id, x, y - 1, true);
		world.setTile(Tile.WHEATTILE.id, x, y + 1, true);
		world.setTile(Tile.WHEATTILE.id, x + 1, y - 1, true);
		world.setTile(Tile.WHEATTILE.id, x + 1, y, true);
		world.setTile(Tile.WHEATTILE.id, x + 1, y + 1, true);
	}

	@Override
	public boolean canPass(Entity entity) {
		return false;
	}

	@Override
	public List<String> getHudInfo(int data) {
		List<String> lines = new ArrayList<String>();
		lines.add("Windmill");
		if ((data & 0xFF) < 100) {
			lines.add("Progress(" + (data & 0xFF) + "%)");
		}
		return lines;
	}

}