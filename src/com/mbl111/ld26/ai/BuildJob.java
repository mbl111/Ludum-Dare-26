package com.mbl111.ld26.ai;

import com.mbl111.ld26.Game;
import com.mbl111.ld26.util.MathHelper;
import com.mbl111.ld26.world.tile.BuildingTile;
import com.mbl111.ld26.world.tile.Tile;

public class BuildJob extends Job {

	public int tileX, tileY;

	public BuildJob(int x, int y) {
		this.tileX = x;
		this.tileY = y;
	}

	@Override
	public void tick() {
		super.tick();
		
		if (MathHelper.distance(unit.x, unit.y, (tileX * Tile.WIDTH) + (Tile.WIDTH / 2), (tileY * Tile.HEIGHT) + (Tile.HEIGHT / 2)) > 16 + 5) {
			if (subJob == null) {
				this.subJob = new MoveJob((tileX * Tile.WIDTH) + (Tile.WIDTH / 2), (tileY * Tile.HEIGHT) + (Tile.HEIGHT / 2));
				this.subJob.init(unit);
			}
		} else {
			subJob = null;
		}

		boolean build = Game.instance.tickCount % 120 / 2 == 0;
		int buildPercent = (Game.instance.getWorld().getData(tileX, tileY) & 0xFF) + 1;
		if (build && buildPercent < 100) {
			buildPercent++;
			Game.instance.getWorld().setData(tileX, tileY, buildPercent);
			if (buildPercent == 100) {
				Tile t = Game.instance.getWorld().getTile(tileX, tileY);
				if (t instanceof BuildingTile) {
					((BuildingTile) t).onBuildingFinished(Game.instance.getWorld(), tileX, tileY);
				}
			}
		}
	}

	@Override
	public boolean finished() {
		return Game.instance.getWorld().getData(tileX, tileY) == 100;
	}

}
