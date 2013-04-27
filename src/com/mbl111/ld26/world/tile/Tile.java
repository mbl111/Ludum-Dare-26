package com.mbl111.ld26.world.tile;

import com.mbl111.ld26.screen.Screen;

public abstract class Tile {

	public Tile(int id) {
		if (tiles[id] != null) System.err.println("Tile " + id + " already exists");
		tiles[id] = this;
	}

	public abstract void update(int x, int y);

	public abstract void tick(int x, int y);

	public abstract void render(Screen screen, int x, int y);

	private static final int maxTiles = 256;
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	private static Tile[] tiles = new Tile[maxTiles];

	public static Tile getById(int tileId) {
		if (tileId < 0 || tileId >= maxTiles) {
			return Tile.VOIDTILE;
		}

		if (tiles[tileId] == null) {
			return Tile.VOIDTILE;
		}

		return tiles[tileId];

	}

	public static Tile VOIDTILE = new VoidTile(0);
	public static Tile GRASSTILE = new GrassTile(1);

}
