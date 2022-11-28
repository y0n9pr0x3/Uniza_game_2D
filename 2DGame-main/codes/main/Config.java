package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GameScreen gs;
	
	public Config(GameScreen gs) {
		this.gs=gs;
	}
	
	public void saveConf() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			//full screen
			if(gs.fullscreenOn == true) {
				bw.write("on");	
			}
			if(gs.fullscreenOn == false) {
				bw.write("false");	
			}
			bw.newLine();
			
			//music volume
			bw.write(String.valueOf(gs.music.volumeScale));
			bw.newLine();
			
			//se volume
			bw.write(String.valueOf(gs.se.volumeScale));
			bw.newLine();
			
			bw.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void loadConf() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			String s= br.readLine();
			
			//full screen
			if(s.equals("on")) {
				gs.fullscreenOn = true;
			}
			if(s.equals("off")) {
				gs.fullscreenOn = false;
			}
			
			//music volume
			s=br.readLine();
			gs.music.volumeScale = Integer.parseInt(s);
			
			//se volume
			s=br.readLine();
			gs.se.volumeScale = Integer.parseInt(s);
			
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
