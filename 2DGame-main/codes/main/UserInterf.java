package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.print.attribute.Size2DSyntax;

import characters.Characters;
import objects.OBJ_Heart;
import objects.OBJ_ManaBEER;
import objects.OBJ_Uniza_Coin;

public class UserInterf {

	GameScreen gs;
	UtilityTool uTool = new UtilityTool();
	Graphics2D g2;
	public Font purisaB;
	BufferedImage keyImage;
	BufferedImage uniza_heartF,ovca, uniza_heartH,uniza_heartB,beer_full ,beer_blank,unyza,monster,stuff11,stuff22,coin;
	public boolean messageOn = false;
	//public String message = "";
	//int messageCounter=0;
	ArrayList<String>message = new ArrayList<>();
	ArrayList<Integer>messageCount = new ArrayList<>();
	public boolean gameFinish=false;
	double playTIME;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	public String currentDialogue = "";
	public int selectedNum = 0;
	public int titleScreenState=0; // 0 firstScreen,  1 secondScreen
	public int playerSlotCol=0;
	public int playerSlotRow=0;
	public int npcSlotRow=0;
	public int npcSlotCol=0;
	int subState=0;
	int tranCounter =0;
	public Characters npc;
	
	
	public UserInterf(GameScreen gs) {
		this.gs=gs;
		
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/purisa.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//hearts
		Characters heart = new OBJ_Heart(gs);
		uniza_heartF = heart.image;
		uniza_heartH = heart.image2;
		uniza_heartB = heart.image3;
		
		Characters beer = new OBJ_ManaBEER(gs);
		beer_full = beer.image;
		beer_blank= beer.image2;
		
		Characters coin1= new OBJ_Uniza_Coin(gs);
		coin=coin1.down1;
	}
	
	public void addMessage(String text) {
		message.add(text);
		messageCount.add(0);
	}
	
	
	public void draw(Graphics2D g2) {
		this.g2=g2;
		
		g2.setColor(Color.white);
		
		//title
		if(gs.gameStates == gs.titleState) {
			drawTittleScreen();
		}
		
		//play
		if(gs.gameStates == gs.huntState) {
			drawLife();
			drawMessage();
		}
		
		//pause
		if(gs.gameStates == gs.pauseState) {
			drawLife();
			drawPause();
		}
		
		//dialogue
		if(gs.gameStates == gs.dialogState) {
			//drawLife();
			drawDialogue();
		}
		
		//character
		if(gs.gameStates == gs.characterState) {
			drawCharScreen();
			drawInventory(gs.player, true);
		}
		
		//options
		if(gs.gameStates == gs.optionState) {
			drawOptionsScreen();
		}
		
		//gameOver
		if(gs.gameStates == gs.gameOverState) {
			gs.stopMusic();
			drawGameOverScreen();
		}
		
		//transition state
		if(gs.gameStates == gs.transitionState) {
			drawTransition();
		}
		
		//transition state
		if(gs.gameStates == gs.tradingState) {
			drawTradingScreen();
		}
		
		//sleep state
		if(gs.gameStates == gs.sleepState) {
			drawSleepScreen();
		}
	}
	
	public void drawLife() {
		
		int x = gs.sizeRect;
		int y = gs.sizeRect - (gs.sizeRect/2);
		
		int i=0;
		
		//draw max life
		while(i <gs.player.maxLife/2) {
			g2.drawImage(uniza_heartB, x, y, null);
			i++;
			x+= gs.sizeRect;
		}
		
		//reset
		x = gs.sizeRect;
		y = gs.sizeRect - (gs.sizeRect/2);
		
		i=0;
		
		//current life
		while(i<gs.player.life) {
			g2.drawImage(uniza_heartH, x, y, null);
			i++;
			if(i <gs.player.life) {
				g2.drawImage(uniza_heartF, x, y, null);
			}
			i++;
			x += gs.sizeRect;
		}
		
		
		//max beer
		x = gs.sizeRect;
		y = gs.sizeRect +20;
		i=0;
		
		while(i < gs.player.maxMana) {
			g2.drawImage(beer_blank, x, y, null);
			i++;
			x+=35;
		}
		
		//beer
		x = gs.sizeRect;
		y = gs.sizeRect +20;
		i=0;
		
		while(i < gs.player.mana) {
			g2.drawImage(beer_full, x, y, null);
			i++;
			x+=35;
		}
		
	}
	
