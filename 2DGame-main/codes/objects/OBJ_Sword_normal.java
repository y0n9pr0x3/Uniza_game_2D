package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Sword_normal extends Characters{
	
	public OBJ_Sword_normal(GameScreen gs) {
		super(gs);
		
		type = type_sword;
		name= "Vreckový nožíček";
		down1= setup("/objekts/sword_normal",gs.sizeRect,gs.sizeRect);
		attackValue = 1;
		attackArea.width= 36;
		attackArea.height=36;
		description = "[" + name +"]\nPozor, \nostrí jak čert!";
		price=6;
		knockBackPower = 2;
		
	}
}
