package characters;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GameScreen;
import main.UtilityTool;

public class Characters {
	public int worldX,worldY;
	public int speed;
	public BufferedImage up1 , up2, down1, down2, left1, left2, right1,right2;
	public BufferedImage attackUP1,attackUP2 ,attackDown1, attackDown2, attackLeft1,attackLeft2,attackRight1,attackRight2;
	public String direction= "down";
	
	GameScreen gs;
	public boolean attacking = false;
	
	// walk animation
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public int dialoguesIndex=0;
	public String dialogues[] = new String[20];
	
	//hracove atributy
	public int level;
	public int maxMana;
	public int defaul_speed;
	public int mana;
	public int ammo;
	public int strength;
	public int attack;
	public int dexterity;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int price;
	public Characters currentWeapon;
	public Characters currentShield;
	public Projectile projectile;
	
	public int attackValue;
	public int defenseValue;
	public String description="";
	public int useCost;
	public int picCounter = 0;
	public int picNum = 1;
	public int knockbackCounter = 0;
	public BufferedImage image,image2,image3;
	public String name;
	
	public ArrayList<Characters> inventory = new ArrayList<>();
	public final int inventorySize = 20;
	
	public Rectangle solidRect = new Rectangle(0,0,48,48);
	public int solidRectDefaultX , solidRectDefaultY;
	public boolean collisionOn = false;
	public boolean collision =false;
	public int actionLockerCounter = 0;
	public boolean invincible = false;
	public int invincibleCount = 0;
	public int type; // 0 player 1 npc 2 monster
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public boolean alive = true;
	public boolean dying= false;
	public int dyingCounter = 0;
	public boolean hpBarOn = false;
	public int hpBarCounter = 0;
	public int shotAvailebleCount = 0;
	public boolean knockBack = false;
	public int knockBackPower = 0;
	public boolean stackable = false;
	public int amount=1;
	
	public Characters currentLight;
	public int lightRadius;
	
	//type
	public int value;
	public final int type_player=0;
	public final int type_npc=1;
	public final int type_monster=2;
	public final int type_sword=3;
	public final int type_axe=4;
	public final int type_shield=5;
	public final int type_consumable=6; // jedlé či to je
	public final int type_pickupONLY=7;
	public final int type_door=8;
	public final int type_obstacle = 9;
	public final int type_light=10;
	
	
	//character status
	public int maxLife;
	public int life;
	
	public Characters (GameScreen gs) {
		this.gs=gs;
	}
	
	
	public BufferedImage setup(String imagePath, int width , int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image=null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image=uTool.scaleImage(image, width, height);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return image;
	}
	
	public BufferedImage setupMon(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image=null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image=uTool.scaleImage(image, gs.sizeRect, gs.sizeRect);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return image;
	}
	
	public void setActionNpc() {
		
	}
	
	public void damageReaction() {

	}
	
	public int getLeftX() {
		return worldX + solidRect.x;
	}
	
	public int getRightX() {
		return worldX + solidRect.x + solidRect.width;
	}
	
	public int getTopY() {
		return worldY + solidRect.y;
	}
	
	public int getBottomY() {
		return worldY + solidRect.y + solidRect.height;
	}
	
	public int getCol() {
		return (worldX + solidRect.x)/gs.sizeRect;
	}
	
	public int getRow() {
		return (worldY + solidRect.y)/gs.sizeRect;
	}
	
	public void speak() {
		if (dialogues[dialoguesIndex] == null) {
			dialoguesIndex = 0;
		}
		gs.ui.currentDialogue = dialogues[dialoguesIndex];
		dialoguesIndex++;
		
		switch(gs.player.direction) {
		case "hore":
			direction = "dole";
			break;
		case "dole":
			direction = "hore";
			break;
		case "vlavo":
			direction = "vpravo";
			break;
		case "vpravo":
			direction = "vlavo";
			break;
		}
	}
	
	public boolean use(Characters character) {
		return false;
	}
	
	public int getDetected(Characters user, Characters target[][],String targetName) {
		int index=999;
		
		int nextWorldX= user.getLeftX();
		int nextWorldY= user.getTopY();
		
		switch(user.direction) {
		case "up": nextWorldY = user.getTopY()-1; break;
		case "down": nextWorldY= user.getBottomY()+1; break;
		case "left": nextWorldX= user.getLeftX()-1;break;
		case "right": nextWorldX= user.getRightX()+1;break;
		}
		
		int col = nextWorldX/gs.sizeRect;
		int row = nextWorldY/gs.sizeRect;
		
		for(int i =0; i < target[1].length; i++) {
			if(target[gs.currentMap][i] != null) {
				if(target[gs.currentMap][i].getCol() == col && 
						target[gs.currentMap][i].getRow() == row &&
						target[gs.currentMap][i].name.equals(targetName)) {
					index=i;
					break;
				}
			}
		}
		return index;
	}
	
