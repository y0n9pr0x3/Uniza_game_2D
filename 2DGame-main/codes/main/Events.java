package main;

import characters.Characters;

public class Events {
	GameScreen gs;
	EventRect eveRect[][][];
	int previousEveX,previousEveY;
	boolean canTouchEvent = true;
	int tempMap, tempCol , tempRow;
	
	public Events(GameScreen gs) {
		this.gs=gs;
		eveRect = new EventRect[gs.maxMap][gs.maxWorldCol][gs.maxWorldRow];
		int map=0;
		int col=0;
		int row=0;
		while (map < gs.maxMap && col <gs.maxWorldCol && row < gs.maxWorldRow) {
			eveRect[map][col][row] = new EventRect();
			eveRect[map][col][row].x= 23;
			eveRect[map][col][row].y=  23;
			eveRect[map][col][row].width = 2;
			eveRect[map][col][row].height=2;
			eveRect[map][col][row].eveRectDefaulX = eveRect[map][col][row].x;
			eveRect[map][col][row].eveRectDefaulY= eveRect[map][col][row].y;
			
			col++;
			if(col == gs.maxWorldCol) {
				col =0;
				row++;
				
				if(row == gs.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
		
	}
	
	public void checkEvent() {
		
		// check if the player is more than 1 rect away from last event
		int xDis = Math.abs(gs.player.worldX - previousEveX);
		int yDis = Math.abs(gs.player.worldY - previousEveY);
		int dis = Math.max(xDis, yDis);
		if(dis > gs.sizeRect) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			
			if(hit(0,27,16,"right")== true) {holeTrap(gs.dialogState);}
			//else if(hit(0,23,19,"any")== true) {holeTrap(gs.dialogState);}
			
			else if(hit(0,23,17,"any")== true) {teleport(1,12,13);}
			
			else if(hit(1,12,13,"any")== true) {teleport(0,23,17);}
			
			else if(hit(1,12,9,"up")==true) { speak(gs.npc[1][0]);}
			
			//if(hit(27,16,"right")== true) {teleport(gp.dialogState);}
			
			//if(hit(0,23,12,"up")== true) {healing(23,12,gs.dialogState);}
		}
	}
	
	public boolean hit(int map, int col,int row,String reqDirection) {
		boolean hit = false;
		
		if(map == gs.currentMap) {
			gs.player.solidRect.x= gs.player.worldX+gs.player.solidRect.x;
			gs.player.solidRect.y= gs.player.worldY+gs.player.solidRect.y;
			
			eveRect[map][col][row].x = col*gs.sizeRect + eveRect[map][col][row].x;
			eveRect[map][col][row].y = row*gs.sizeRect + eveRect[map][col][row].y;
			
			if(gs.player.solidRect.intersects(eveRect[map][col][row]) && eveRect[map][col][row].eveDone == false) {
				if(gs.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;
					previousEveX = gs.player.worldX;
					previousEveY= gs.player.worldY;
					
				}
			}
			gs.player.solidRect.x= gs.player.solidRectDefaultX;
			gs.player.solidRect.y= gs.player.solidRectDefaultY;
			eveRect[map][col][row].x = eveRect[map][col][row].eveRectDefaulX;
			eveRect[map][col][row].y = eveRect[map][col][row].eveRectDefaulY;
		}
		return hit;
	}
	
	public void holeTrap(int gameState) {
		gs.gameStates = gameState;
		gs.playSE(6);
		
		gs.ui.currentDialogue = "You have fallen \ninto a hole!";
		gs.player.life -=1;
		// eveRect[col][row].eveDone = true;  event once time
		canTouchEvent = false;
	}
	
	public void healing(int col , int row,int gameState) {
		
		if(gs.keys.enterPress == true ) {
			if(gs.player.life < gs.player.maxLife) {
				gs.gameStates = gameState;
				gs.player.attackCanceled =true;
				gs.playSE(2);
				gs.ui.currentDialogue = "You drank a Borovička!\nYour health has been \nrecovered!";
				gs.player.life = gs.player.maxLife;
				gs.player.mana = gs.player.maxMana;
				gs.dObject.setMonster();
			}else if(gs.player.life == gs.player.maxLife){
				gs.gameStates = gameState;
				gs.ui.currentDialogue = "You are healty , go to study!";
			}
			
		}
	}
	
	
	public void teleport(int map, int col , int row) {
		gs.gameStates = gs.transitionState;
		tempMap= map;
		tempCol= col;
		tempRow= row;
		
		
		//gs.currentMap = map;
		//gs.player.worldX = gs.sizeRect*col;
		//gs.player.worldY = gs.sizeRect*row;
		//previousEveX = gs.player.worldX;
		//previousEveY= gs.player.worldY;
		canTouchEvent = false;
		gs.playSE(13);
		
	}
	
	public void speak(Characters character) {
		if(gs.keys.enterPress == true) {
			gs.gameStates = gs.dialogState;
			gs.player.attackCanceled = true;
			character.speak();
		}
	}
	
}
