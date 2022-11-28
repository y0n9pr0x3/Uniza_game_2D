package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Flasa_Vodky extends Characters{
	
	GameScreen gs;
	public OBJ_Flasa_Vodky(GameScreen gs) {
		super(gs);
		this.gs=gs;
		type= type_consumable;
		name="Vodka";
		value=4;
		price=15;
		down1= setup("/objekts/flasa_vodky",gs.sizeRect,gs.sizeRect);
		description = "[" + name +"]\nVodka better than \nsex. Heal by "+value+"hp.";
	}
	
	public void use(Characters character) {
		gs.gameStates = gs.dialogState;
		gs.ui.currentDialogue= "You drink the "+name+"!\n" + "Your life has been \nrecovered by "+value+".";
		character.life += value;
		gs.playSE(2);
	}

}
