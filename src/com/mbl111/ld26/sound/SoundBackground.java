package com.mbl111.ld26.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class SoundBackground extends SoundClip implements ISound{

	public Clip clip;

	public SoundBackground(String name) {
		super(name);
	}

	public void play() {
		try {
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(format, audio, 0, length);
			clip.start();
		} catch (LineUnavailableException e) {
			System.out.println("Failed to play sound!");
		}
	}

	public void stop() {
		if (clip != null) {
			clip.stop();
			clip = null;
		}
	}

	public boolean isPlaying() {
		return clip != null;
	}

}
