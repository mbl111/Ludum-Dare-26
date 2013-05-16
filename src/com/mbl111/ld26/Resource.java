package com.mbl111.ld26;

import com.mbl111.ld26.world.tile.BuildingTile;
import com.mbl111.ld26.world.tile.Tile;
import com.mbl111.ld26.world.tile.WindmillTile;

public enum Resource {
	FOOD(Tile.MILLTILE, 5), NONE(null, 0), WOOD(Tile.LOGGERTILE, 2), ;

	private Resource(Tile clazz, int maxBuild) {
		this.deposit = clazz;
		this.maxBuild = maxBuild;
	}

	public Tile deposit;
	public int maxBuild = 0;
}
