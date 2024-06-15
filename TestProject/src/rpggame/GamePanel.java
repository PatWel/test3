package rpggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import entity.Entity;
import entity.Monster;
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
	public ArrayList<Entity> party = new ArrayList<Entity>(4);
	
	public static Random rand = new Random();

	Thread gameThread;
	//public MessagePanel mPanel = new MessagePanel();
	
	public MessagePanel mPanel = new MessagePanel();;

	boolean showGameInfo = true;
	
	//////////////////////////////////////////////////////////////////////
	// World Objects
	//////////////////////////////////////////////////////////////////////
	// MWandering Monsters
	public ArrayList<Entity> monsters = new ArrayList<Entity>();
	//public ArrayList<Entity> party = new ArrayList<Entity>();
	
			
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		party.add(player);
	}

	public void StartGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public Monster createMonster(int monsterType)
	{
		Monster m = new Monster(this, monsterType);
		monsters.add(m);
		m.name = "Skully " + monsters.size();
		return m;
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
		
		/////////////////////////////////////////////////////
		// Test monster
		/////////////////////////////////////////////////////
		this.createMonster(1).setWorldPos(8,22);
		this.createMonster(1).setWorldPos(8, 30);
		this.createMonster(1).setWorldPos(23, 12);
		this.createMonster(1).setWorldPos(8,20);
		this.createMonster(1).setWorldPos(10, 12);
		this.createMonster(1).setWorldPos(23, 15);
		
		for (int i = 0; i<20; i++)
		{
			this.createMonster(1).setWorldPos(23, 43-i);
		}
		//this.createMonster(1);
		//this.createMonster(1);
		//this.createMonster(1);
		
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
			player.update();
		}
		if (player.playerTurn == false)
		{
			//System.out.println("World Update");
			updateWorld();
			player.playerTurn = true;
		}

	}
	
	// Testing combat
	public void testCombat()
	{
		Combat c = new Combat(monsters, party);
	}
	
	// Update all world objects
	public void updateWorld() {
    	//System.out.println("update monsters");
	    for (int i = 0; i < monsters.size(); i++) {
	    	//ystem.out.println("update monster "+ i);
	        ((Monster) monsters.get(i)).update();
	      }	
	}
	
	public void paintComponent(Graphics g) {
		//System.out.println("Paint");
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		player.draw(g2);
		drawWorld(g2);
		
		if (this.showGameInfo) {
			Font messageFont = 	new Font("Minecraft Seven",Font.BOLD,20);
			g2.setFont(messageFont);
			g2.setColor(Color.WHITE);
			 String temp = player.name+ " WX: " + player.worldX + " WY: " + player.worldY +  "[" + player.worldX/tileSize + "][" + player.worldY/tileSize + "]";
			g2.drawString(temp,10,20);
			 temp = player.name+ " WX: " + player.worldX + " WY: " + player.worldY;
		}
		// Grid
		//g2.setColor(Color.red);
		//g2.fillRect(0, 0, tileSize, tileSize);

		//g2.setColor(Color.blue);
		//g2.fillRect(0, tileSize, tileSize, tileSize);
		
		//g2.dispose();
		//System.out.println("X: " + playerX);
	}
	
	public void drawWorld(Graphics2D g2) {
		//Graphics2D g2 = (Graphics2D)g;
				
	    for (int i = 0; i < monsters.size(); i++) {
	        ((Monster) monsters.get(i)).draw(g2);
	      }
		//g2.dispose();
	}
	
	public static int rollDice(int max, int min)
	{
		return rand.nextInt(max-min)+min+1;
	}
	
	public Entity checkTileForMonster(int col, int row, int tileSize)
	{	
		for (int i = 0; i < monsters.size(); i++) {
			int colm = monsters.get(i).worldX/tileSize;
			int rowm = monsters.get(i).worldY/tileSize;
			
			if (col == colm && row == rowm)
			{
				return monsters.get(i);
			}
	      }
	    
		return null;
		
	}
}
