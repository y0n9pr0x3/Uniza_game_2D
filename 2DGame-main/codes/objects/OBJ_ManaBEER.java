package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_ManaBEER extends Characters{
	
	

	GameScreen gs;
	
	public OBJ_ManaBEER(GameScreen gs) {
		super(gs);
		this.gs=gs;
		
		type= type_pickupONLY;
		name = "Beer";
		value= 1;
		price=7;
		down1 = image= setup("/objekts/beer_full",gs.sizeRect, gs.sizeRect);
		image= setup("/objekts/beer_full",gs.sizeRect, gs.sizeRect);
		image2= setup("/objekts/beer_blank",gs.sizeRect, gs.sizeRect);
		
		description = "[" + name +"]\nYou can shoot \nfireballs!";
	}

	
	public void use(Characters character) {
		gs.playSE(2);
		gs.ui.addMessage("Mana +" + value );
		character.mana += value;
		
	}
}
