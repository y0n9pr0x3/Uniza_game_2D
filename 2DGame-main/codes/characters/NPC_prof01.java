package characters;

import java.util.Random;

import main.GameScreen;

public class NPC_prof01 extends Characters{
	
	public NPC_prof01(GameScreen gs) {
		super(gs);
		direction = "down";
		speed =1;
		
		getNPCImage();
		setDialogue();
	}
	
	public void getNPCImage() {
		up1= setup("/npc/oldman_up_1",gs.sizeRect,gs.sizeRect);
		up2= setup("/npc/oldman_up_2",gs.sizeRect,gs.sizeRect);
		down1= setup("/npc/oldman_down_1",gs.sizeRect,gs.sizeRect);
		down2= setup("/npc/oldman_down_2",gs.sizeRect,gs.sizeRect);
		left1= setup("/npc/oldman_left_1",gs.sizeRect,gs.sizeRect);
		left2= setup("/npc/oldman_left_2",gs.sizeRect,gs.sizeRect);
		right1= setup("/npc/oldman_right_1",gs.sizeRect,gs.sizeRect);
		right2= setup("/npc/oldman_right_2",gs.sizeRect,gs.sizeRect);
	}
	
	public void setActionNpc() {
		actionLockerCounter++;
		
		if(actionLockerCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1;
			if(i<=25) {
				direction="up";
			}
			if(i>25 && i <= 50) {
				direction="down";
			}
			if(i>50 && i <= 75) {
				direction="left";
			}
			if(i>75 && i <= 100) {
				direction="right";
			}
			actionLockerCounter =0;
		}
	}
	
	public void setDialogue() {
		dialogues[0]= "Hello , player";
		dialogues[1]= "So you've come to this island \nto find the treasure?";
		dialogues[2]= "I used to be a great wizard\n but now ... I'm a bit too\nold for taking an adventure.";
		dialogues[3]= "Well, good luck on you!";
		
	}
		
	public void speak() {
		super.speak();
	}
	
	
}
