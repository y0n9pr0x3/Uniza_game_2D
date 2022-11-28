package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Chest extends Characters{
	public OBJ_Chest(GameScreen gs) {
		super(gs);
		name="Chest";
		down1 = setup("/objekts/chest",gs.sizeRect,gs.sizeRect);
	}
}
