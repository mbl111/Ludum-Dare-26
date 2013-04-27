package com.mbl111.ld26;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 360;
	public static final int HEIGHT = WIDTH * 3 / 4;
	public static final String NAME = "";
	private static int SCALE = 2;

	private JFrame frame;
	public boolean running = true;
	private int fps;

	public Game() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void start() {
		new Thread(this, "Main Game Thread").start();
	}

	private void stop() {
		this.running = false;
	}

	public void rescale(int scale) {
		if (this.running == false) {
			throw new UnsupportedOperationException(
					"Can't rescale the window while the game is not running. ");
		}
		this.SCALE = scale;
		this.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	private void init() {

	}

	public void run() {
		init();

		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		long lastTime = System.nanoTime();
		long lastTimer1 = System.currentTimeMillis();

		while (running) {

			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(1L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			{
				if (shouldRender) {
					frames++;
					render();
				}
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				this.fps = frames;
				System.out.println(ticks + " ticks - " + frames + " fps");
				frames = 0;
				ticks = 0;
			}

		}
	}

	private void tick() {

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		int width = WIDTH * SCALE;
		int height = HEIGHT * SCALE;
		int xs = (getWidth() - width) / 2;
		int ys = (getHeight() - height) / 2;
		g.drawImage(screen.image, xs, ys, width, height, null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		game.frame = new JFrame(NAME);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLayout(new BorderLayout());
		game.frame.add(game, BorderLayout.CENTER);
		game.frame.pack();
		game.frame.setResizable(false);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}

}
