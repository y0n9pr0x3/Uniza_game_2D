package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Door extends Characters {
	GameScreen gs;
	
	public OBJ_Door(GameScreen gs) {
		super(gs);
		this.gs=gs;
		
		name="Dveros";
		down1 = setup("/objekts/door",gs.sizeRect,gs.sizeRect);
		type= type_obstacle;
		
		solidRect.x =0;
		solidRect.y = 16;
		solidRect.width = 48;
		solidRect.height = 28;
		solidRectDefaultX = solidRect.x;
		solidRectDefaultY = solidRect.y;
		
		collision = true;
		
	}
	
	public void interact() {
		gs.gameStates = gs.dialogState;
		gs.ui.currentDialogue= "Šaňo, zabudel zebrac kĺúča \nnajprv ho zežeň.";
	}
	
}
