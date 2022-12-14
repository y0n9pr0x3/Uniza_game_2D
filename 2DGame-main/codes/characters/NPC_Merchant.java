package characters;

import main.GameScreen;
import objects.OBJ_Axe;
import objects.OBJ_Boots;
import objects.OBJ_Fireball;
import objects.OBJ_Flasa_Borovicky;
import objects.OBJ_Flasa_Vodky;
import objects.OBJ_Key;
import objects.OBJ_Lantern;
import objects.OBJ_ManaBEER;
import objects.OBJ_Potion_Red;
import objects.OBJ_Shield_blue;
import objects.OBJ_Sword_normal;
import objects.OBJ_Tent;

public class NPC_Merchant extends Characters{
	
	public NPC_Merchant(GameScreen gs) {
		super(gs);
		direction = "down";
		//speed =1;
		
		getNPCImage();
		setDialogue();
		setItems();
	}
	
	public void getNPCImage() {
		up1= setup("/npc/merchant_up_1",gs.sizeRect,gs.sizeRect);
		up2= setup("/npc/merchant_up_2",gs.sizeRect,gs.sizeRect);
		down1= setup("/npc/merchant_down_1",gs.sizeRect,gs.sizeRect);
		down2= setup("/npc/merchant_down_2",gs.sizeRect,gs.sizeRect);
		left1= setup("/npc/merchant_left_1",gs.sizeRect,gs.sizeRect);
		left2= setup("/npc/merchant_left_2",gs.sizeRect,gs.sizeRect);
		right1= setup("/npc/merchant_right_1",gs.sizeRect,gs.sizeRect);
		right2= setup("/npc/merchant_right_2",gs.sizeRect,gs.sizeRect);
	}
	
	public void setDialogue() {
		dialogues[0]= "Szia bro, mám super \nmatroš keby chceš vybavím \nhop-šup prášek!";
		
	}
	
	public void setItems() {
		inventory.add(new OBJ_Potion_Red(gs));
		inventory.add(new OBJ_Shield_blue(gs));
		inventory.add(new OBJ_Sword_normal(gs));
		inventory.add(new OBJ_Key(gs));
		inventory.add(new OBJ_Axe(gs));
		inventory.add(new OBJ_Lantern(gs));
		inventory.add(new OBJ_Tent(gs));
		//inventory.add(new OBJ_ManaBEER(gs));
		inventory.add(new OBJ_Flasa_Vodky(gs));
		inventory.add(new OBJ_Flasa_Borovicky(gs));
		
	}
	
	public void speak() {
		super.speak();
		gs.gameStates = gs.tradingState;
		gs.ui.npc= this;
	}
}
