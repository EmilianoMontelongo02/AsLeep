package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTitleSize = 32; // 32 x 32 title 
	final int scale = 2;
	
	public final int titleSize = originalTitleSize * scale; // 64x64 tile
	final int maxScreenCol = 18;
	final int maxScreenRow = 14;
	final int screenWidth = titleSize * maxScreenCol; //1152
	final int screenHeight = titleSize * maxScreenRow; // 896
	
	//FPS
	int FPS = 60;
	
	KeyHandler KeyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this,KeyH);
	
	
	public GamePanel () {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; //0.01666 SECODNDS
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >=1) {
				update();
				repaint();
				delta--;
				drawCount++;
			
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
		
	}
	public void update() {
		
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		player.draw(g2);
	
		g2.dispose();
	}
}
