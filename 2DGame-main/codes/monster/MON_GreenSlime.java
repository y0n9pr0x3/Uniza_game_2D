package monster;

import java.util.Random;

import characters.Characters;
import main.GameScreen;
import objects.OBJ_Heart;
import objects.OBJ_ManaBEER;
import objects.OBJ_Rock;
import objects.OBJ_Uniza_Coin;

public class MON_GreenSlime extends Characters{
	GameScreen gs;
	public MON_GreenSlime(GameScreen gs) {
		super(gs);
		this.gs=gs;
		type= type_monster;
		name= "Green Slime";
		speed = 1;
		maxLife = 3;
		life=maxLife;
		attack = 4;
		defense= 0;
		ammo = 10;
		
		exp = 2;
		projectile = new OBJ_Rock(gs);
		
		
		
		
		solidRect.x =3;
		solidRect.y = 18;
		
		
		
		
		
		
		
		solidRect.width = 42;
		solidRect.height = 30;
		solidRectDefaultX = solidRect.x;
		solidRectDefaultY = solidRect.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setupMon("/monster/greenslime_down_1");
		up2 = setupMon("/monster/greenslime_down_2");
		down1 = setupMon("/monster/greenslime_down_1");
		down2 = setupMon("/monster/greenslime_down_2");
		left1 = setupMon("/monster/greenslime_down_1");
		left2 = setupMon("/monster/greenslime_down_2");
		right1 = setupMon("/monster/greenslime_down_1");
		right2 = setupMon("/monster/greenslime_down_2");
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

		int i = new Random().nextInt(100)+1;
		if(i > 99 && projectile.alive == false && shotAvailebleCount == 30) {
			projectile.set(worldX, worldY, direction, true, this);
			gs.projectileList.add(projectile);
			shotAvailebleCount=0;
		}
	}
	
	public void damageReaction() {
		actionLockerCounter=0;
		direction = gs.player.direction;
	}
	
	public void checkDrop() {
		
		
		int i = new Random().nextInt(100)+1;
		
		//set monster drop
		
		if(i < 50) {
			dropItem(new OBJ_Uniza_Coin(gs));
		}
		if(i >= 50 && i < 70) {
			dropItem(new OBJ_Heart(gs));
		}
		if(i >= 70 && i < 100) {
			dropItem(new OBJ_ManaBEER(gs));
		}
	}
}
