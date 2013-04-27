package com.mbl111.ld26.screen;

public class Font {

	private static String chars = "" + //
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ|     " + //
			"0123456789.,!?'\"-+=/\\%()<>:{     " + //
			"";

	public static void setMap(String msg, Screen screen, int x, int y, int col) {
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			int ix = chars.indexOf(msg.charAt(i));
			if (ix >= 0) {
				// /screen.setTile(x + i, y, ix + 30 * 32,col, 0);
			}
		}

	}

	public static void draw(String msg, Screen screen, int x, int y, int col) {
		if (msg != null) {
			if (msg.length() > 0) {
				msg = msg.toUpperCase();
				for (int i = 0; i < msg.length(); i++) {
					int ix = chars.indexOf(msg.charAt(i));
					if (ix >= 0) {
						screen.drawFont(Art.FONT[ix % 32][ix / 32], x + (i * 8), y, col);
					}
				}
			}
		}
	}

	public static void drawWithShadow(String msg, Screen screen, int x, int y, int col) {
		if (msg != null) {
			if (msg.length() > 0) {
				msg = msg.toUpperCase();
				for (int i = 0; i < msg.length(); i++) {
					int ix = chars.indexOf(msg.charAt(i));
					if (ix >= 0) {
						screen.drawFont(Art.FONT[ix % 32][ix / 32], x + (i * 8), y + 1, col & 0xFF555555);
						screen.drawFont(Art.FONT[ix % 32][ix / 32], x + (i * 8), y, col);
					}
				}
			}
		}
	}

	// public static void drawWithShadow(String msg, Screen screen, int x, int
	// y, int col) {
	// drawWithShadow(msg, screen, x, y, col, 1);
	// }
}
