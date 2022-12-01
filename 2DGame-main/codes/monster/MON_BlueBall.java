package monster;

import java.util.Random;

import characters.Characters;
import main.GameScreen;
import objects.OBJ_Heart;
import objects.OBJ_ManaBEER;
import objects.OBJ_Rock;
import objects.OBJ_Uniza_Coin;

public class MON_BlueBall extends Characters{
	GameScreen gs;
	public MON_BlueBall(GameScreen gs) {
		super(gs);
		this.gs=gs;
		type= type_monster;
		name= "Blue Ball";
		defaul_speed=1;
		speed = defaul_speed;
		maxLife = 3;
		life=maxLife;
		attack = 4;
		defense= 0;
		exp=2;
		ammo = 10;
		
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
		up1 = setup("/monster/1",58,58);
		up2 = setup("/monster/1",58,58);
		down1 = setup("/monster/1",58,58);
		down2 = setup("/monster/1",58,58);
		left1 = setup("/monster/1",58,58);
		left2 = setup("/monster/1",58,58);
		right1 = setup("/monster/1",58,58);
		right2 = setup("/monster/1",58,58);
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
			//gs.projectile.add(projectile);
			
			for(int o = 0; o < gs.projectile[1].length;o++) {
				if(gs.projectile[gs.currentMap][o] == null) {
					gs.projectile[gs.currentMap][o] = projectile;
					break;
				}
			}
			shotAvailebleCount=0;
		}
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
