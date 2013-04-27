package com.mbl111.ld26.world.tile.util;

public class MathHelper {

	public static double distance(int x0, int y0, int x1, int y1) {
		int x = x0 - x1;
		int y = y0 - y1;
		return Math.abs(Math.sqrt(x * x + y * y));
	}

	public static int clamp(int toClamp, int range) {
		if (toClamp > range)
			toClamp = range;
		if (toClamp < -range)
			toClamp = -range;
		return toClamp;
	}
}
