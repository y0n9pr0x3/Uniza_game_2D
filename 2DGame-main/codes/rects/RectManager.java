package rects;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GameScreen;
import main.UtilityTool;

public class RectManager {
	GameScreen gs ;
	public Rects[] rects;
	public int mapRectNum[][][];
	
	public RectManager(GameScreen gs) {
		
		this.gs = gs;
		rects = new Rects[50]; // 50 different background rectangles
		mapRectNum = new int[gs.maxMap][gs.maxWorldCol][gs.maxWorldRow]; // here we save all numbers from text document
		
		getImage();
		loadMap("/maps/worldOK.txt",0);
		loadMap("/maps/interior01.txt",1);
		
	}
	
	public void getImage() {
		setup(0, "grass00", false);
		setup(1, "wall", false);
		setup(2, "water00", false);
		setup(3, "earth", false);
		setup(4, "tree", true);
		setup(5, "sand", false);
		setup(6, "grass00", false);
		setup(7, "grass00", false);
		setup(8, "grass00", false);
		setup(9, "grass00", false);
		
		
		setup(10, "grass00", false);
		setup(11, "grass01", false);
		setup(12, "water00", true);
		setup(13, "water01", true);
		setup(14, "water02", true);
		setup(15, "water03", true);
		setup(16, "water04", true);
		setup(17, "water05", true);
		setup(18, "water06", true);
		setup(19, "water07", true);
		setup(20, "water08", true);
		setup(21, "water09", true);
		setup(22, "water10", true);
		setup(23, "water11", true);
		setup(24, "water12", true);
		setup(25, "water13", true);
		setup(26, "road00", false);
		setup(27, "road01", false);
		setup(28, "road02", false);
		setup(29, "road03", false);
		setup(30, "road04", false);
		setup(31, "road05", false);
		setup(32, "road06", false);
		setup(33, "road07", false);
		setup(34, "road08", false);
		setup(35, "road09", false);
		setup(36, "road10", false);
		setup(37, "road11", false);
		setup(38, "road12", false);
		setup(39, "earth", false);
		setup(40, "wall", true);
		setup(41, "tree", true);
		setup(42, "hut", false);
		setup(43, "floor01", false);
		setup(44, "table01", true);
		setup(45, "schod", false);
		setup(46, "tabula", false);
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow=0;
		
		
		while(worldCol < gs.maxWorldCol && worldRow < gs.maxWorldRow) {
			
			int rectNum = mapRectNum[gs.currentMap][worldCol][worldRow];
			int worldX= worldCol * gs.sizeRect;
			int worldY= worldRow * gs.sizeRect;
			int screenX = worldX - gs.player.worldX + gs.player.screenX;
			int screenY = worldY - gs.player.worldY + gs.player.screenY;
			
			
			if(worldX + gs.sizeRect > gs.player.worldX - gs.player.screenX && 
					worldX - gs.sizeRect < gs.player.worldX + gs.player.screenX &&
					worldY + gs.sizeRect > gs.player.worldY - gs.player.screenY && 
					worldY - gs.sizeRect < gs.player.worldY + gs.player.screenY) {
				g2.drawImage(rects[rectNum].image, screenX, screenY, null);
				
			}
			worldCol++;
			
			if (worldCol == gs.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
	public void setup(int index,String imageName, boolean collision) {
		UtilityTool uTool =new UtilityTool();
		try {
			rects[index] =new Rects();
			rects[index].image= ImageIO.read(getClass().getResourceAsStream("/rects/"+imageName + ".png"));
			rects[index].image=uTool.scaleImage(rects[index].image, gs.sizeRect, gs.sizeRect);
			rects[index].collision=collision;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void loadMap(String filePath, int map) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int row = 0;
			int col = 0;
			
			while (col < gs.maxWorldCol && row < gs.maxWorldRow ) {
				String line = br.readLine();
				while ( col < gs.maxWorldCol) {
					String numbers[]= line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					mapRectNum[map][col][row]=num;
					col++;
				}
				if(col == gs.maxWorldCol) {
					col=0;
					row++;
				}
			}
			br.close();
			} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
