package com.mbl111.ld26.world.tile;

import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;

public class UnitInteractableTile extends Tile {

	public UnitInteractableTile(int id) {
		super(id);
	}

	public boolean giveUnitAction(Unit u, World world, int x, int y) {
		return false;
	}

	@Override
	public void update(World world, int x, int y) {

	}

	@Override
	public void tick(World world, int x, int y) {

	}

	@Override
	public void render(Screen screen, World world, int x, int y) {

	}

}
