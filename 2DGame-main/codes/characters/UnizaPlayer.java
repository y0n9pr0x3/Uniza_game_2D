package characters;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GameScreen;
import main.Keys;
import objects.OBJ_Fireball;
import objects.OBJ_Key;
import objects.OBJ_Sword_normal;
import objects.OBJ_Wood_Shield;

public class UnizaPlayer extends Characters{
	//GameScreen gs;
	Keys keys;
	
	public final int screenX;
	public final int screenY;
	//public int hasKey=0;
	public boolean attackCanceled=false;
	public boolean lightUpdate = false;
	
	OBJ_Key key = new OBJ_Key(gs);
	
	public UnizaPlayer(GameScreen gs , Keys keys) {
		super(gs);
		this.keys= keys;
		
		screenX = gs.screenWidth/2 - (gs.sizeRect/2);
		screenY = gs.screenHeight/2 - (gs.sizeRect/2);
		
		solidRect = new Rectangle(); // x,y , width, height
		solidRect.x = 8;
		solidRect.y = 16;
		solidRect.width = 32;
		solidRect.height = 32;
		solidRectDefaultX = solidRect.x;
		solidRectDefaultY = solidRect.y;
		
		//attackArea.width = 36;
		//attackArea.height = 36;
		
		setDefault();
		getImage();
		getAttackImage();
		setItems();
	}
	
