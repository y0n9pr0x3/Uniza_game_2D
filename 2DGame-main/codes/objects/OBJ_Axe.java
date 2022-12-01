package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Axe extends Characters{

	public OBJ_Axe(GameScreen gs) {
		super(gs);
		
		type = type_axe;
		name= "Sekeru";
		knockBackPower = 10;
		down1= setup("/objekts/axe",gs.sizeRect,gs.sizeRect);
		attackValue = 2;
		attackArea.width= 30;
		attackArea.height=30;
		description = "[" + name +"]\nVeľmi dobrá keď, \nti strom zavadzá.";
		price=6;
	}
	
	

}
