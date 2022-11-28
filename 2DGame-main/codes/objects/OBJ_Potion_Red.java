package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Potion_Red extends Characters{
	
	GameScreen gs;
	public OBJ_Potion_Red(GameScreen gs) {
		super(gs);
		this.gs=gs;
		type= type_consumable;
		name="RedBull";
		value=2;
		price=10;
		down1= setup("/objekts/potion_red",gs.sizeRect,gs.sizeRect);
		description = "[" + name +"]\nYou can fly. If you \nwant. Heal by "+value+"hp.";
	}
	
	public void use(Characters character) {
		gs.gameStates = gs.dialogState;
		gs.ui.currentDialogue= "You drink the "+name+"!\n" + "Your life has been recovered \nby "+value+".";
		character.life += value;
		gs.playSE(2);
	}
}
