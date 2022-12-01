package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Wood_Shield extends Characters{

	public OBJ_Wood_Shield(GameScreen gs) {
		super(gs);
		
		type = type_shield;
		name= "Ochrana I. Stupňa";
		down1= setup("/objekts/shield_wood",gs.sizeRect,gs.sizeRect);
		defenseValue = 1;
		description = "[" + name +"]\nBlbuvzdorný štít I.\ngenerácie.";
		price=6;
	}

}