	public void setDefault() {
		worldX=gs.sizeRect *23;
		worldY=gs.sizeRect * 21;
		//worldX=gs.sizeRect *12;
		//worldY=gs.sizeRect * 13;
		defaul_speed = 4;
		speed = defaul_speed;
		level=1;
		direction = "down";
		maxLife = 6;
		life=maxLife;
		maxMana = 4;
		mana=maxMana;
		strength=1;
		dexterity=1;
		exp=0;
		nextLevelExp=5;
		coin=500;
		 
	
		currentWeapon=new OBJ_Sword_normal(gs);
		currentShield= new OBJ_Wood_Shield(gs);
		projectile = new OBJ_Fireball(gs);
		
		attack= getAttack();
		defense= getDefense();
	}
	
	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(key);
		//inventory.add(key);
	}
	
	public void setDefaultPosition() {
		worldX=gs.sizeRect *23;
		worldY=gs.sizeRect * 21;
		direction = "down";
		
	}
	
	public void restoreLifeAndMana() {
		life=maxLife;
		mana=maxMana;
		invincible= false;
	}
	
	public int getAttack() {
		attackArea= currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	
	public void getImage() {
		up1 = setup("/player/boy_up_1",gs.sizeRect,gs.sizeRect);
		up2 = setup("/player/boy_up_2",gs.sizeRect,gs.sizeRect);
		down1 = setup("/player/boy_down_1",gs.sizeRect,gs.sizeRect);
		down2 = setup("/player/boy_down_2",gs.sizeRect,gs.sizeRect);
		left1 = setup("/player/boy_left_1",gs.sizeRect,gs.sizeRect);
		left2 =  setup("/player/boy_left_2",gs.sizeRect,gs.sizeRect);
		right1 = setup("/player/boy_right_1",gs.sizeRect,gs.sizeRect);
		right2 = setup("/player/boy_right_2",gs.sizeRect,gs.sizeRect);
	}
	
	public void getAttackImage() {
		if(currentWeapon.type == type_sword) {
			attackUP1 = setup("/player/boy_attack_up_1",gs.sizeRect,gs.sizeRect*2);
			attackUP2 = setup("/player/boy_attack_up_2",gs.sizeRect,gs.sizeRect*2);
			attackDown1 = setup("/player/boy_attack_down_1",gs.sizeRect,gs.sizeRect*2);
			attackDown2 = setup("/player/boy_attack_down_2",gs.sizeRect,gs.sizeRect*2);
			attackLeft1 = setup("/player/boy_attack_left_1",gs.sizeRect*2,gs.sizeRect);
			attackLeft2= setup("/player/boy_attack_left_2",gs.sizeRect*2,gs.sizeRect);
			attackRight1 = setup("/player/boy_attack_right_1",gs.sizeRect*2,gs.sizeRect);
			attackRight2 = setup("/player/boy_attack_right_2",gs.sizeRect*2,gs.sizeRect);
		}
		if(currentWeapon.type == type_axe) {
			attackUP1 = setup("/player/boy_axe_up_1",gs.sizeRect,gs.sizeRect*2);
			attackUP2 = setup("/player/boy_axe_up_2",gs.sizeRect,gs.sizeRect*2);
			attackDown1 = setup("/player/boy_axe_down_1",gs.sizeRect,gs.sizeRect*2);
			attackDown2 = setup("/player/boy_axe_down_2",gs.sizeRect,gs.sizeRect*2);
			attackLeft1 = setup("/player/boy_axe_left_1",gs.sizeRect*2,gs.sizeRect);
			attackLeft2= setup("/player/boy_axe_left_2",gs.sizeRect*2,gs.sizeRect);
			attackRight1 = setup("/player/boy_axe_right_1",gs.sizeRect*2,gs.sizeRect);
			attackRight2 = setup("/player/boy_axe_right_2",gs.sizeRect*2,gs.sizeRect);
		}
		
	}
	
	public void getSleepImage(BufferedImage image) {
		up1 = image;
		up2 = image;
		down1 = image;
		down2 = image;
		left1 = image;
		left2 =  image;
		right1 = image;
		right2 = image;
	}
	
	public void update() {
		if(attacking == true) {
			attackingPlayer();
			
			
			
		}else if(keys.upPress== true || keys.downPress== true || 
				keys.leftPress== true || keys.rightPress== true || keys.enterPress == true) {
			
			if(keys.upPress == true) {
				direction = "up";
			}
			else if(keys.downPress == true) {
				direction = "down";
			}
			else if(keys.leftPress == true) {
				direction = "left";
			}
			else if(keys.rightPress == true) {
				direction = "right";
			}
			
			//check collision
			collisionOn = false;
			gs.cManager.checkRect(this);
			
			//check object collision
			int objIndex =gs.cManager.checkObject(this, true);
			pickedUp(objIndex);
			
			//check NPC collision
			int npcIndex = gs.cManager.checkCharacters(this, gs.npc);
			interactNPC(npcIndex);
			
			//check interact rect
			int iRectIndex = gs.cManager.checkCharacters(this, gs.iRect);
			
			//check monster collision
			int monIndex = gs.cManager.checkCharacters(this, gs.mon);
			contactMon(monIndex);
			
			//check events
			gs.events.checkEvent();
			
			
			//if collision is false , player cant move
			if(collisionOn == false && keys.enterPress == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			if(keys.enterPress == true && attackCanceled == false) {
				gs.playSE(7);
				attacking = true;
				spriteCounter=0;
			}
			attackCanceled=false;
			gs.keys.enterPress = false;
			
			picCounter++;
			if(picCounter > 12) {
				if(picNum == 1) {
					picNum =2;
				}else if(picNum ==2) {
					picNum=1;
				}
				picCounter =0;
			}
		}
		
		if(gs.keys.shotKeyPress == true && projectile.alive == false 
				&& shotAvailebleCount == 30 && projectile.haveResource(this)== true) {
			
			//set default coordinate , direction and user
			projectile.set(worldX,worldY,direction,true,this);
			
			//subtract mana
			projectile.subtractResource(this);
			
			//projectile
			for(int i=0; i < gs.projectile[1].length; i++) {
				if(gs.projectile[gs.currentMap][i] == null) {
					gs.projectile[gs.currentMap][i]= projectile;
					break;
				}
			}
			shotAvailebleCount = 0;
			gs.playSE(10);
		}
		
		
		
		
		
		//this needs to be outside of key if statement
		if(invincible == true) {
			invincibleCount++;
			if(invincibleCount >60) {
				invincible= false;
				invincibleCount=0;
			}
		}
		if(shotAvailebleCount < 30) {
			shotAvailebleCount++;
		}
		
		if(life > maxLife) {
			life = maxLife;
		}
		
		if(mana > maxMana) {
			mana = maxMana;
		}
		
		if(life<= 0) {
			gs.gameStates = gs.gameOverState;
			gs.stopMusic();
			gs.playSE(12);
		}
	}
	
	public void contactMon(int i) {
		if(i != 999) {
			if(invincible == false && gs.mon[gs.currentMap][i].dying == false) {
				gs.playSE(6);
				int damage =gs.mon[gs.currentMap][i].attack - defense;
				if (damage <0) {
					damage=0;
				}
				
				life-= damage;
				invincible = true;
			}
		}
	
	}
	
	public int searchItemInInventory(String itemName) {
		
		int itemIndex = 999;
		for(int i=0; i <inventory.size(); i++) {
			if(inventory.get(i).name.equals(itemName)) {
				itemIndex =i;
				break;
			}
		}
		return itemIndex;
	}
	
	public boolean canObtainItem(Characters item) {
		boolean canObtain = false;
		
		//if it stackeble
		if(item.stackable== true) {
			int index = searchItemInInventory(item.name);
			
			if(index != 999) {
				inventory.get(index).amount++;
				canObtain=true;
			}else {//new item
				if(inventory.size() != inventorySize) {
					inventory.add(item);
					canObtain=true;
				}
				
			}
		}else { // not stackeble
			if(inventory.size() != inventorySize) {
				inventory.add(item);
				canObtain=true;
			}
		}
		return canObtain;
	}
	
	public void attackingPlayer(){
		spriteCounter++;
		if(spriteCounter <= 5) {
			spriteNum =1;
		}
		if(spriteCounter > 5 && spriteCounter <=25 ) {
			spriteNum=2;
			
			
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth= solidRect.width;
			int solidAreaHeight= solidRect.height;
			
			switch(direction) {
			case "up":	worldY -= attackArea.height;
				break;
			case "down":worldY += attackArea.height;
				break;
			case "left":	worldX -= attackArea.width;
				break;
			case "right":	worldX += attackArea.width;
				break;
			}
			
			solidRect.width = attackArea.width;
			solidRect.height = attackArea.height;
			
			int monsterIndex = gs.cManager.checkCharacters(this, gs.mon);
			damageMonster(monsterIndex,attack,currentWeapon.knockBackPower);
			
			int iRectIndex = gs.cManager.checkCharacters(this, gs.iRect);
			damageInteractRect(iRectIndex);
			
			int projectileIndex = gs.cManager.checkCharacters(this, gs.projectile);
			damageProjectile(projectileIndex);
			
			worldX= currentWorldX;
			worldY = currentWorldY;
			
			solidRect.width = solidAreaWidth;
			solidRect.height= solidAreaHeight;
			
			
			
		}
		if(spriteCounter > 25 ) {
			spriteNum=1;
			spriteCounter=0;
			attacking=false;
		}
		
	}
	
	public void knockBack(Characters character, int knockBackPower) {
		character.direction = direction;
		character.speed += knockBackPower;
		character.knockBack=true;
	}
	
	public void damageInteractRect(int i) {
		if(i != 999 && gs.iRect[gs.currentMap][i].destruct == true && gs.iRect[gs.currentMap][i].isAxe(this) == true 
				&& gs.iRect[gs.currentMap][i].invincible == false) {
			
			gs.iRect[gs.currentMap][i].playSE();
			gs.iRect[gs.currentMap][i].life--;
			gs.iRect[gs.currentMap][i].invincible=true;
			
			if(gs.iRect[gs.currentMap][i].life == 0) {
				gs.iRect[gs.currentMap][i] = gs.iRect[gs.currentMap][i].getDownTree();
				gs.ui.addMessage("You destroyed old Oak!");
				//gs.ui.addMessage("Exp + " + 1);
				//exp += 2;
				gs.playSE(3);
			}
		}
	}
	
	public void damageMonster(int i, int attack, int knockBackPower) {
		if(i != 999) {
			if(gs.mon[gs.currentMap][i].invincible == false) {
				gs.playSE(5);
				
				if(knockBackPower > 0) {
					knockBack(gs.mon[gs.currentMap][i],knockBackPower);
				}
				
				
				
				int damage = attack - gs.mon[gs.currentMap][i].defense;
				if (damage <0) {
					damage=0;
				}
				gs.mon[gs.currentMap][i].life -= damage;
				gs.ui.addMessage(damage +" rozdávaš!");
				
				gs.mon[gs.currentMap][i].invincible = true;
				gs.mon[gs.currentMap][i].damageReaction();
				
				if(gs.mon[gs.currentMap][i].life <= 0) {
					gs.mon[gs.currentMap][i].dying = true;
					gs.ui.addMessage("Zabeu si " +gs.mon[gs.currentMap][i].name + "!");
					gs.ui.addMessage("Exp + " +gs.mon[gs.currentMap][i].exp);
					exp += gs.mon[gs.currentMap][i].exp;
					checkLevelUp();
				}
			}
	
		}
	}
	
	public void damageProjectile(int i) {
		if(i != 999) {
			Characters projectile = gs.projectile[gs.currentMap][i];
			projectile.alive = false;
		}
	}
	
	public void checkLevelUp() {
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp * 2;
			
			//maxLife += 1;
			life = maxLife;
			mana=maxMana;
			
			
			
			strength ++;
			dexterity ++;
			attack = getAttack();
			defense = getDefense();
			gs.playSE(8);
			gs.gameStates = gs.dialogState;
			gs.ui.currentDialogue = "Dostal si nový level, neposer sa z toho!";
		}
	}
	
	public void pickedUp(int i) {
		if(i != 999) {
			//items
			if(gs.obj[gs.currentMap][i].name == "Jordany") {
				gs.obj[gs.currentMap][i]= null;
				gs.playSE(1);
				gs.ui.addMessage("Nové jordany, Jou!");
				gs.player.speed += 3;
			}else if(gs.obj[gs.currentMap][i].type== type_pickupONLY) {
				gs.obj[gs.currentMap][i].use(this);
				gs.obj[gs.currentMap][i]=null;
				
			}else if(gs.obj[gs.currentMap][i].type == type_obstacle) {
				gs.player.collision = true;
				if(keys.enterPress == true) {
					attackCanceled = true;
					gs.obj[gs.currentMap][i].interact();
					
				}
				
				
			}else{
				//inventory items
				String text;
				
				if(canObtainItem(gs.obj[gs.currentMap][i]) == true) {
					//inventory.add(gs.obj[gs.currentMap][i]);
					gs.playSE(1);
					text = "Získal si "+gs.obj[gs.currentMap][i].name + "!";
					
				}else {
					text= "Si plný , predaj dačo!";
				}
				gs.ui.addMessage(text);
				gs.obj[gs.currentMap][i] =null;
			}
			
		}
	}
	
	public void interactNPC(int i){
		if(gs.keys.enterPress == true) {
			if(i != 999) {
				attackCanceled = true;
				gs.gameStates = gs.dialogState;
				gs.npc[gs.currentMap][i].speak();
			}
		}
	}
	
	public void selectItem() {
		int itemIndex = gs.ui.getItemIndexOnSlot(gs.ui.playerSlotCol,gs.ui.playerSlotRow);
		if(itemIndex < inventory.size()) {
			Characters selectedItem = inventory.get(itemIndex);
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack=getAttack();
				getAttackImage();
			}
			if(selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense=getDefense();
			}
			if(selectedItem.type == type_light) {
				if(currentLight == selectedItem) {
					currentLight = null;
				}else {
					currentLight = selectedItem;
				}
				lightUpdate= true;
			}
			if(selectedItem.type == type_consumable) {
				if(selectedItem.use(this) == true) {
					if(selectedItem.amount > 1) {
						selectedItem.amount--;
					}else {
						inventory.remove(itemIndex);
					}
				}
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY= screenY;
		
		switch (direction) {
		case "up":
			if(attacking == false) {
				if(picNum == 1) {image=up1;}
				if(picNum == 2) {image=up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gs.sizeRect;
				if(picNum == 1) {image=attackUP2;}
				if(picNum == 2) {image=attackUP2;}
			}
			
			break;
		case "down":
			if(attacking == false) {
				if(picNum == 1) {image=down1;}
				if(picNum == 2) {image=down2;}
			}
			if(attacking == true) {
				if(picNum == 1) {image=attackDown2;}
				if(picNum == 2) {image=attackDown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if(picNum == 1) {image=left1;}
				if(picNum == 2) {image=left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gs.sizeRect;
				if(picNum == 1) {image=attackLeft2;}
				if(picNum == 2) {image=attackLeft2;}
			}
			break;
		case "right":
			if(attacking == false) {
				if(picNum == 1) {image=right1;}
				if(picNum == 2) {image=right2;}
			}
			if(attacking == true) {
				if(picNum == 1) {image=attackRight2;}
				if(picNum == 2) {image=attackRight2;}
			}
			break;
		}
		
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
		}
		g2.drawImage(image, tempScreenX, tempScreenY,null);
		
		//reset opacity
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
	}
}
