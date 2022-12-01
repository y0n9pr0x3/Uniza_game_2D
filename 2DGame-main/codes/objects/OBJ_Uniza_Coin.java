package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Uniza_Coin extends Characters{
	GameScreen gs;
	public OBJ_Uniza_Coin(GameScreen gs) {
		super(gs);
		this.gs=gs;
		
		name="Uniza dolár";
		type = type_pickupONLY;
		down1= setup ("/objekts/coin_uniza",gs.sizeRect,gs.sizeRect);
		value=10;
	}
	
	public boolean use(Characters character) {
		
		gs.playSE(1);
		gs.ui.addMessage("Lovákos +" +value);
		gs.player.coin +=value;
		return true;
	}
}
