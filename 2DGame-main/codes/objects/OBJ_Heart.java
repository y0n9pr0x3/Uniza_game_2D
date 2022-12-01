package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Heart extends Characters{
	GameScreen gs;
	public OBJ_Heart(GameScreen gs) {
		super(gs);
		this.gs=gs;
		type= type_pickupONLY;
		name="Srdce";
		value=2;
		down1= setup("/objekts/heart_full",gs.sizeRect,gs.sizeRect);
		image= setup("/objekts/heart_full",gs.sizeRect,gs.sizeRect);
		image2= setup("/objekts/heart_half",gs.sizeRect,gs.sizeRect);
		image3= setup("/objekts/heart_blank",gs.sizeRect,gs.sizeRect);
	}
	
	public boolean use(Characters character) {
		gs.playSE(2);
		gs.ui.addMessage("Srdcia +" + value );
		character.life += value;
		return true;
	}
}
