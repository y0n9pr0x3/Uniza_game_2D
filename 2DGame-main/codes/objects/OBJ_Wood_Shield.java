package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Wood_Shield extends Characters{

	public OBJ_Wood_Shield(GameScreen gs) {
		super(gs);
		
		type = type_shield;
		name= "Wooden shield";
		down1= setup("/objekts/shield_wood",gs.sizeRect,gs.sizeRect);
		defenseValue = 1;
		description = "[" + name +"]\nMade by wood.";
		price=6;
	}

}
