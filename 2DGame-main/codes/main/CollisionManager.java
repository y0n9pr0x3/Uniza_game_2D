package main;

import characters.Characters;

public class CollisionManager {
	GameScreen gs;
	
	public CollisionManager(GameScreen gs) {
		this.gs = gs;
	}
	
	public void checkRect(Characters characters) {
		
		// find row and col
		int characterLeftWorldX = characters.worldX + characters.solidRect.x;
		int characterRightWorldX = characters.worldX + characters.solidRect.x + characters.solidRect.width;
		int characterTopWorldY = characters.worldY + characters.solidRect.y;
		int characterBottomWorldY = characters.worldY + characters.solidRect.y + characters.solidRect.height;
		
		int characterLeftCol = characterLeftWorldX / gs.sizeRect;
		int characterRightCol = characterRightWorldX / gs.sizeRect;
		int characterTopRow = characterTopWorldY / gs.sizeRect;
		int characterBottomRow =characterBottomWorldY / gs.sizeRect;
		
		int rectNum1 , rectNum2;
		
		switch(characters.direction) {
		case "up":
			characterTopRow = (characterTopWorldY - characters.speed)/gs.sizeRect;
			rectNum1 = gs.rectM.mapRectNum[gs.currentMap][characterLeftCol][characterTopRow];
			rectNum2= gs.rectM.mapRectNum[gs.currentMap][characterRightCol][characterTopRow];
			if(gs.rectM.rects[rectNum1].collision == true || gs.rectM.rects[rectNum2].collision == true) {
				characters.collisionOn = true;
			}
			break;
		case "down":
			characterBottomRow = (characterBottomWorldY + characters.speed)/gs.sizeRect;
			rectNum1 = gs.rectM.mapRectNum[gs.currentMap][characterLeftCol][characterBottomRow];
			rectNum2= gs.rectM.mapRectNum[gs.currentMap][characterRightCol][characterBottomRow];
			if(gs.rectM.rects[rectNum1].collision == true || gs.rectM.rects[rectNum2].collision == true) {
				characters.collisionOn = true;
			}
			break;
		case "left":
			characterLeftCol = (characterLeftWorldX - characters.speed)/gs.sizeRect;
			rectNum1 = gs.rectM.mapRectNum[gs.currentMap][characterLeftCol][characterTopRow];
			rectNum2= gs.rectM.mapRectNum[gs.currentMap][characterLeftCol][characterBottomRow];
			if(gs.rectM.rects[rectNum1].collision == true || gs.rectM.rects[rectNum2].collision == true) {
				characters.collisionOn = true;
			}
			break;
		case "right":
			characterRightCol = (characterRightWorldX + characters.speed)/gs.sizeRect;
			rectNum1 = gs.rectM.mapRectNum[gs.currentMap][characterRightCol][characterTopRow];
			rectNum2= gs.rectM.mapRectNum[gs.currentMap][characterRightCol][characterBottomRow];
			if(gs.rectM.rects[rectNum1].collision == true || gs.rectM.rects[rectNum2].collision == true) {
				characters.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkCharacters(Characters character , Characters[][] target) {
		int index =999;
		
		for(int i = 0; i < target[1].length; i++) {
			if(target[gs.currentMap][i] !=null) {
				
				//get characters solid rect position
				character.solidRect.x = character.worldX + character.solidRect.x;
				character.solidRect.y = character.worldY + character.solidRect.y;
				
				//get the objects solid rect position
				target[gs.currentMap][i].solidRect.x = target[gs.currentMap][i].worldX + target[gs.currentMap][i].solidRect.x;
				target[gs.currentMap][i].solidRect.y = target[gs.currentMap][i].worldY + target[gs.currentMap][i].solidRect.y;
				
				switch(character.direction) {
				case "up":
					character.solidRect.y -= character.speed; 	//intersects = checking two rectangles if it touching
					break;
				case "down":
					character.solidRect.y += character.speed;
					break;
				case "left":
					character.solidRect.x -= character.speed;
					break;
				case "right":
					character.solidRect.x += character.speed;
					break;
				}
				if(character.solidRect.intersects(target[gs.currentMap][i].solidRect)) {
					if(target[gs.currentMap][i] != character) {
						character.collisionOn=true;
						index = i;
					}
				}
				character.solidRect.x = character.solidRectDefaultX;
				character.solidRect.y = character.solidRectDefaultY;
				target[gs.currentMap][i].solidRect.x = target[gs.currentMap][i].solidRectDefaultX;
				target[gs.currentMap][i].solidRect.y = target[gs.currentMap][i].solidRectDefaultY;
			}
		}
		
		return index;
		
	}
	
	public int checkObject(Characters character , boolean player) {
		int index =999;
		
		for(int i = 0; i < gs.obj[1].length; i++) {
			if(gs.obj[gs.currentMap][i] !=null) {
				
				//get characters solid rect position
				character.solidRect.x = character.worldX + character.solidRect.x;
				character.solidRect.y = character.worldY + character.solidRect.y;
				
				//get the objects solid rect position
				gs.obj[gs.currentMap][i].solidRect.x = gs.obj[gs.currentMap][i].worldX + gs.obj[gs.currentMap][i].solidRect.x;
				gs.obj[gs.currentMap][i].solidRect.y = gs.obj[gs.currentMap][i].worldY + gs.obj[gs.currentMap][i].solidRect.y;
				
				switch(character.direction) {
				case "up":
					character.solidRect.y -= character.speed; 	//intersects = checking two rectangles if it touching
					break;
				case "down":
					character.solidRect.y += character.speed;
					break;
				case "left":
					character.solidRect.x -= character.speed;
					break;
				case "right":
					character.solidRect.x += character.speed;
					break;
				}
				
				if(character.solidRect.intersects(gs.obj[gs.currentMap][i].solidRect)) {
					if(gs.obj[gs.currentMap][i].collision == true) {
						character.collisionOn=true;
					}
					if(player == true) {
						index = i;
					}
				}
				
				character.solidRect.x = character.solidRectDefaultX;
				character.solidRect.y = character.solidRectDefaultY;
				gs.obj[gs.currentMap][i].solidRect.x = gs.obj[gs.currentMap][i].solidRectDefaultX;
				gs.obj[gs.currentMap][i].solidRect.y = gs.obj[gs.currentMap][i].solidRectDefaultY;
			}
		}
		
		return index;
	}
	
	public boolean checkPlayer(Characters character ) {
		boolean contactPlayer = false;
		//get characters solid rect position
		character.solidRect.x = character.worldX + character.solidRect.x;
		character.solidRect.y = character.worldY + character.solidRect.y;
		
		//get the objects solid rect position
		gs.player.solidRect.x = gs.player.worldX + gs.player.solidRect.x;
		gs.player.solidRect.y = gs.player.worldY + gs.player.solidRect.y;
		
		switch(character.direction) {
		case "up":
			character.solidRect.y -= character.speed; 	//intersects = checking two rectangles if it touching
			break;
		case "down":
			character.solidRect.y += character.speed;
			break;
		case "left":
			character.solidRect.x -= character.speed;
			break;
		case "right":
			character.solidRect.x += character.speed;
			break;
		}

		if(character.solidRect.intersects(gs.player.solidRect)) {
			character.collisionOn=true;
			contactPlayer = true;
		}
		
		character.solidRect.x = character.solidRectDefaultX;
		character.solidRect.y = character.solidRectDefaultY;
		gs.player.solidRect.x = gs.player.solidRectDefaultX;
		gs.player.solidRect.y = gs.player.solidRectDefaultY;
		
		return contactPlayer;
	}
}
