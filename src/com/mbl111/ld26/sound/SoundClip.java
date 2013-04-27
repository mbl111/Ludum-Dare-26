package com.mbl111.ld26.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.DataLine.Info;

import com.mbl111.ld26.Game;

public class SoundClip implements ISound{

	protected Info info;
	protected byte[] audio;
	protected int length;
	protected AudioFormat format;
	protected String name;

	public SoundClip(String name) {
		this.name = name;
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Game.class.getResource("/" + name + ".wav"));
			format = audioInputStream.getFormat();
			length = (int) (format.getFrameSize() * audioInputStream.getFrameLength());
			audio = new byte[length];
			info = new DataLine.Info(Clip.class, format, length);
			audioInputStream.read(audio, 0, length);

		} catch (Exception e) {
			System.err.println("Failed to load sound - " + name);
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			Clip playable = (Clip) AudioSystem.getLine(info);
			playable.open(format, audio, 0, length);
			playable.start();
		} catch (LineUnavailableException e) {
			System.out.println("Failed to play sound!");
		}
	}

}
