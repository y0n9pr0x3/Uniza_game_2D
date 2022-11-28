package rects_interact;

import main.GameScreen;

public class Interact_trunk extends InteractiveRect{
	
GameScreen gs;
	
	public Interact_trunk(GameScreen gs,int col , int row) {
		super(gs,col ,row);
		this.gs=gs;
		
		this.worldX = gs.sizeRect * col;
		this.worldY = gs.sizeRect * row;
		
		down1 = setup("/rects_interact/trunk",gs.sizeRect,gs.sizeRect);
		destruct= true;
		
		solidRect.x=0;
		solidRect.y=0;
		solidRect.width=0;
		solidRect.height=0;
		solidRectDefaultX=solidRect.x;
		solidRectDefaultY=solidRect.y;
	}
}
