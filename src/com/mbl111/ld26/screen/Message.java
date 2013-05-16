package com.mbl111.ld26.screen;

import com.mbl111.ld26.Game;

public class Message {

	private String message;
	private MessageType type;
	private int life = 60 * 6;

	public Message(String msg, MessageType type) {
		this.message = msg;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public MessageType getType() {
		return type;
	}

	public void tick() {
		this.life -= 1;
	}

	public void render(Screen screen, int messageIndex) {
		int c = type.color;
		if (life < 32) {
			c = c & (0xFFFFFF | ((life * 8) & 0xFF) << 24);
		}
		Font.draw(message, screen, (Game.WIDTH - (message.length() * 8)) / 2, 8 * messageIndex + 13, c);
	}

	public static enum MessageType {
		ERROR(0xFFFF2222), INFO(0xFFDDDDDD);

		public int color;

		private MessageType(int color) {
			this.color = color;
		}

	}

	public boolean isDead() {
		return this.life <= 0;
	}

}
