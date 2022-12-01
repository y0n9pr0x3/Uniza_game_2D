package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Lantern extends Characters{

	public OBJ_Lantern(GameScreen gs) {
		super(gs);
		
		type= type_light;
		name= "Buď svetlo!";
		down1 = setup("/objekts/lantern",gs.sizeRect,gs.sizeRect);
		description = "["+name+"]"+"\nŽiža , pozor aby ti\nnedošli baterky!";
		price =200;
		lightRadius=250;
	}

}
