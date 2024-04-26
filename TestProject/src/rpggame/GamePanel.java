package rpggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = maxScreenCol * tileSize;
	public final int screenHeight = maxScreenRow * tileSize;
	
	// WORLD
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// FPS
	final int FPS = 60;
	
	public TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Player player = new Player(this, keyH);
	
//	int playerY = 100;
//	int playerX = 100;
//	int playerSpeed = 4;
	
	int playerY = 100;
	int playerX = 100;
	int playerSpeed = 4;

	//private int playerGameState_Player = 1;
	//int playerGameState = playerGameState_Player;
	//public long playerTurnStartTime = 0;
	
	Thread gameThread;
	public MessagePanel mPanel = new MessagePanel();
	

			
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void StartGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime  = 0;
		long timer = 0;
		int drawCount = 0;

		//long playerTurnTime = 0;

		//playerTurnStartTime = System.nanoTime();
		//int playerTurnCount = 0;
		
		while(gameThread != null)
		{
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime-lastTime);
			lastTime = currentTime;;

			// PlayerState counter
			//playerTurnTime = (currentTime - playerTurnStartTime);
			
			if (delta >= 1) {
				update();
				repaint();
				
				//System.out.println(count);
				delta--;
				drawCount++;
				
				//playerTurnCount++;
			}

			//FPS Counter
			if (timer > 1000000000) {
				//System.out.println("FPS: " + drawCount);
				timer = 0;
				drawCount = 0;
				
			}
		}
		// TODO Auto-generated method stub
		
	}
	
	public void update() {
		//if (keyH.leftPressed == true)
		//System.out.print("Pressed: " + keyH.leftPressed);
		if (player.playerTurn == true)
		{
			//System.out.println("Player Update");
			player.Update();
		}
		if (player.playerTurn == false)
		{
			System.out.println("World Update");
			player.playerTurn = true;
		}

	}
	
	public void paintComponent(Graphics g) {
		//System.out.println("Paint");
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		player.draw(g2);
		
		// Grid
		//g2.setColor(Color.red);
		//g2.fillRect(0, 0, tileSize, tileSize);

		//g2.setColor(Color.blue);
		//g2.fillRect(0, tileSize, tileSize, tileSize);
		
		g2.dispose();
		//System.out.println("X: " + playerX);
	}
	
}
