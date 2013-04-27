package com.mbl111.ld26.screen;

import java.util.Arrays;

public class Bitmap {

	public int[] pixels;
	private int width, height;

	public Bitmap(int w, int h) {
		this.width = w;
		this.height = h;
		this.pixels = new int[w * h];
		clear(0xFFFFFFFF);
	}

	public void clear(int color) {
		Arrays.fill(pixels, color);
	}

	public void draw(Bitmap bitmap, int x, int y) {
		for (int xx = 0; xx < bitmap.width; xx++) {
			if (xx + x >= width || xx + x < 0)
				continue;
			for (int yy = 0; yy < bitmap.height; yy++) {
				if (yy + y >= height || yy + y < 0)
					continue;
				int color = bitmap.pixels[xx + yy * bitmap.width];
				int alpha = (color >> 24) & 0xFF;
				if (alpha == 0)
					continue;
				if (alpha == 0xFF) {
					pixels[(xx + x) + (yy + y) * width] = color;
				} else {
					pixels[(xx + x) + (yy + y) * width] = blend(pixels[(xx + x) + (yy + y) * width], color);
				}
			}
		}
	}

	public void drawFont(Bitmap bitmap, int x, int y, int color) {
		for (int xx = 0; xx < bitmap.width; xx++) {
			if (xx + x >= width || xx + x < 0)
				continue;
			for (int yy = 0; yy < bitmap.height; yy++) {
				if (yy + y >= height || yy + y < 0)
					continue;
				int color2 = bitmap.pixels[xx + yy * bitmap.width];
				int alpha = (color2 >> 24) & 0xFF;
				if (alpha == 0)
					continue;
				if (alpha == 0xFF) {
					int a = (color >> 24) & 0xFF;
					if (a == 0)
						continue;
					if (a == 0xFF) {
						pixels[(xx + x) + (yy + y) * width] = color;
					} else {
						pixels[(xx + x) + (yy + y) * width] = blend(pixels[(xx + x) + (yy + y) * width], color);
					}
				}
			}
		}
	}

	public void drawWash(Bitmap bitmap, int x, int y, int color) {
		for (int xx = 0; xx < bitmap.width; xx++) {
			if (xx + x >= width || xx + x < 0)
				continue;
			for (int yy = 0; yy < bitmap.height; yy++) {
				if (yy + y >= height || yy + y < 0)
					continue;
				int color2 = bitmap.pixels[xx + yy * bitmap.width];
				int alpha = (color2 >> 24) & 0xFF;
				if (alpha == 0)
					continue;
				int blended = blend(color2, color);
				if (alpha == 0xFF) {
					pixels[(xx + x) + (yy + y) * width] = blended;
				} else {
					pixels[(xx + x) + (yy + y) * width] = blend(pixels[(xx + x) + (yy + y) * width], blended);
				}
			}
		}
	}

	private int blendOn(int col, int col1) {
		int a2 = (col1 >> 24) & 0xFF;
		int a1 = 256 - a2;

		int rr = col1 & 0xFF0000;
		int gg = col1 & 0xFF00;
		int bb = col1 & 0xFF;

		int r = col & 0xFF0000;
		int g = col & 0xFF00;
		int b = col & 0xFF;

		r = r * a1 + rr * a2 >> 8 & 0xFF0000;
		g = g * a1 + gg * a2 >> 8 & 0xFF00;
		b = b * a1 + bb * a2 >> 8 & 0xFF;

		return ((a2 << 24) | r | g | b);
	}

	private int blend(int col, int col1) {
		int a2 = (col1 >> 24) & 0xFF;
		int a1 = 256 - a2;

		int rr = col1 & 0xFF0000;
		int gg = col1 & 0xFF00;
		int bb = col1 & 0xFF;

		int r = col & 0xFF0000;
		int g = col & 0xFF00;
		int b = col & 0xFF;

		r = r * a1 + rr * a2 >> 8 & 0xFF0000;
		g = g * a1 + gg * a2 >> 8 & 0xFF00;
		b = b * a1 + bb * a2 >> 8 & 0xFF;

		return (0xFF000000 | r | g | b);
	}

	public void fill(int x, int y, int w, int h, int color) {
		Bitmap b = new Bitmap(w, h);
		b.clear(color);
		draw(b, x, y);
	}

}
