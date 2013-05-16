package com.mbl111.ld26.world.tile;

import java.util.ArrayList;
import java.util.List;

import com.mbl111.ld26.entity.Entity;
import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.screen.Screen;
import com.mbl111.ld26.world.World;

public abstract class Tile {

	public int id;

	public Tile(int id) {
		if (tiles[id] != null)
			System.err.println("Tile " + id + " already exists");
		tiles[id] = this;
		this.id = id;
	}

	public abstract void update(World world, int x, int y);

	public abstract void tick(World world, int x, int y);

	public abstract void render(Screen screen, World world, int x, int y);

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
	public static Tile TREETILE = new TreeTile(2);
	public static Tile WHEATTILE = new WheatTile(3);
	public static Tile MILLTILE = new WindmillTile(4);
	public static Tile LOGGERTILE = new LoggerHutTile(5);

	public boolean canPass(Entity entity) {
		return true;
	}

	public void postEntityRender(Screen screen, World world, int x, int y) {
	}

	public void onPlace(World world, int x, int y) {
	}

	public List<String> getHudInfo(int data) {
		return new ArrayList<String>();
	}

}
