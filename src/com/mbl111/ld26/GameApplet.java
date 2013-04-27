package com.mbl111.ld26;

import java.applet.Applet;
import java.awt.BorderLayout;

public class GameApplet extends Applet {

	public Game game = new Game();

	@Override
	public void start() {
		game.start();
		super.start();
	}

	@Override
	public void stop() {
		super.stop();
	}

	public void init() {
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
	};

}
