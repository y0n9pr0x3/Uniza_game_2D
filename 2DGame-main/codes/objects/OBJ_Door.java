package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Door extends Characters {
	public OBJ_Door(GameScreen gs) {
		super(gs);
		name="Door";
		down1 = setup("/objekts/door",gs.sizeRect,gs.sizeRect);
		type= type_door;
		
		solidRect.x =0;
		solidRect.y = 16;
		solidRect.width = 48;
		solidRect.height = 38;
		solidRectDefaultX = solidRect.x;
		solidRectDefaultY = solidRect.y;
		
		collision = true;
		
	}
	
	
}
