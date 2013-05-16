package com.mbl111.ld26.world.tile;

import java.util.ArrayList;
import java.util.List;

import com.mbl111.ld26.Resource;
import com.mbl111.ld26.ai.FarmJob;
import com.mbl111.ld26.entity.Entity;
import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.screen.Art;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;

public class WheatTile extends UnitInteractableTile {

	public WheatTile(int id) {
		super(id);
	}

	public void update(World world, int x, int y) {

	}

	public void tick(World world, int x, int y) {

	}

	@Override
	public void onPlace(World world, int x, int y) {
		world.setData(x, y, 0);
	}

	@Override
	public boolean giveUnitAction(Unit u, World world, int x, int y) {
		u.setJob(new FarmJob(x, y, Resource.FOOD));
		return true;
	}

	public void render(Screen screen, World world, int x, int y) {
		screen.draw(Art.TILES[0][0], x * Tile.WIDTH, y * Tile.HEIGHT);
		screen.draw(Art.TILES[4 + (world.getData(x, y) & 0xF)][1], (x * Tile.WIDTH), (y * Tile.HEIGHT));
	}

	@Override
	public boolean canPass(Entity entity) {
		return true;
	}
	
	@Override
	public List<String> getHudInfo(int data) {
		List<String> s = new ArrayList<String>();
		s.add("Farm");
		int pstat = (data & 0xF);
		int wheat = (data & 0xFF0) >> 4;
		if (pstat == 0){
			s.add("Not Seeded");
		}else if (pstat < 5){
			s.add("Growing");
		}else{
			s.add("Harvesting");
			s.add("Wheat("+(100-wheat)+")");
		}
		return s;
	}

}
