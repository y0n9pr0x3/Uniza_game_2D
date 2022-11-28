package main;

import characters.NPC_Merchant;
import characters.NPC_prof01;
import monster.MON_BlueBall;
import monster.MON_GreenSlime;
import objects.OBJ_Axe;
import objects.OBJ_Boots;
import objects.OBJ_Door;
import objects.OBJ_Heart;
import objects.OBJ_Key;
import objects.OBJ_ManaBEER;
import objects.OBJ_Potion_Red;
import objects.OBJ_Shield_blue;
import objects.OBJ_Uniza_Coin;
import rects_interact.Interact_tree;

public class DrawObjects {
	GameScreen gs;
	
	public DrawObjects(GameScreen gs) {
		
		this.gs = gs;
	}

	public void setObject() {
		int mapNum = 0;
		int i = 0;
		
		gs.obj[mapNum][i] = new OBJ_Uniza_Coin(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 27;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 15;
		i++;
		
		gs.obj[mapNum][i] = new OBJ_ManaBEER(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 28;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 15;
		i++;
		
		gs.obj[mapNum][i] = new OBJ_Heart(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 29;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 15;
		i++;
		
		gs.obj[mapNum][i] = new OBJ_Axe(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 30;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 15;
		i++;
		
		gs.obj[mapNum][i] = new OBJ_Shield_blue(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 31;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 15;
		i++;
		
		gs.obj[mapNum][i] = new OBJ_Boots(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 32;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 15;
		i++;
		
		gs.obj[mapNum][i] = new OBJ_Door(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 30;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 19;
		i++;
		
		gs.obj[mapNum][i] = new OBJ_Door(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 25;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 19;
		i++;
		
		gs.obj[mapNum][i] = new OBJ_Key(gs);
		gs.obj[mapNum][i].worldX = gs.sizeRect * 31;
		gs.obj[mapNum][i].worldY = gs.sizeRect * 19;
		i++;
		
	}
	
	public void setNPC() {
		int mapNum = 0;
		int i =0;
		//map 0
		gs.npc[mapNum][i] = new NPC_prof01(gs);
		gs.npc[mapNum][i].worldX = gs.sizeRect * 30;
		gs.npc[mapNum][i].worldY = gs.sizeRect * 18;
		i++;
		
		//map 1
		mapNum=1;
		i=0;
		gs.npc[mapNum][i] = new NPC_Merchant(gs);
		gs.npc[mapNum][i].worldX = gs.sizeRect * 12;
		gs.npc[mapNum][i].worldY = gs.sizeRect * 7;
		
	}
	
	public void setMonster() {
		int mapNum = 0;
		int i = 0;
		
		gs.mon[mapNum][i] = new MON_BlueBall(gs);
		gs.mon[mapNum][i].worldX = gs.sizeRect * 21;
		gs.mon[mapNum][i].worldY = gs.sizeRect * 38;
		i++;
		
		gs.mon[mapNum][i] = new MON_BlueBall(gs);
		gs.mon[mapNum][i].worldX = gs.sizeRect * 34;
		gs.mon[mapNum][i].worldY = gs.sizeRect * 42;
		i++;
		
		
	}
	
	public void setInterRect() {
		int mapNum = 0;
		int i =0;
		
		gs.iRect[mapNum][i] = new Interact_tree(gs,27,13);
		gs.iRect[mapNum][i].worldX = gs.sizeRect * 27;
		gs.iRect[mapNum][i].worldY = gs.sizeRect * 13;
		i++;
		
		gs.iRect[mapNum][i] = new Interact_tree(gs,28,13);
		gs.iRect[mapNum][i].worldX = gs.sizeRect * 28;
		gs.iRect[mapNum][i].worldY = gs.sizeRect * 13;
		i++;
		
		gs.iRect[mapNum][i] = new Interact_tree(gs,29,13);
		gs.iRect[mapNum][i].worldX = gs.sizeRect * 29;
		gs.iRect[mapNum][i].worldY = gs.sizeRect * 13;
		i++;
		
		gs.iRect[mapNum][i] = new Interact_tree(gs,30,13);
		gs.iRect[mapNum][i].worldX = gs.sizeRect * 30;
		gs.iRect[mapNum][i].worldY = gs.sizeRect * 13;
		i++;
	}
}
