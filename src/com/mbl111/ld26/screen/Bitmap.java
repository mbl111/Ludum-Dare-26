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
		for (int xx = 0; x < bitmap.width; x++) {
			if (xx + x >= width || xx + x < 0)
				continue;
			for (int yy = 0; x < bitmap.height; y++) {
				if (yy + y >= height || yy + y < 0)
					continue;
				int color = bitmap.pixels[xx + yy * bitmap.width];
				int alpha = (color >> 24) & 0xFF;
				if (alpha == 0)
					continue;

				if (alpha == 255) {
					pixels[(xx + x) + (yy + y) * width] = color;
				} else {
					pixels[(xx + x) + (yy + y) * width] = blend(pixels[(xx + x) + (yy + y) * width], color);
				}

			}
		}
	}

	public void drawColor(Bitmap bitmap, int x, int y, int color){
		for (int xx = 0; x < bitmap.width; x++) {
			if (xx + x >= width || xx + x < 0)
				continue;
			for (int yy = 0; x < bitmap.height; y++) {
				if (yy + y >= height || yy + y < 0)
					continue;
				int color2 = bitmap.pixels[xx + yy * bitmap.width];
				int alpha = (color >> 24) & 0xFF;
				if (alpha == 0)
					continue;

				if (alpha == 255) {
					pixels[(xx + x) + (yy + y) * width] = blend(color2, color);
				} else {
					pixels[(xx + x) + (yy + y) * width] = blend(pixels[(xx + x) + (yy + y) * width], color);
				}

			}
		}
	}
	
	private int blend(int col1, int col2) {
		int a2 = (col2 >> 24) & 0xFF;
		int a1 = 256 - a2;
		
		int r1 = col1 & 0xFF0000;
		int g1 = col1 & 0xFF00;
		int b1 = col1 & 0xFF;
		
		int r1 = col1 & 0xFF0000;
		int g1 = col1 & 0xFF00;
		int b1 = col1 & 0xFF;
		return 0;
	}

	public void fill(int x, int y, int w, int h, int color) {
		Bitmap b = new Bitmap(w, h);
		b.clear(color);
		draw(b, x, y);
	}

}
