package characters;

import main.GameScreen;

public class Projectile extends Characters{
	
	Characters user;

	public Projectile(GameScreen gs) {
		super(gs);
		
	}
	
	public void set(int worldX , int worldY, String direction, boolean alive, Characters user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction=direction;
		this.alive=alive;
		this.user=user;
		this.life = this.maxLife;	//reset life projectile
		
	}
	
	public void update() {
		
		
		if(user == gs.player ) {
			int monsterIndex = gs.cManager.checkCharacters(this, gs.mon);
			if(monsterIndex != 999) {
				gs.player.damageMonster(monsterIndex,attack);
				alive=false;
			}
		}
		if(user != gs.player) {
			boolean contactPlayer = gs.cManager.checkPlayer(this);
			if(gs.player.invincible == false && contactPlayer == true) {
				damagePlayer(attack);
				alive=false;
			}
		}
		
		switch(direction) {
		case "up":		worldY -= speed; break;
		case "down":	worldY += speed; break;
		case "left":	worldX -= speed; break;
		case "right":	worldX += speed; break;
		}
		
		life--;
		if(life <= 0) {
			alive=false;
		}
		
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum =2;
			}else if(spriteNum == 2) {
				spriteNum =1;
			}
			spriteCounter=0;
		}
	}
	
	
	public boolean haveResource(Characters user) {
		boolean haveResource = false;
		return haveResource;
	}
	
	public void subtractResource(Characters user) {}
}
