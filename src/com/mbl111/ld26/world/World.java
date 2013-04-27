package com.mbl111.ld26.world;

import java.util.Arrays;

import com.mbl111.ld26.world.tile.Tile;

public class World {

	public int[] tiles;
	public byte[] data;
	public int width, height;

	public World(int w, int h) {
		this.width = w;
		this.height = h;
		this.tiles = new int[w * h];
		this.data = new byte[w * h];
		Arrays.fill(tiles, 1);
	}

	private void tick() {

	}

	public void setTile(int id, int x, int y, boolean update) {
		if (x >= width || x < 0 || y >= height || y < 0)
			return;
		tiles[x + y * width] = id;
		Tile.getById(id).update(x, y);
		if (update) {
			getTile(x + 1, y).update(x + 1, y);
			getTile(x - 1, y).update(x - 1, y);
			getTile(x, y + 1).update(x, y + 1);
			getTile(x, y - 1).update(x, y - 1);
		}
	}

	private int getTileId(int x, int y) {
		if (x >= width || x < 0 || y >= height || y < 0)
			return 0;

		return tiles[x + y * width];
	}

	public Tile getTile(int x, int y) {
		return Tile.getById(getTileId(x, y));
	}
}
