package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sounds {
	Clip clip;
	URL soundUrl[]= new URL[30];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	public Sounds() {
		soundUrl[0]= getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundUrl[1]= getClass().getResource("/sound/coin.wav");
		soundUrl[2]= getClass().getResource("/sound/powerup.wav");
		soundUrl[3]= getClass().getResource("/sound/unlock.wav");
		soundUrl[4]= getClass().getResource("/sound/fanfare.wav");
		soundUrl[5]= getClass().getResource("/sound/hitmonster.wav");
		soundUrl[6]= getClass().getResource("/sound/receivedamage.wav");
		soundUrl[7]= getClass().getResource("/sound/swing.wav");
		soundUrl[8]= getClass().getResource("/sound/levelup.wav");
		soundUrl[9]= getClass().getResource("/sound/cursor.wav");
		soundUrl[10]= getClass().getResource("/sound/burning.wav");
		soundUrl[11]= getClass().getResource("/sound/cuttree.wav");
		soundUrl[12]= getClass().getResource("/sound/gameover.wav");
		soundUrl[13]= getClass().getResource("/sound/stairs.wav");
		soundUrl[14]= getClass().getResource("/sound/uniza_level_music1.wav");
		soundUrl[15]= getClass().getResource("/sound/sleep.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
			clip= AudioSystem.getClip();
			clip.open(ais);
			fc= (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}
	public void checkVolume() {
		switch(volumeScale) {
		case 0: volume= -80f; break;
		case 1: volume = -20f; break;
		case 2: volume = -12f; break;
		case 3: volume = -5f; break;
		case 4: volume = 1f; break;
		case 5: volume =6f; break;
		}
		fc.setValue(volume);
	}
}
