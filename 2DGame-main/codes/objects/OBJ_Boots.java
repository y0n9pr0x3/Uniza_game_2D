package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Boots extends Characters{
	public OBJ_Boots(GameScreen gs) {
		super(gs);
		type = type_pickupONLY;
		name="Boots";
		down1 = setup("/objekts/boots",gs.sizeRect,gs.sizeRect);
		price=7;
		
		description = "[" + name +"]\nYou will be fast.";
	}
}
