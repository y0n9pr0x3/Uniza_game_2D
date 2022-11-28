package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Key extends Characters{
	
	public OBJ_Key(GameScreen gs) {
		super(gs);
		
		
		name="Key";
		down1 = setup("/objekts/key",gs.sizeRect,gs.sizeRect);
		description = "[" + name +"]\nIt opens a door.";
		price=10;
	}
}
