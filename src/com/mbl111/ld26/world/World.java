package com.mbl111.ld26.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mbl111.ld26.entity.Entity;
import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.world.tile.Tile;

public class World {

	public int[] tiles;
	public byte[] data;
	public int width, height;
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Entity>[] entitiesInTiles;
	public int lastEntityId = 0;

	public World(int w, int h) {
		this.width = w;
		this.height = h;
		this.tiles = new int[w * h];
		this.data = new byte[w * h];
		entitiesInTiles = new ArrayList[w * h];
		for (int i = 0; i < entitiesInTiles.length; i++) {
			entitiesInTiles[i] = new ArrayList<Entity>();
		}
		Arrays.fill(tiles, 1);

		tiles[2 + 2 * width] = 2;
		data[2 + 2 * width] = 4;

		for (int i = 0; i < 5; i++) {
			add(new Unit(i));
		}

	}

	public void tick() {
		for (Entity e : entities) {
			int xo = e.x;
			int yo = e.y;
			e.tick();
			if (e.removed) {
				remove(e);
				continue;
			}
			int xn = e.x;
			int yn = e.y;
			if (xn != xo || yn != yo) {
				removeEntity(xo / Tile.WIDTH, yo / Tile.HEIGHT, e);
				insertEntity(xn / Tile.WIDTH, yn / Tile.HEIGHT, e);
			}

		}
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

	public void add(Entity entity) {
		entities.add(entity);
		entity.init(this, lastEntityId++);
		insertEntity(entity.x / Tile.WIDTH, entity.y / Tile.HEIGHT, entity);

	}

	public void remove(Entity entity) {
		entities.remove(entity);
		removeEntity(entity.x / Tile.WIDTH, entity.y / Tile.HEIGHT, entity);

	}

	private void insertEntity(int xt, int yt, Entity e) {
		if (xt < 0 || yt < 0 || xt >= width || yt >= height)
			return;
		entitiesInTiles[xt + yt * width].add(e);

	}

	private void removeEntity(int xt, int yt, Entity e) {
		if (xt < 0 || yt < 0 || xt >= width || yt >= height)
			return;
		entitiesInTiles[xt + yt * width].remove(e);
	}

	public List<Entity> getEntities(int x0, int y0, int x1, int y1) {
		List<Entity> result = new ArrayList<Entity>();
		int xt0 = x0 / Tile.WIDTH;
		int xt1 = x1 / Tile.WIDTH;
		int yt0 = y0 / Tile.HEIGHT;
		int yt1 = y1 / Tile.HEIGHT;
		for (int y = yt0; y <= yt1; y++) {
			for (int x = xt0; x <= xt1; x++) {
				if (x < 0 || y < 0 || x >= width || y >= height)
					continue;
				List<Entity> entities = entitiesInTiles[x + y * this.width];
				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					if (e.intersects(x0, y0, x1, y1))
						result.add(e);
				}
			}
		}
		return result;
	}

	public List<Entity> getTileEntities(int txo, int tyo, int tww, int thh) {
		List<Entity> result = new ArrayList<Entity>();
		for (int y = tyo; y <= thh + tyo; y++) {
			for (int x = txo; x <= tww + txo; x++) {
				if (x < 0 || y < 0 || x >= width || y >= height)
					continue;
				result.addAll(entitiesInTiles[x + y * width]);
			}
		}
		return result;
	}

	public int getData(int x, int y) {
		if (x >= width || x < 0 || y >= height || y < 0)
			return 0;

		return data[x + y * width];
	}
}
