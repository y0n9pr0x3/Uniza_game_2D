package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Potion_Red extends Characters{
	
	GameScreen gs;
	public OBJ_Potion_Red(GameScreen gs) {
		super(gs);
		this.gs=gs;
		type= type_consumable;
		name="Gumidžús";
		value=2;
		price=10;
		down1= setup("/objekts/potion_red",gs.sizeRect,gs.sizeRect);
		description = "[" + name +"]\nŠe, napi a budeš \nskákac jak gumkáč!";
		stackable = true;
	}
	
	public boolean use(Characters character) {
		gs.gameStates = gs.dialogState;
		gs.ui.currentDialogue= "You drink the "+name+"!\n" + "Your life has been \nrecovered by "+value+".";
		character.life += value;
		gs.playSE(2);
		return true;
	}
}
