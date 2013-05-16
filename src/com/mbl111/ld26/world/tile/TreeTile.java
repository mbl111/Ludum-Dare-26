package com.mbl111.ld26.world.tile;

import java.util.ArrayList;
import java.util.List;

import com.mbl111.ld26.Game;
import com.mbl111.ld26.Resource;
import com.mbl111.ld26.ai.FarmJob;
import com.mbl111.ld26.entity.Entity;
import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.screen.Art;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.util.MathHelper;
import com.mbl111.ld26.world.World;

public class TreeTile extends UnitInteractableTile {

	public TreeTile(int id) {
		super(id);
	}

	public void update(World world, int x, int y) {

	}

	public void tick(World world, int x, int y) {

	}

	public void render(Screen screen, World world, int x, int y) {
		int data = world.getData(x, y);
		int pstat = (data & 0xF);
		if (pstat > 2)
			pstat = 2;
		int wheat = (data & 0xFF0) >> 4;

		screen.draw(Art.TILES[0][0], x * Tile.WIDTH, y * Tile.HEIGHT);
		screen.draw(Art.TILES[4 + pstat + (wheat / 20)][0], (x * Tile.WIDTH), (y * Tile.HEIGHT));
	}

	@Override
	public boolean canPass(Entity entity) {
		return false;
	}

	@Override
	public List<String> getHudInfo(int data) {
		List<String> s = new ArrayList<String>();
		s.add("Forest");
		int pstat = (data & 0xF);
		int wheat = (data & 0xFF0) >> 4;
		if (pstat == 0) {
			s.add("Planting");
		} else if (pstat < 2) {
			s.add("Growing");
		} else {
			s.add("Felling");
			s.add("Logs(" + (100 - wheat) + ")");
		}
		return s;
	}

	public boolean giveUnitAction(Unit u, World world, int x, int y) {
		u.setJob(new FarmJob(x, y, Resource.WOOD));
		return true;
	}

}
