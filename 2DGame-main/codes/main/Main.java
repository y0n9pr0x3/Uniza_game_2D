package main;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
	static JFrame window;
	public static void main(String[] args) {
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/img/fakla.jpg")));
		window.setTitle("Unyza Game 1.6");
		
		GameScreen gameScreen = new GameScreen();
		window.add(gameScreen);
		gameScreen.config.loadConf();
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gameScreen.setupGame();
		
		gameScreen.startGameThread();
		
	}
}
