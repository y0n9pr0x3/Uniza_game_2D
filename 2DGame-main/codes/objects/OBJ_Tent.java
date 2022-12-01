package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Tent extends Characters{
	GameScreen gs;
	
	public OBJ_Tent(GameScreen gs) {
		super(gs);
		this.gs = gs;
		
		type = type_consumable;
		name= "Stan";
		down1= setup("/objekts/tent",gs.sizeRect,gs.sizeRect);
		description= "["+name+"]"+"\nŠe vyspi bo si \nnadruzgany!";
		price = 300;
		stackable= true;
	}
	
	public boolean use(Characters character) {
		
		gs.gameStates = gs.sleepState;
		gs.playSE(15);
		gs.player.life = gs.player.maxLife;
		gs.player.mana = gs.player.maxMana;
		gs.player.getSleepImage(down1);
		return true;
	}
}
