package rects_interact;

import characters.Characters;
import main.GameScreen;

public class InteractiveRect extends Characters{
	
	GameScreen gs;
	public boolean destruct = false;
	public InteractiveRect(GameScreen gs,int col , int row) {
		super(gs);
		this.gs=gs;
		
	}
	
	public boolean isAxe(Characters character) {
		
		boolean isAxe=false;
		
		return isAxe;
	}
	
	public void playSE() {
		
	}
	
	public InteractiveRect getDownTree() {
		InteractiveRect rect= null;
		return rect;
	}
	
	public void update() {
		
		if(invincible == true) {
			invincibleCount++;
			if(invincibleCount >20) {
				invincible= false;
				invincibleCount=0;
			}
		}
		
	}
	

}