	public void drawTransition() {
		tranCounter++;
		g2.setColor(new Color(0,0,0,tranCounter*5));
		g2.fillRect(0, 0, gs.screenWidth, gs.screenHeight);
		
		if(tranCounter == 50) {
			tranCounter= 0;
			gs.gameStates = gs.huntState;
			gs.currentMap = gs.events.tempMap;
			gs.player.worldX = gs.sizeRect * gs.events.tempCol;
			gs.player.worldY = gs.sizeRect * gs.events.tempRow;
			gs.events.previousEveX = gs.player.worldX;
			gs.events.previousEveY = gs.player.worldY;
		}
	}
	
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(purisaB);
		g2.setFont(g2.getFont().deriveFont(24F));
		
		//sub window
		
		int frameX = gs.sizeRect *6;
		int frameY= gs.sizeRect;
		int frameWidth= gs.sizeRect *8;
		int frameHeight= gs.sizeRect *10;
		
		drawSubWind(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: optionsTop(frameX,frameY); break;
		case 1: options_fullScrNotif(frameX, frameY);break;
		case 2: options_control(frameX, frameY); break;
		case 3: options_endGameConf(frameX, frameY); break;
		}
		
		gs.keys.enterPress = false;
	}
	
	public void optionsTop(int frameX, int frameY) {
		int textX;
		int textY;
		
		String text = "MOŽNOSTI";
		
		textX= getXforCenteredText(text);
		textY= frameY + gs.sizeRect;
		g2.drawString(text, textX, textY);
		
		//full screen
		textX= frameX + gs.sizeRect;
		textY += gs.sizeRect*2;
		g2.drawString("FULL SCREEN" , textX, textY);
		if(selectedNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gs.keys.enterPress == true) {
				if(gs.fullscreenOn == false) {
					gs.fullscreenOn = true;
				}else if(gs.fullscreenOn == true) {
					gs.fullscreenOn = false;
				}
				subState = 1;
			}
		}
		
		//music
		textY+=gs.sizeRect;
		g2.drawString("HUDBA", textX, textY);
		if(selectedNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		
		//se
		textY+=gs.sizeRect;
		g2.drawString("EFEKTY", textX, textY);
		if(selectedNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		
		//control
		textY+=gs.sizeRect;
		g2.drawString("OVLÁDANIE", textX, textY);
		if(selectedNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gs.keys.enterPress == true) {
				subState =2;
				selectedNum=0;
			}
		}
		
		
		//end game
		textY+=gs.sizeRect;
		g2.drawString("KONEC HRY", textX, textY);
		if(selectedNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gs.keys.enterPress == true) {
				subState=3;
				selectedNum = 0;
			}
		}
		
		
		//back
		textY+=gs.sizeRect*2;
		g2.drawString("SPÄŤ", textX, textY);
		if(selectedNum == 5) {
			g2.drawString(">", textX-25, textY);
			if(gs.keys.enterPress == true) {
				gs.gameStates= gs.huntState;
				selectedNum=0;
			}
		}
		
		//full screen box
		textX= frameX+ gs.sizeRect*6;
		textY= frameY+ gs.sizeRect*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if(gs.fullscreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		//music volume
		textX= frameX+ gs.sizeRect*4;
		textY += gs.sizeRect;
		g2.drawRect(textX, textY, 120, 24); // 120 /5 = 24 
		
		int volimeWidth = 24*gs.music.volumeScale;
		g2.fillRect(textX, textY, volimeWidth, 24);
		
		//se volume
		textX= frameX+ gs.sizeRect*4;
		textY += gs.sizeRect;
		g2.drawRect(textX, textY, 120, 24);
		volimeWidth = 24*gs.se.volumeScale;
		g2.fillRect(textX, textY, volimeWidth, 24);
		
		textX= frameX+ gs.sizeRect*6;
		textY += gs.sizeRect-10;
		
		g2.drawImage(ovca,textX , textY,null);
		
		textX= frameX+ gs.sizeRect*6;
		textY += gs.sizeRect;
		
		g2.drawImage(ovca,textX , textY,null);
		
		
		gs.config.saveConf();

		
	}
	
	public void options_control(int frameX, int frameY) {
		String text = "OVLÁDANIE";
		int textX = getXforCenteredText(text);
		int textY = frameY + gs.sizeRect;
		g2.drawString(text, textX, textY);
		g2.setFont(g2.getFont().deriveFont(19F));
		
		
		
		textX = frameX + gs.sizeRect;
		textY+= gs.sizeRect;
		
		g2.drawString("POHYB", textX, textY);
		textY+= gs.sizeRect;
		
		g2.drawString("ÚTOK", textX, textY);
		textY+= gs.sizeRect;
		
		g2.drawString("STREĽBA", textX, textY);
		textY+= gs.sizeRect;
		
		g2.drawString("INVENTOR", textX, textY);
		textY+= gs.sizeRect;
		
		g2.drawString("PAUZA", textX, textY);
		textY+= gs.sizeRect;
		
		g2.drawString("MOŽNOSTI", textX, textY);
		textY+= gs.sizeRect;
		
		//back
		textX = frameX + gs.sizeRect;
		textY = frameY + gs.sizeRect*9;
		g2.drawString("BACK", textX, textY);
		if(selectedNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gs.keys.enterPress == true) {
				subState=0;
				selectedNum=3;
			}
		}
		
		textX = frameX + gs.sizeRect*5;
		textY = frameY + gs.sizeRect*2;
		g2.setColor(Color.GRAY);
		
		g2.drawString("WASD", textX, textY);
		textY+= gs.sizeRect;
		g2.drawString("ENTER", textX, textY);
		textY+= gs.sizeRect;
		g2.drawString("F", textX, textY);
		textY+= gs.sizeRect;
		g2.drawString("C", textX, textY);
		textY+= gs.sizeRect;
		g2.drawString("P", textX, textY);
		textY+= gs.sizeRect;
		g2.drawString("ESCAPE", textX, textY);
		textY+= gs.sizeRect;
		
		
		
	}
	
	public void options_endGameConf(int frameX,int frameY) {
		int textX = frameX + gs.sizeRect;
		int textY= frameY + gs.sizeRect*3;
		
		currentDialogue = "Odíd, ale hra nebude \nuložená!!";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+= 40;
		}
		
		//yes 
		String text = "ANO";
		textX = getXforCenteredText(text);
		textY += gs.sizeRect*3;
		g2.drawString(text, textX, textY);
		if(selectedNum==0) {
			g2.drawString(">", textX-25, textY);
			if(gs.keys.enterPress == true) {
				subState=0;
				gs.gameStates = gs.titleState;
				titleScreenState = 0;
				gs.stopMusic();
				gs.restart();
			}
		}
		
		//no
		
		text = "NIE";
		textX = getXforCenteredText(text);
		textY += gs.sizeRect;
		g2.drawString(text, textX, textY);
		if(selectedNum==1) {
			g2.drawString(">", textX-25, textY);
			if(gs.keys.enterPress == true) {
				subState=0;
				selectedNum = 4;
			}
		}
	}
	
	public void options_fullScrNotif(int frameX, int frameY) {
		int textX= frameX + gs.sizeRect;
		int textY= frameY + gs.sizeRect;
		
		currentDialogue = "The change will take \neffect after \nrestarting the game!";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+= 40;
		}
		
		//back
		textY= frameY + gs.sizeRect*9;
		g2.drawString("BACK", textX, textY);
		if(selectedNum ==0) {
			g2.drawString(">", textX-25, textY);
			if(gs.keys.enterPress == true) {
				subState=0;
			}
		}
	}
	
	public void drawMessage() {
		int messX = gs.sizeRect;
		int messY = gs.sizeRect*6;
		g2.setFont(purisaB);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,28F));
		
		for(int i =0; i < message.size(); i++) {
			if(message.get(i) != null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i),messX,messY);
				
				g2.setColor(new Color(252, 127, 3,200));
				g2.drawString(message.get(i),messX+2,messY+2);
				
				int counter = messageCount.get(i) +1;	// messageCounter++
				messageCount.set(i, counter);			//set the counter to the array
				messY += 50;
				
				if(messageCount.get(i) > 180) {
					message.remove(i);
					messageCount.remove(i);
				}
			}
		}
	}
	
	public void drawSleepScreen() {
		tranCounter++;
		
		if(tranCounter <120) {
			gs.eManag.light.filterAlpha += 0.01f;
			if(gs.eManag.light.filterAlpha > 1f) {
				gs.eManag.light.filterAlpha = 1f;
			}
		}
		if(tranCounter >= 120) {
			gs.eManag.light.filterAlpha -= 0.01f;
			if(gs.eManag.light.filterAlpha <= 0f) {
				gs.eManag.light.filterAlpha = 0f;
				tranCounter=0;
				gs.eManag.light.dayState = gs.eManag.light.day;
				gs.eManag.light.dayCount =0;
				gs.gameStates = gs.huntState;
				gs.player.getImage();
			}
		}
	}
	
	public void drawCharScreen() {
		
		//window frame
		final int frameX = gs.sizeRect ;
		final int frameY = gs.sizeRect;
		final int frameWidth = gs.sizeRect*5;
		final int frameHeight = gs.sizeRect * 10;
		drawSubWind(frameX, frameY, frameWidth, frameHeight);
		
		//text
		g2.setColor(Color.black);
		g2.setFont(purisaB);
		g2.setFont(g2.getFont().deriveFont(24F));
		
		int textX = frameX + 20;
		int textY = frameY+gs.sizeRect;
		final int lineHeight = 35;
		
		//names
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Život", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Sila", textX, textY);
		textY += lineHeight;
		g2.drawString("Obratnosť", textX, textY);
		textY += lineHeight;
		g2.drawString("Útok", textX, textY);
		textY += lineHeight;
		g2.drawString("Obrana", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Další Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Dolár", textX, textY);
		textY += lineHeight+15;
		g2.drawString("Zbraň", textX, textY);
		textY += lineHeight+15;
		g2.drawString("Štít", textX, textY);
		textY += lineHeight;
		
		
		//values 
		int tailX = (frameX + frameWidth)- 30;
		//reset
		textY = frameY + gs.sizeRect;
		String value;
		
		
		value= String.valueOf(gs.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value= String.valueOf(gs.player.life +"/"+ gs.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value= String.valueOf(gs.player.mana +"/"+ gs.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value= String.valueOf(gs.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value= String.valueOf(gs.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value= String.valueOf(gs.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		
		value= String.valueOf(gs.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value= String.valueOf(gs.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value= String.valueOf(gs.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value= String.valueOf(gs.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += 15;
		
		g2.drawImage(gs.player.currentWeapon.down1, tailX-gs.sizeRect, textY, null);
		textY += gs.sizeRect;
		
		g2.drawImage(gs.player.currentShield.down1, tailX-gs.sizeRect, textY, null);
		textY += gs.sizeRect;
		
		
		
	}
	
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gs.screenWidth, gs.screenHeight);
		
		int x;
		int y;
		String text;
		
		g2.setFont(purisaB);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
		
		text = "GAME OVER!";
		
		g2.setColor(Color.black);
		x=getXforCenteredText(text);
		y=gs.sizeRect*4;
		g2.drawString(text, x, y);
		
		g2.setColor(new Color(252, 127, 3,200));
		x=getXforCenteredText(text);
		y=gs.sizeRect*4;
		g2.drawString(text, x+5, y+5);
		
		//retry
		g2.setFont(g2.getFont().deriveFont(50f));
		g2.setColor(Color.black);
		text="Skúsiť znova!";
		x =getXforCenteredText(text);
		y +=gs.sizeRect*4;
		g2.drawString(text, x, y);
		
		g2.setColor(new Color(252, 127, 3,200));
		g2.drawString(text, x+4, y+4);
		if(selectedNum == 0) {
			g2.setColor(Color.black);
			g2.drawString(">", x-gs.sizeRect, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(">", x-gs.sizeRect+4, y+4);
		}
		
		//quit
		g2.setColor(Color.black);
		text="Ukončiť hru!";
		x =getXforCenteredText(text);
		y += 60;
		g2.drawString(text, x, y);
		
		g2.setColor(new Color(252, 127, 3,200));
		g2.drawString(text, x+4, y+4);
		if(selectedNum == 1) {
			g2.setColor(Color.black);
			g2.drawString(">", x-gs.sizeRect, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(">", x-gs.sizeRect+4, y+4);
		}
		
	}
	
	public void drawTradingScreen() {
		
		switch(subState) {
		case 0: trade_select();break;
		case 1: trade_buy();break;
		case 2: trade_sell();break;
		}
		gs.keys.enterPress = false;
	}
	
	public void trade_select() {
		int x,y,width,height;
		drawDialogue();
		g2.setFont(g2.getFont().deriveFont(50f));
		String uniza = "SPONZORYNG FEYT UNYZA!";
		x= gs.sizeRect;
		y=gs.sizeRect*2;
		g2.drawString(uniza, x, y);
		
		g2.setColor(new Color(252, 127, 3));
		g2.drawString(uniza, x+4, y+4);
		
		//draw window
		x= gs.sizeRect *14;
		y= gs.sizeRect *3;
		
		width= gs.sizeRect*3;
		height= (int)(gs.sizeRect * 3.5);
		
		drawSubWind(x, y, width, height);
		
		//draw texts
		g2.setFont(g2.getFont().deriveFont(26f));
		
		x += gs.sizeRect;
		y += gs.sizeRect;
		g2.drawString("Kúpa", x-15, y);
		if(selectedNum == 0) {
			g2.drawString(">", x-34, y);
			if(gs.keys.enterPress == true) {
				subState = 1;
			}
		}
		y += gs.sizeRect;
		g2.drawString("Predaj", x-15, y);
		if(selectedNum == 1) {
			g2.drawString(">", x-34, y);
			if(gs.keys.enterPress == true) {
				subState = 2;
			}
		}
		y += gs.sizeRect;
		g2.drawString("Späť", x-15, y);
		if(selectedNum == 2) {
			g2.drawString(">", x-34, y);
			if(gs.keys.enterPress == true) {
				selectedNum = 0;
				gs.gameStates = gs.dialogState;
				currentDialogue = "Maj sa, nabudúce kúp \nteho vác";
			}
		}
		
	}
	
	public void trade_buy() {
		
		//draw player inventory
		drawInventory(gs.player, false);
		//draw npc inventory
		drawInventory(npc, true);
		
		//draw hint window
		int x = gs.sizeRect*2;
		int y = gs.sizeRect*9+15;
		int width = gs.sizeRect*6;
		int height = gs.sizeRect*2;
		drawSubWind(x, y, width, height);
		g2.drawString("[ESC] Späť", x+24, y+55);
		
		//draw npc  coin window
		x = gs.sizeRect*11;
		y = gs.sizeRect*9+15;
		width = gs.sizeRect*6;
		height = gs.sizeRect*2;
		drawSubWind(x, y, width, height);
		g2.drawString("Doláros: "+gs.player.coin, x+24, y+55);
		
		//DRAW price WINDOW
		int itemIndex= getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			x=(int)(gs.sizeRect*5.5);
			y=(int)(gs.sizeRect*5.5);
			width = (int)(gs.sizeRect*2.5);
			height = gs.sizeRect;
			drawSubWind(x, y, width, height);
			g2.drawImage(coin, x+10	, y+8, 32,32,null);
			
			int price = npc.inventory.get(itemIndex).price;
			
			String text = ""+price;
			x=getXforAlignToRightText(text, gs.sizeRect*8-20);
			g2.drawString(text, x, y+30);
			
			
			
			//BUYING ITEMS
			if(gs.keys.enterPress == true) {
				if(npc.inventory.get(itemIndex).price > gs.player.coin) {
					subState=0;
					gs.gameStates = gs.dialogState;
					currentDialogue = "Nemáš love , čo by si \nchcel?";
					drawDialogue();
				}else {
					if(gs.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						gs.player.coin -= npc.inventory.get(itemIndex).price;
					}else {
						subState=0;
						gs.gameStates = gs.dialogState;
						currentDialogue = "Maš plné vrecka čo ty \nchceš!";
					}
				}
			}
		}
	}
	
	public void trade_sell() {
		//draw player invetory
		
		drawInventory(gs.player, true);
		int x;
		int y;
		int width;
		int height;
		
		//draw hint window
		x = gs.sizeRect*2;
		y = gs.sizeRect*9+15;
		width = gs.sizeRect*6;
		height = gs.sizeRect*2;
		drawSubWind(x, y, width, height);
		g2.drawString("[ESC] Späť", x+24, y+55);
		
		//draw npc  coin window
		x = gs.sizeRect*11;
		y = gs.sizeRect*9+15;
		width = gs.sizeRect*6;
		height = gs.sizeRect*2;
		drawSubWind(x, y, width, height);
		g2.drawString("Doláros: "+gs.player.coin, x+24, y+55);
		
		//DRAW price WINDOW
		int itemIndex= getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if(itemIndex < gs.player.inventory.size()) {
			x=(int)(gs.sizeRect*14.5);
			y=(int)(gs.sizeRect*5.5);
			width = (int)(gs.sizeRect*2.5);
			height = gs.sizeRect;
			drawSubWind(x, y, width, height);
			g2.drawImage(coin, x+10	, y+8, 32,32,null);
			
			int price = gs.player.inventory.get(itemIndex).price/2;
			
			String text = ""+price;
			x=getXforAlignToRightText(text, gs.sizeRect*17-20);
			g2.drawString(text, x, y+30);
			
			
			
			//selling ITEMS
			if(gs.keys.enterPress == true) {
				if(gs.player.inventory.get(itemIndex) == gs.player.currentWeapon || 
						gs.player.inventory.get(itemIndex) == gs.player.currentShield) {
					selectedNum=0;
					subState=0;
					gs.gameStates = gs.dialogState;
					currentDialogue= "Vybavené náradičko,\nnemôžeš predať!!!";
				}else {
					if(gs.player.inventory.get(itemIndex).amount > 1 ) {
						gs.player.inventory.get(itemIndex).amount--;
					}else {
						gs.player.inventory.remove(itemIndex);
					}
					gs.player.coin += price;
				}
					
			}
		}
	}
	
	public void drawInventory(Characters character, boolean cursor) {
		
		int frameX=0;
		int frameY=0;
		int frameWidth=0;
		int frameHeight=0;
		int slotCol=0;
		int slotRow=0;
		
		if(character == gs.player) {
			frameX = gs.sizeRect *11;
			frameY = gs.sizeRect;
			frameWidth = gs.sizeRect*6;
			frameHeight = gs.sizeRect * 5;
			slotCol= playerSlotCol;
			slotRow= playerSlotRow;
		}else {
			frameX = gs.sizeRect *2;
			frameY = gs.sizeRect;
			frameWidth = gs.sizeRect*6;
			frameHeight = gs.sizeRect * 5;
			slotCol= npcSlotCol;
			slotRow= npcSlotRow;
		}
		g2.setFont(purisaB);
		//window frame
		drawSubWind(frameX, frameY, frameWidth, frameHeight);
		
		//SLot
		final int slotXstart= frameX + 20;
		final int slotYstart= frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize= gs.sizeRect+3;
		
		//draw player items
		for(int i = 0; i < character.inventory.size(); i++) {
			//EQUIP cursor
			if(character.inventory.get(i)== character.currentWeapon || 
					character.inventory.get(i)== character.currentShield ||
					character.inventory.get(i)== character.currentLight) {
				g2.setColor(new Color(179,98,0));
				g2.fillRoundRect(slotX, slotY, gs.sizeRect, gs.sizeRect,10,10);
			}
			
			g2.drawImage(character.inventory.get(i).down1, slotX, slotY, null );
			
			//display amount
			if(character == gs.player && character.inventory.get(i).amount > 1) {
				g2.setFont(g2.getFont().deriveFont(32f));
				int amountX;
				int amountY;
				String s = ""+ character.inventory.get(i).amount;
				amountX=getXforAlignToRightText(s, slotX + 44);
				amountY= slotY + gs.sizeRect;
				
				//draw shadow number
				g2.setColor(new Color(60,60,60));
				g2.drawString(s, amountX, amountY);
				
				//number
				g2.setColor(Color.yellow
						);
				g2.drawString(s, amountX-3, amountY-3);
			}
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14){
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		// cursor
		if(cursor == true) {
			int cursorX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gs.sizeRect;
			int cursorHeight = gs.sizeRect;
			
			//draw cursor
			g2.setColor(new Color(169,169,169));
			//g2.fillRect(cursorX, cursorY, cursorWidth, cursorHeight);
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight,10 ,10);
			
			
			//DESCRIPTION FREAM
			int dFrameX=frameX;
			int dFrameY= frameY+frameHeight+10;
			int dFrameWidth= frameWidth;
			int dFrameHeight= gs.sizeRect*3;
			
			
			//text
			int textX = dFrameX+20;
			int textY= dFrameY+ gs.sizeRect;
			g2.setFont(g2.getFont().deriveFont(20F));
			
			int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
			if(itemIndex < character.inventory.size()) {
				
				drawSubWind(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				
				for(String line : character.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 32;
				}
				
			}
		}
	}
	
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	
	public void drawTittleScreen() {
		if(titleScreenState == 0) {
			//background
			UtilityTool uTool = new UtilityTool();
			try {
				BufferedImage uniza = ImageIO.read(getClass().getResourceAsStream("/img/uniza1111.jpg"));
				unyza= uTool.scaleImage(uniza, gs.screenWidth, gs.screenHeight);
				g2.drawImage(uniza, 0, 0, null);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//tittle name
			g2.setFont(purisaB);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,70));
			String text = "UNYZA ADVENTÜRE";
			int x = getXforCenteredText(text);
			int y = gs.sizeRect*3;
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+5, y+5);
			
			//ovca
			try {
				BufferedImage uniza = ImageIO.read(getClass().getResourceAsStream("/img/ovca.png"));
				unyza= uTool.scaleImage(uniza, gs.screenWidth-30, gs.screenHeight-30);
				//g2.drawImage(uniza, 0, 0, null);
			} catch (Exception e) {
				// TODO: handle exception
			}
			x = (gs.sizeRect*8)- (gs.sizeRect/3) ;
			y = gs.sizeRect*2;
			g2.drawImage(unyza, x, y,gs.sizeRect-10, gs.sizeRect-10,null);
			
			
			
			//text gandalf
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,30));
			x=gs.screenWidth/2 + (gs.sizeRect*2);
			y += gs.sizeRect*4;
			g2.setColor(Color.black);
			g2.drawString("Skúl Gandalf", x, y);
			
			g2.setColor(Color.decode("#FF742c"));
			
			g2.drawString("Skúl Gandalf", x+4, y+4);
			
			
			
			//gandalf
			x=gs.screenWidth/2 + (gs.sizeRect*3);
			y += gs.sizeRect;
			
			try {
				BufferedImage monster2 = ImageIO.read(getClass().getResourceAsStream("/img/oldman_down_1.png"));
				monster= uTool.scaleImage(monster2, gs.screenWidth, gs.screenHeight);
				g2.drawImage(monster, x, y,gs.sizeRect*3, gs.sizeRect*3,null);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//monster
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,42));
			
			
			
			text = "Nová hra";
			x = gs.sizeRect*2;
			y = gs.sizeRect *7+10;
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+4, y+4);
			if(selectedNum == 0) {
				g2.setColor(Color.black);
				g2.drawString(">", x-gs.sizeRect, y);
				g2.setColor(Color.decode("#FF742c"));
				g2.drawString(">", x-gs.sizeRect+4, y+4);
			}
			
			
			text = "Načítaj";
			x = gs.sizeRect*2;
			y += gs.sizeRect+5;
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+4, y+4);
			if(selectedNum == 1) {
				g2.setColor(Color.black);
				g2.drawString(">", x-gs.sizeRect, y);
				g2.setColor(Color.decode("#FF742c"));
				g2.drawString(">", x-gs.sizeRect+4, y+4);
			}
			
			
			text = "Možnosti";
			x = gs.sizeRect*2;
			y += gs.sizeRect;
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+4, y+4);
			if(selectedNum == 2) {
				g2.setColor(Color.black);
				g2.drawString(">", x-gs.sizeRect, y);
				g2.setColor(Color.decode("#FF742c"));
				g2.drawString(">", x-gs.sizeRect+4, y+4);
			}
			
			
			text = "Ukončiť hru";
			x = gs.sizeRect*2;
			y += gs.sizeRect+5;
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+4, y+4);
			if(selectedNum == 3) {
				g2.setColor(Color.black);
				g2.drawString(">", x-gs.sizeRect, y);
				g2.setColor(Color.decode("#FF742c"));
				g2.drawString(">", x-gs.sizeRect+4, y+4);
			}
		}
		else if(titleScreenState == 1) {
			g2.setFont(purisaB);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,40));
			
			try {
				BufferedImage uniza = ImageIO.read(getClass().getResourceAsStream("/img/uniza1111.jpg"));
				unyza= uTool.scaleImage(uniza, gs.screenWidth, gs.screenHeight);
				g2.drawImage(uniza, 0, 0, null);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			String text = "";
			int x;
			int y=0;
			
			text="Hrať";
			x = gs.sizeRect;
			y += gs.sizeRect*10;
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+3, y+3);
			
			if(selectedNum == 0) {
				g2.setColor(Color.black);
				g2.drawString(">", x-gs.sizeRect, y);
				g2.setColor(Color.decode("#FF742c"));
				g2.drawString(">", x-gs.sizeRect+3, y+3);
			}
			text="Späť";
			x = gs.sizeRect;
			y += gs.sizeRect;
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+3, y+3);
			if(selectedNum == 1) {
				g2.setColor(Color.black);
				g2.drawString(">", x-gs.sizeRect, y);
				g2.setColor(Color.decode("#FF742c"));
				g2.drawString(">", x-gs.sizeRect+3, y+3);
				
			}
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,40));
			text = "Krycia prezývka : " + gs.id;
			x = gs.sizeRect- (gs.sizeRect/2);
			y = gs.sizeRect*2;
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+3, y+3);
			
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,30));
			text = "Tvoja výbava, vagoš: ";
			x = gs.sizeRect - (gs.sizeRect/2);
			y = gs.sizeRect*4;
			g2.setColor(Color.black);
			g2.drawString(text, x, y);
			g2.setColor(Color.decode("#FF742c"));
			g2.drawString(text, x+3, y+3);
			
			x=gs.screenWidth - (gs.sizeRect*6);
			y=gs.sizeRect;
			
			try {
				BufferedImage stuff1 = ImageIO.read(getClass().getResourceAsStream("/img/deafaultStuff.png"));
				stuff11= uTool.scaleImage(stuff1, gs.screenWidth, gs.screenHeight);
				g2.drawImage(stuff11, x, y,gs.sizeRect*5, gs.sizeRect*10,null);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			x=gs.screenWidth - (gs.sizeRect*12);
			y=gs.sizeRect*5;
			
			try {
				BufferedImage stuff2 = ImageIO.read(getClass().getResourceAsStream("/img/deafaultStuff2.png"));
				stuff22= uTool.scaleImage(stuff2, gs.screenWidth, gs.screenHeight);
				g2.drawImage(stuff22, x, y,gs.sizeRect*5, gs.sizeRect*6,null);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
	
	public void drawDialogue() {
		//window
		g2.setFont(purisaB);
		int x=gs.sizeRect*3;
		int y=gs.sizeRect*7;
		int width=gs.screenWidth - (gs.sizeRect*6);
		int height= gs.sizeRect*4;
		
		drawSubWind(x, y, width, height);
		
		x += gs.sizeRect;
		y += gs.sizeRect+5;
		
		for(String line : currentDialogue.split("\n")) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,38));
			  g2.drawString(line, x, y); y +=52; 
			  }
		/*
		g2.setColor(new Color(0,0,0));
		String enter = "Press enter!";
		g2.drawString(enter, gs.sizeRect*7, gs.sizeRect*5); 
		
		g2.setColor(new Color(252, 127, 3));
		enter = "Press enter!";
		g2.drawString(enter, gs.sizeRect*7+3, gs.sizeRect*5+3);
		*/
	}
	
	public void drawSubWind(int x, int y , int width, int height) {
		Color c= new Color(252, 127, 3,200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c= new Color(0,0,0);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	public void drawPause() {
		g2.setFont(purisaB);
		//g2.setColor(Color.white);
		g2.setColor(Color.black);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,50));
		String text="PAUZA";
		int x= getXforCenteredText(text);
				
		
		int y= gs.screenHeight/2;
		
		g2.drawString(text, x, y);

		g2.setColor(new Color(252, 127, 3));
		x= getXforCenteredText(text);		
		y= gs.screenHeight/2;
		
		g2.drawString(text, x+3, y+3);
	}
	
	public int getXforCenteredText(String text) {
		int length =(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x=gs.screenWidth/2 - length/2;
		return x;
	}
	
	public int getXforAlignToRightText(String text,int tailX) {
		int length =(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x=tailX - length;
		return x;
	}
}
