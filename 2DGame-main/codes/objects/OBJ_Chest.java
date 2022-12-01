package objects;

import characters.Characters;
import main.GameScreen;

public class OBJ_Chest extends Characters{
	GameScreen gs;
	Characters loot;
	boolean opened = false;
	
	public OBJ_Chest(GameScreen gs, Characters loot) {
		super(gs);
		this.gs=gs;
		this.loot = loot;
		
		type= type_obstacle;
		name="Truhla";
		
		image = setup("/objekts/chest",gs.sizeRect,gs.sizeRect);
		image2 = setup("/objekts/chest_opened",gs.sizeRect,gs.sizeRect);
		down1= image;
		collision=true;
		
		solidRect.x= 4;
		solidRect.y= 16;
		solidRect.width= 40;
		solidRect.height= 32;
		solidRectDefaultX = solidRect.x;
		solidRectDefaultY= solidRect.y;
	}
	
	public void interact() {
		gs.gameStates = gs.dialogState;
		if(opened == false) {
			gs.playSE(3);
			
			StringBuilder sb = new StringBuilder();
			
			
			if(gs.player.canObtainItem(loot)== false) {
				sb.append("\n...Máš plnej vercajk, brácho!");
			}else {
				sb.append("Otvoril si truhlicu, bráško!");
				sb.append("\nInventár čekuj. Jou");
				//gs.player.inventory.add(loot);
				down1=image2;
				opened = true;
			}
			gs.ui.currentDialogue = sb.toString();
		}else {
			gs.ui.currentDialogue = "Nič še nebuj, došel si \nneskoro!";
		}
	}
}
