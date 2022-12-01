package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Flasa_Borovicky extends Characters{
	
	GameScreen gs;
	public OBJ_Flasa_Borovicky(GameScreen gs) {
		super(gs);
		this.gs=gs;
		type= type_consumable;
		name="Borovička";
		value=4;
		price=15;
		down1= setup("/objekts/flasa_boroviky",gs.sizeRect,gs.sizeRect);
		description = "[" + name +"]\nIba pre samovrahou,\nkonzumácia zakázaná!";
	}
	
	public boolean use(Characters character) {
		gs.gameStates = gs.dialogState;
		gs.ui.currentDialogue= "Cíí, vágo mocný elixír.";
		character.speed += 10;
		gs.playSE(2);
		return true;
	}
}
