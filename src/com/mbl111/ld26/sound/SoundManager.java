package com.mbl111.ld26.sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoundManager {

	private static ISound blankSound = new BlankSoundClip();
	private static HashMap<String, ISound> sounds = new HashMap<String, ISound>();
	private static List<SoundBackground> bgMusic = new ArrayList<SoundBackground>();
	public static SoundBackground currentBgMusic;

	public static ISound getSound(String name) {
		if (sounds.containsKey(name)) {
			return sounds.get(name);
		}
		return blankSound;
	}

}
