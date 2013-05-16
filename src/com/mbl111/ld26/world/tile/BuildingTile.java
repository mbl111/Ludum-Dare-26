package com.mbl111.ld26.world.tile;

import com.mbl111.ld26.ai.BuildJob;
import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;

public class BuildingTile extends UnitInteractableTile {

	public static enum BuildState {
		ONE(0x0), TWO(0x1), THREE(0x2), BUILT(0x3);

		BuildState(int value) {
			this.value = (byte) value;
		}

		public byte value;

		public static BuildState getState(int buildPercent) {
			if (buildPercent >= 0 && buildPercent < (100 / 3)) {
				return ONE;
			} else if (buildPercent >= 100 / 3 && buildPercent < (100 / 3) * 2) {
				return TWO;
			} else if (buildPercent == 100) {
				return BUILT;
			} else {
				return THREE;
			}
		}
	}

	public BuildingTile(int id) {
		super(id);
	}

	@Override
	public void update(World world, int x, int y) {
	}

	public void parentBuildingTick(World world, int x, int y, int px, int py) {
	}

	@Override
	public void tick(World world, int x, int y) {
	}

	@Override
	public void render(Screen screen, World world, int x, int y) {
	}

	public void onBuildingFinished(World world, int x, int y) {
	}

	public boolean giveUnitPostBuildAction(Unit u, World world, int x, int y) {
		return false;
	}

	public boolean giveUnitAction(Unit u, World world, int x, int y) {
		if ((world.getData(x, y) & 0xFF) < 100) {
			u.setJob(new BuildJob(x, y));
			return true;
		} else {
			return giveUnitPostBuildAction(u, world, x, y);
		}
	}

	@Override
	public void onPlace(World world, int x, int y) {
		world.setData(x, y, 0);
	}

}
