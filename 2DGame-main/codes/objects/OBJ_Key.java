package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Key extends Characters{
	GameScreen gs;
	public OBJ_Key(GameScreen gs) {
		super(gs);
		this.gs = gs;
		
		type = type_consumable;
		name="VšeKľúč";
		down1 = setup("/objekts/key",gs.sizeRect,gs.sizeRect);
		description = "[" + name +"]\nAj tajomnú komnatu \nsním odemkneš!";
		price=10;
		stackable = true;
	}
	
	public boolean use(Characters character) {
		gs.gameStates = gs.dialogState;
		
		int objIndex= getDetected(character, gs.obj,"Dveros");
		
		if(objIndex != 999) {
			gs.ui.currentDialogue = "Odemkol si dvere, vyzuj \nse a pokračuj dál.";
			gs.playSE(3);
			gs.obj[gs.currentMap][objIndex] = null;
			return true;
		}else {
			gs.ui.currentDialogue= "Vidíš dvere? Ne? tak čo \nskúšaš!";
			return false;
		}
	}
}
