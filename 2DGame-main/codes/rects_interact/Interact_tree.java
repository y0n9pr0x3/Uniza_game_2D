package rects_interact;

import characters.Characters;
import main.GameScreen;

public class Interact_tree extends InteractiveRect{
	GameScreen gs;
	
	public Interact_tree(GameScreen gs,int col , int row) {
		super(gs,col ,row);
		this.gs=gs;
		
		this.worldX = gs.sizeRect * col;
		this.worldY = gs.sizeRect * row;
		
		down1 = setup("/rects_interact/drytree",gs.sizeRect,gs.sizeRect);
		destruct= true;
		life = 3;
	}
	
	public boolean isAxe(Characters character) {
		
		boolean isAxe=false;
		
		if(character.currentWeapon.type == type_axe) {
			isAxe=true;
		}
		
		return isAxe;
	}
	
	public void playSE() {
		gs.playSE(11);
	}
	
	public InteractiveRect getDownTree() {
		InteractiveRect rect= new Interact_trunk(gs, worldX/gs.sizeRect, worldY/gs.sizeRect);
		return rect;
	}

}
