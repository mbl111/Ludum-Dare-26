package com.mbl111.ld26.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import com.mbl111.ld26.Game;
import com.mbl111.ld26.ai.FollowJob;
import com.mbl111.ld26.ai.MoveJob;
import com.mbl111.ld26.entity.Entity;
import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.screen.Message.MessageType;
import com.mbl111.ld26.world.World;
import com.mbl111.ld26.world.tile.Tile;

public class PlayerView {

	public int scrollX = 0;
	public int scrollY = 0;
	public int width, height;
	public int selectedBuilding = -1;
	public int selectedUnit = -1;
	public List<Message> messages = new ArrayList<Message>();
	private TreeMap<Integer, Unit> units = new TreeMap<Integer, Unit>();

	public PlayerView(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void tick() {

		Game.instance.getWorld().tick();

		for (int i = 0; i < messages.size(); i++) {
			Message m = messages.get(i);
			m.tick();
			if (m.isDead()) {
				messages.remove(m);
				i--;
			}
		}

		int mx = Game.instance.getInput().x;
		int my = Game.instance.getInput().y;

		if (Game.instance.getInput().b0Clicked) {
			int u = getNearestUnit(mx + scrollX, my + scrollY);
			if (u != -1) {
				if (selectedUnit != -1) {
					units.get(selectedUnit).unSelect();
				}
				units.get(u).select();
				selectedUnit = u;
			}

		}

		if (Game.instance.getInput().b1Clicked) {
			if (selectedUnit > -1) {
				Unit u = units.get(selectedUnit);
				if (u != null) {
					u.setJob(new MoveJob(mx + scrollX, my + scrollY));
					messages.add(new Message("Moving Unit(" + u.id + ") to (" + (mx + scrollX) + "," + (my + scrollY) + ")", MessageType.INFO));
				}
			}
		}

		if (Game.instance.getInput().b2Clicked) {
			if (selectedUnit > -1) {
				Unit u = units.get(selectedUnit);
				if (u != null) {
					u.setJob(new FollowJob(units.get(getNearestUnit(mx + scrollX, my + scrollY))));
					messages.add(new Message("Following!", MessageType.INFO));
				}
			}
		}

		if (Game.instance.getInput().up.down)
			scrollY -= 2;
		if (Game.instance.getInput().down.down)
			scrollY += 2;
		if (Game.instance.getInput().left.down)
			scrollX -= 2;
		if (Game.instance.getInput().right.down)
			scrollX += 2;

		if (scrollX < 0)
			scrollX = 0;
		if (scrollX > Game.instance.getWorld().width * Tile.WIDTH - width)
			scrollX = Game.instance.getWorld().width * Tile.WIDTH - width;
		if (scrollY < 0)
			scrollY = 0;
		if (scrollY > Game.instance.getWorld().height * Tile.HEIGHT - height)
			scrollY = Game.instance.getWorld().height * Tile.HEIGHT - height;

	}

	// private Comparator<Entity> unitDistFromMouse = new Comparator<Entity>() {
	//
	// public int compare(Entity o1, Entity o2) {
	//
	// int mx = Game.instance.getInput().x;
	// int my = Game.instance.getInput().y;
	// double d1 = Math.sqrt((mx - o1.x) * (mx - o1.x) + (my - o1.y) * (my -
	// o1.y));
	// double d2 = Math.sqrt((mx - o2.x) * (mx - o2.x) + (my - o2.y) * (my -
	// o2.y));
	//
	// return (int) (d1 - d2);
	//
	// }
	// };

	private int getNearestUnit(int mx, int my) {
		List<Entity> search = new ArrayList<Entity>();
		World w = Game.instance.getWorld();
		search.addAll(w.getEntities(mx - 4, my - 4, mx + 4, my + 4));
		if (search.size() > 0) {
			double bestDist = -1;
			int toSelect = -1;
			for (Entity e : search) {
				if (e instanceof Unit) {
					Unit u = (Unit) e;
					double d1 = Math.sqrt((mx - u.x) * (mx - u.x) + (my - u.y) * (my - u.y));
					if (d1 < bestDist || bestDist == -1) {
						bestDist = d1;
						toSelect = u.id;
					}
				}
			}
			return toSelect;
		}
		return -1;
	}

	public void render(Screen screen) {
		World w = Game.instance.getWorld();
		screen.setScroll(scrollX, scrollY);
		int ww = (width + 31);
		int hh = (height + 31);
		int xo = (scrollX);
		int yo = (scrollY);

		int tww = ww / Tile.WIDTH;
		int thh = hh / Tile.HEIGHT;
		int txo = xo / Tile.WIDTH;
		int tyo = yo / Tile.HEIGHT;

		for (int y = tyo; y <= thh + tyo; y++) {
			for (int x = txo; x <= tww + txo; x++) {
				w.getTile(x, y).render(screen, x, y);
			}
		}

		List<Entity> e = w.getTileEntities(txo, tyo, tww, thh);
		if (e.size() > 0)
			sortAndRender(screen, e);

		screen.setScroll(0, 0);

		renderHUD(screen);

	}

	public void renderHUD(Screen screen) {
		Font.draw("FPS:" + Game.instance.fps, screen, 3, 3, 0xFFFFDD00);
		for (int i = 0; i < messages.size(); i++) {
			messages.get(i).render(screen, i);
		}
	}

	private Comparator<Entity> spriteSorter = new Comparator<Entity>() {

		public int compare(Entity e1, Entity e2) {
			if (e2.y > e1.y)
				return -1;
			if (e2.y < e1.y)
				return +1;
			return 0;
		}
	};

	private void sortAndRender(Screen screen, List<Entity> list) {
		Collections.sort(list, spriteSorter);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).render(screen);
		}
	}

	public void addUnit(Unit unit, int lastEntityId) {
		this.units.put(lastEntityId, unit);
	}
}
