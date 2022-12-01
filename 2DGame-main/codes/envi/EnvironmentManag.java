package envi;

import java.awt.Graphics2D;

import main.GameScreen;

public class EnvironmentManag {
	
	GameScreen gs;
	public Light light;
	
	public EnvironmentManag(GameScreen gs) {
		this.gs = gs;
		
		
	}
	
	public void setUP() {
		light = new Light(gs);
	}
	
	public void update() {
		light.update();
	}
	
	public void draw(Graphics2D g2) {
		light.draw(g2);
	}
}
