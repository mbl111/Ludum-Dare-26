package com.mbl111.ld26.screen;

import com.mbl111.ld26.Game;
import com.mbl111.ld26.world.World;
import com.mbl111.ld26.world.tile.Tile;

public class PlayerView {

	public int scrollX = 0;
	public int scrollY = 0;
	public int width, height;

	public PlayerView(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void tick() {
		int mx = Game.instance.getInput().x;
		int my = Game.instance.getInput().y;
		
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

	public void render(Screen screen) {
		// get section of world based on scroll and view size;
		// Draw it to the screen, no real need to worry about scrolling as its
		// handled in getting the world;
		World w = Game.instance.getWorld();
		screen.setScroll(scrollX, scrollY);
		int ww = (width + 15) >> 4;
		int hh = (height + 15) >> 4;
		int xo = (scrollX / 2) >> 4;
		int yo = (scrollY / 2) >> 4;

		for (int y = yo; y <= hh + yo; y++) {
			for (int x = xo; x <= ww + xo; x++) {
				w.getTile(x, y).render(screen, x, y);
			}
		}
		screen.setScroll(0, 0);

	}

}