	public void update() {
		
		collisionOn=false;
		gs.cManager.checkRect(this);
		gs.cManager.checkObject(this,false);
		gs.cManager.checkCharacters(this, gs.npc);
		gs.cManager.checkCharacters(this, gs.mon);
		gs.cManager.checkCharacters(this, gs.iRect);
		gs.cManager.checkCharacters(this, gs.obj);
		boolean contactPlayer = gs.cManager.checkPlayer(this);
		
		
		if(knockBack == true) {
			
			
			
			if(this.type == type_monster && contactPlayer == true) {
				damagePlayer(attack);
			}
			
			if(collisionOn == true) {
				knockbackCounter=0;
				knockBack =false;
				speed = defaul_speed;
			}else if(collisionOn == false) {
				switch(gs.player.direction) {
				case "up":worldY -= speed;break;
				case "down":worldY += speed;break;
				case "left":worldX -= speed;break;
				case "right":worldX += speed;break;
				}
			}
			
			knockbackCounter++;
			if(knockbackCounter == 10) {
				knockbackCounter =0;
				knockBack=false;
				speed=defaul_speed;
			}
			
		}else {
			setActionNpc();
			
			//if collision is false, player can move
			if(collisionOn == false) {
				switch(direction) {
				case "up":worldY -= speed;break;
				case "down":worldY += speed;break;
				case "left":worldX -= speed;break;
				case "right":worldX += speed;break;
				}
			}
		}
		
		
		
		
		
		
		spriteCounter ++;
		if (spriteCounter > 24) {
			if(spriteNum == 1) {
				spriteNum =2;
			}
			else if(spriteNum == 2) {
				spriteNum =1;
			}
			spriteCounter =0;
		}
		
		if(invincible == true) {
			invincibleCount++;
			if(invincibleCount >40) {
				invincible= false;
				invincibleCount=0;
			}
		}
		if(shotAvailebleCount < 30 ) {
			shotAvailebleCount++;
		}
	}
	
	public void interact() {
		
	}
	
	public void damagePlayer(int attack) {
		if(gs.player.invincible == false) {
			gs.playSE(6);
			int damage = attack - gs.player.defense;
			if (damage <0) {
				damage=0;
			}
			gs.player.life -= damage;
			gs.player.invincible = true;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gs.player.worldX + gs.player.screenX;
		int screenY = worldY - gs.player.worldY + gs.player.screenY;
		
		
		if(worldX + gs.sizeRect > gs.player.worldX - gs.player.screenX && 
				worldX - gs.sizeRect < gs.player.worldX + gs.player.screenX &&
				worldY + gs.sizeRect > gs.player.worldY - gs.player.screenY && 
				worldY - gs.sizeRect < gs.player.worldY + gs.player.screenY) {
			switch (direction) {
			case "up":
				if(spriteNum == 1) {
					image=up1;
				}
				if(spriteNum == 2) {
					image=up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image=down1;
				}
				if(spriteNum == 2) {
					image=down2;
				}
				break;
			case "left":
				if(spriteNum == 1) {
					image=left1;
				}
				if(spriteNum == 2) {
					image=left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image=right1;
				}
				if(spriteNum == 2) {
					image=right2;
				}
				break;
			}
			
			
			//monster HP bar
			if(type==2 && hpBarOn == true) {
				double oneScale= (double)gs.sizeRect / maxLife; // we get length rect 
				double hpBar = oneScale*life;
				
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenX-1, screenY-16, gs.sizeRect+2, 12);
				g2.setColor(Color.red);
				g2.fillRect(screenX, screenY-15, (int)hpBar, 10);
				
				
				hpBarCounter++;
				
				if(hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			
			
			
			if(invincible == true) {
				hpBarOn = true;
				hpBarCounter =0;
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
			}
			if(dying == true) {
				dyingAnim(g2);
			}
			g2.drawImage(image, screenX, screenY, null);
			
			//reset opacity
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
			
		}
	}
	
	public void dyingAnim(Graphics2D g2) {
		dyingCounter++;
		int i = 5;
		
		if(dyingCounter <= i) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0f));
		}
		if(dyingCounter > i && dyingCounter <= i*2) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		}
		if(dyingCounter > i*2 && dyingCounter <= i*3) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0f));
		}
		if(dyingCounter > i*3 && dyingCounter <= i*4) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		}
		if(dyingCounter > i*4 && dyingCounter <= i*5) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0f));
		}
		if(dyingCounter > i*5 && dyingCounter <= i*6) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		}
		if(dyingCounter > i*6 && dyingCounter <= i*7) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0f));
		}
		if(dyingCounter > i*7 && dyingCounter <= i*8) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		}
		if(dyingCounter > i*8) {
			dying=false;
			alive=false;
		}
			
	}
	
	
	public void checkDrop() {
		
	}
	public void dropItem(Characters droppedItem) {
		for(int i = 0; i < gs.obj[1].length; i++) {
			if(gs.obj[gs.currentMap][i] == null) {
				gs.obj[gs.currentMap][i] = droppedItem;
				gs.obj[gs.currentMap][i].worldX = worldX; //dead monster worldX
				gs.obj[gs.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
}
