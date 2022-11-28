	package main;
	
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.Comparator;
	import java.util.UUID;
	
	import javax.swing.JPanel;
	
	import characters.Characters;
	import characters.UnizaPlayer;
	import rects.RectManager;
import rects_interact.InteractiveRect;
	
	public class GameScreen extends JPanel implements Runnable{
		
		//window settings
	public final int originalSizeRect = 16; // 16x16 rectangle
	public final int scale = 3;				// image will be 48x48
	
	public int sizeRect= originalSizeRect * scale; // 48x48
	public int maxScreenCol = 20;
	public int maxScreenRow = 12;
	public int screenWidth = sizeRect * maxScreenCol; //960 px
	public int screenHeight = sizeRect * maxScreenRow; // 576 px
	
	//world
	public final int  maxWorldCol = 50;
	public final int  maxWorldRow = 50;
	public final int maxMap =10; // we create 10 maps
	public int currentMap= 0; // this current map is index 0
	
	//Initialized objects
	public Keys keys= new Keys(this);
	public UnizaPlayer player = new UnizaPlayer(this,keys );
	public Config config = new Config(this);
	public RectManager rectM = new RectManager(this);
	public CollisionManager cManager = new CollisionManager(this);
	public DrawObjects dObject = new DrawObjects(this);
	public Sounds music =new Sounds();
	public Sounds se =new Sounds();
	public UserInterf ui=new UserInterf(this);
	public Events events = new Events(this);
	
	static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	static GraphicsDevice gd = ge.getDefaultScreenDevice();
	//game thread
	Thread gameThread;
	
	//lists
	public Characters obj[][] = new Characters[maxMap][20];
	public Characters npc[][] = new Characters[maxMap][10];
	public Characters mon[][] = new Characters[maxMap][20];
	public InteractiveRect iRect[][] = new InteractiveRect[maxMap][50];
	public ArrayList<Characters> charactList = new ArrayList<>();
	public ArrayList<Characters> projectileList = new ArrayList<>();
	
	public String id;
	
	//FOR FULL SCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullscreenOn = false;
	
	
	//game State
	public int gameStates;
	public final int titleState=0;
	public final int huntState=1;
	public final int pauseState=2;
	public final int dialogState=3;
	public final int characterState=4;
	public final int optionState = 5;
	public final int gameOverState=6;
	public final int transitionState=7;
	public final int tradingState=8;
			
	//FPS
	int fps=60;
	
	public GameScreen() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true); 	//Needed for reducing flickering and having smoother animation.
		this.addKeyListener(keys);
		this.setFocusable(true);		// if true , gamepanel can be "focused" to receive key input.
	}
	
	public void setupGame() {
		
		dObject.setObject();
		dObject.setNPC();
		dObject.setMonster();
		dObject.setInterRect();
		//playMusic(14);
		gameStates= titleState;
		
		//tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		//g2= (Graphics2D)tempScreen.getGraphics();
		if(fullscreenOn == true) {
			setFullScreen();
		}
		
		
		id = generujID();
	}
	
	public void retry() {
		playMusic(14);
		player.setDefaultPosition();
		player.restoreLifeAndMana();
		dObject.setNPC();
		dObject.setMonster();
	}
	
	public void restart() {
		//playMusic(14);
		player.setDefault();
		player.setDefaultPosition();
		player.restoreLifeAndMana();
		player.setItems();
		dObject.setObject();
		dObject.setNPC();
		dObject.setMonster();
		dObject.setInterRect();
	}
	
	public void startGameThread() {
		gameThread= new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount=0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
				drawCount ++;
			}
			if(timer >= 1000000000) {			
				System.out.println("FPS: " + drawCount);		//show FPS in console
				drawCount=0;
				timer=0;
			}
		}
		
	}
	
	public void setFullScreen() {
		
		gd.setFullScreenWindow(Main.window);
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
		
		setPreferredSize(new Dimension(screenWidth2,screenHeight2));
		requestFocus();
	}
	
	public void update() {
		if(gameStates == huntState) {
			player.update();
			
			//npc
			for(int i=0; i < npc[1].length;i++) {
				if(npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			
			//monster
			for(int i=0; i < mon[1].length;i++) {
				if(mon[currentMap][i] != null) {
					if(mon[currentMap][i].alive == true && mon[currentMap][i].dying == false) {
						mon[currentMap][i].update();
					}
					if(mon[currentMap][i].alive == false) {
						mon[currentMap][i].checkDrop();
						mon[currentMap][i] = null;
					}
				}
			}
			
			//projectile
			for(int i=0; i < projectileList.size();i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}
			
			
			for(int i=0; i < iRect[1].length; i++) {
				if(iRect[currentMap][i] != null) {
					iRect[currentMap][i].update();
				}
			}
			
		}
		if(gameStates == pauseState) {
			
		}
		
	}
	
	public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D)g.create();
	//debug 
	long drawStart = 0;
	if(keys.checkDrawTime == true) {
		drawStart = System.nanoTime();
	}
	
	//tittle screen
	if(gameStates == titleState) {
		ui.draw(g2);
		
	}else {
	
		rectM.draw(g2);
		
		for(int i=0; i < iRect[1].length; i++) {
			if(iRect[currentMap][i] != null) {
				iRect[currentMap][i].draw(g2);
			}
		}
		
		//add characters to the list
		charactList.add(player);
		
		for(int i=0; i < npc[1].length; i++) {
			if(npc[currentMap][i] != null) {
				charactList.add(npc[currentMap][i]);
			}
		}
		
		for(int i=0; i < obj[1].length; i++) {
			if(obj[currentMap][i] != null) {
				charactList.add(obj[currentMap][i]);
			}
		}
		
		for(int i=0; i < mon[1].length; i++) {
			if(mon[currentMap][i] != null) {
				charactList.add(mon[currentMap][i]);
			}
		}
		
		for(int i=0; i < projectileList.size(); i++) {
			if(projectileList.get(i) != null) {
				charactList.add(projectileList.get(i));
			}
		}
		
		//Sort
		Collections.sort(charactList,new Comparator<Characters>(){
	
			@Override
			public int compare(Characters ch1, Characters ch2) {
				int res = Integer.compare(ch1.worldY, ch2.worldY);
				return res;
			}
			
		});
		
		//draw characters
		for(int i=0; i < charactList.size(); i++) {
			charactList.get(i).draw(g2);
		}
		
		//reset list
		charactList.clear();
		
		//UI
		ui.draw(g2);
	}
	//Debug
	if(keys.checkDrawTime == true) {
		long drawEnd = System.nanoTime();
		long passed = drawEnd - drawStart;
		g2.setColor(Color.white);
		g2.drawString("Draw Time: "+passed, 10, 400);
		System.out.println("Draw Time: "+passed);
	}
	g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
	public String generujID() {
		String id = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toLowerCase();
					return  id;
				}
	}
