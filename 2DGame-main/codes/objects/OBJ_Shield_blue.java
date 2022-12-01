package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Shield_blue extends Characters{
	
	public OBJ_Shield_blue(GameScreen gs) {
		super(gs);
		
		type = type_shield;
		name= "Ochranu II. Stupňa";
		down1= setup("/objekts/shield_blue",gs.sizeRect,gs.sizeRect);
		defenseValue = 2;
		description = "[" + name +"]\nBlbuvzdorný štít II.\ngenerácie.";
		price=8;
	}
}
