package com.mbl111.ld26.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen extends Bitmap {

	public BufferedImage image;
	private int xOff = 0;
	private int yOff = 0;

	public Screen(int w, int h) {
		super(w, h);
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	public void draw(Bitmap bitmap, int x, int y) {
		super.draw(bitmap, (x - this.xOff), (y - this.yOff));
	}

	public void drawWash(Bitmap bitmap, int x, int y, int color) {
		super.drawWash(bitmap, (x - this.xOff), (y - this.yOff), color);
	}

	public void setScroll(int x, int y) {
		this.xOff = x;
		this.yOff = y;
	}

}
