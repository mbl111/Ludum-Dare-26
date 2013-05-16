package com.mbl111.ld26.screen;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.mbl111.ld26.Game;

public class Art {

	public static Bitmap[][] WALL = cutLoad("wall", 32, 32);
	public static Bitmap[][] TILES = cutLoad("tiles", 32, 32);
	public static Bitmap[][] FONT = cutLoad("font", 8, 8);
	public static Bitmap[][] UNIT = cutLoad("unit", 8, 12);
	public static Bitmap[][] BUILDINGS = cutLoad("buildings", 32, 32);
	public static Bitmap[][] HUDOVERLAY = cutLoad("hud", 360, 48);
	public static Bitmap[][] TOPBAR = cutLoad("topbar", 360, 12);

	private static Bitmap[][] cutLoad(String name, int w, int h) {

		BufferedImage bi = null;
		Bitmap[][] bitmaps;
		try {
			URL u = Game.class.getResource("/textures/" + name + ".png");
			bi = ImageIO.read(u);

		} catch (Exception e) {
			System.err.println("Failed to load texture - " + name);
			return null;
		}
		int nx = bi.getWidth() / w;
		int ny = bi.getHeight() / h;
		bitmaps = new Bitmap[nx][ny];
		for (int cx = 0; cx < nx; cx++) {
			for (int cy = 0; cy < ny; cy++) {
				bitmaps[cx][cy] = new Bitmap(w, h);
				bitmaps[cx][cy].pixels = bi.getRGB(cx * w, cy * h, w, h, null, 0, w);
			}
		}

		return bitmaps;
	}
}
