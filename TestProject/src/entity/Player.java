package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpggame.Combat;
import rpggame.GamePanel;
import rpggame.KeyHandler;
import rpggame.KeyHandler.CommandLevel;
import tile.Tile;
import core.Position;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;

	public boolean playerTurn = true;
	public long playerIdleTime = 0;
	public long playerIdleStart = 0;
	private int state = 0;	// 0 = normal 1 = attack

	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2- (gp.tileSize/2);
		
		solidArea = new Rectangle(0,0,gp.tileSize, gp.tileSize);
		SetDefaultValues();
		getPlayerImage();
	}
	

	public void SetDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = gp.tileSize;
		direction = "South";
		name = "Babar";
		speed = 48;
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/player1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/player1.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/player1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/player1.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/player1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/player1.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/player1.png"));

			hit = ImageIO.read(getClass().getResourceAsStream("/tiles/hit.png"));
			
//			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
//			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_Up_2.png"));
//			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
//			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
//			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
//			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
//			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
//			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		} catch(IOException e) {
		e.printStackTrace();
		}
	}
	
	public void update() {

		Console_coords();
		String message;
		
		
		// Movement
		boolean invalidMove = false;
		
		// 2nd part of command.  Direction
		if (keyH.cmdLevel == CommandLevel.CMD_LEVEL_2)
		{
			if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
					|| keyH.rightPressed == true) {
		
				Monster monster = null;
				
				// Examine target
				if (keyH.leftPressed == true) {
					direction = "West";
					gp.mPanel.AddMessageText(direction, false);
					keyH.leftPressed = false;
					keyH.cmdLevel = CommandLevel.CMD_LEVEL_1;
					//System.out.println("worldX: " + worldX + " + worldY: " + worldY);
					monster = (Monster)gp.checkTileForMonster((int)worldX/gp.tileSize-1,(int)worldY/gp.tileSize, gp.tileSize); 
					if (monster != null)
					{
						//System.out.println("Attack monster " + monster.name);
						attack(monster.getMonsterPosition(), monster);
						playerTurn = false;
						playerIdleStart = System.nanoTime();
					}
					else
					{
						gp.mPanel.AddMessageText("Nothing There!", false);
						playerTurn = false;
						playerIdleStart = System.nanoTime();
					}
				}
				if (keyH.rightPressed == true) {
					direction = "East";
					gp.mPanel.AddMessageText(direction, false);
					keyH.rightPressed = false;
					keyH.cmdLevel = CommandLevel.CMD_LEVEL_1;
					monster = (Monster)gp.checkTileForMonster((int)worldX/gp.tileSize+1,(int)worldY/gp.tileSize, gp.tileSize); 
					if (monster != null)
					{
						//System.out.println("Attack monster " + monster.name);
						attack(monster.getMonsterPosition(), monster);
						playerTurn = false;
						playerIdleStart = System.nanoTime();
					}
					else
					{
						gp.mPanel.AddMessageText("Nothing There!", false);
						playerTurn = false;
						playerIdleStart = System.nanoTime();
					}
				}
				if (keyH.upPressed == true) {
					direction = "North";
					gp.mPanel.AddMessageText(direction, false);
					keyH.upPressed = false;
					keyH.cmdLevel = CommandLevel.CMD_LEVEL_1;
					monster = (Monster)gp.checkTileForMonster((int)worldX/gp.tileSize,(int)worldY/gp.tileSize-1, gp.tileSize); 
					if (monster != null)
					{
						//System.out.println("Attack monster " + monster.name);
						attack(monster.getMonsterPosition(), monster);
						playerTurn = false;
						playerIdleStart = System.nanoTime();
					}
					else
					{
						gp.mPanel.AddMessageText("Nothing There!", false);
						playerTurn = false;
						playerIdleStart = System.nanoTime();
					}
				}
				if (keyH.downPressed == true) {
					direction = "South";
					gp.mPanel.AddMessageText(direction, false);
					keyH.downPressed = false;
					keyH.cmdLevel = CommandLevel.CMD_LEVEL_1;
					monster = (Monster)gp.checkTileForMonster((int)worldX/gp.tileSize,(int)worldY/gp.tileSize+1, gp.tileSize); 
					if (monster != null)
					{
						//System.out.println("Attack monster " + monster.name);
						attack(monster.getMonsterPosition(), monster);
						playerTurn = false;
						playerIdleStart = System.nanoTime();
					}
					else
					{
						gp.mPanel.AddMessageText("Nothing There!", false);
						playerTurn = false;
						playerIdleStart = System.nanoTime();
					}
				}
				
			}			
		}
		else
		if (keyH.cmdLevel == CommandLevel.CMD_LEVEL_1)
		{
			//System.out.println(" Player update Level 1 command" + keyH.leftPressed);
			
			if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
					|| keyH.rightPressed == true) {
	
				// Check for collision
				int col = worldX/gp.tileSize;
				int row = worldY/gp.tileSize;

				int tileSpan = speed/gp.tileSize;
				
				System.out.println("tileSpan: " + tileSpan);
				if (keyH.leftPressed == true) {
					direction = "West";
					
					//for (int i=0; i<tileSpan; i++)
					//{
						if (gp.cChecker.CheckTargetTile(direction, col, row) == true)
						{
							worldX -= speed;
						}
						else
						{
							invalidMove = true;
						}
					//}
					keyH.leftPressed = false;
				} else if (keyH.rightPressed == true) {
					direction = "East";

					//for (int i=0; i<tileSpan; i++)
					//{
						if (gp.cChecker.CheckTargetTile(direction, col, row) == true)
						{
							worldX += speed;
						}
						else
						{
							invalidMove = true;
							//break;
						}
					//}
					keyH.rightPressed = false;
				} else if (keyH.upPressed == true) {
					direction = "North";

					//for (int i=0; i<tileSpan; i++)
					//{
						if (gp.cChecker.CheckTargetTile(direction, col, row) == true)
						{
							worldY -= speed;
						}
						else
						{
							invalidMove = true;
							//break;
						}
					//}
					keyH.upPressed = false;
				} else if (keyH.downPressed == true) {
					direction = "South";

					//for (int i=0; i<tileSpan; i++)
					//{
	
						if (gp.cChecker.CheckTargetTile(direction, col, row) == true)
						{
							worldY += speed;
						}
						else
						{
							invalidMove = true;
							//break;
						}
					//}
					keyH.downPressed = false;
				}
			
				if (invalidMove == true) {
					message = direction + "\r\nInvalid Move!";
					gp.mPanel.AddMessageText(message, false);
				}
				else
				{
					gp.mPanel.AddMessageText(direction, false);
				}
			
				playerTurn = false;
				playerIdleStart = System.nanoTime();
			
			//collisionOn = false;
			//gp.cChecker.CheckTile(this);
			
				spriteCounter++;
				if (spriteCounter > 12) {
					if (spriteNum == 1) {
						spriteNum = 2;
					} else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			}
		}

		if (keyH.attackPressed == true) {
			//////////////////////////////////////////
			//Prompt for direction
			//////////////////////////////////////////
			gp.mPanel.AddMessageText("Attack-", true);
			keyH.attackPressed = false;
			this.state = 1;
			keyH.cmdLevel = CommandLevel.CMD_LEVEL_2;
			//subCommandNeeded = true;
		}		

		if (keyH.peerPressed == true) {
			//////////////////////////////////////////
			//Prompt for direction
			//////////////////////////////////////////
			gp.mPanel.AddMessageText("Direction->", true);
		}
		if (keyH.testPressed == true) {
			gp.testCombat();
			keyH.testPressed = false;
		}		
	
		
		// Skip turn after certain number of seconds
		long idleTime = System.nanoTime() - playerIdleStart;
		long passLimit = 10000000000L;
		if (idleTime > passLimit)
		{
			keyH.spacePressed = true;
		}
		
		if (keyH.spacePressed == true)
		{
			gp.mPanel.AddMessageText("Pass", false);
			playerTurn = false;
			playerIdleStart = System.nanoTime();
			
			keyH.spacePressed = false;
		}
		//System.out.println("Player WorldX: " + worldX + " WorldY: " + worldY);
		//System.out.println("Player screenX: " + screenX + " screenY: " + screenY);	
	}
	
	public void Console_coords()
	{

		//System.out.println("World X: " + this.worldX);
		//System.out.println("World Col: " + this.worldX/gp.tileSize);
		
		//System.out.println("World Y: " + this.worldY);
	}
	
	public void draw(Graphics2D g2) {
		
		/////////////////////////////////////////////////
		// Hide non visible tiles
		/////////////////////////////////////////////////
		
		BufferedImage image = null;
		switch(direction) {
		case "North":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}	
			break;
		case "South":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "West":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;		
		case "East":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}			
			break;			
		}
		//System.out.println("Player WorldX: " + worldX + " WorldY: " + worldY);
		//System.out.println("Player WorldX: " + worldX /gp.tileSize + " WorldY: " + worldY / gp.tileSize);
		Position pos = new Position(worldX, worldY, gp.tileSize);
		
		// Set image to "splat" for a hit.  Show for 50 ticks
		if (hitState == true)
		{
			hitCounter++;
			if (hitCounter > 50) {
				hitState = false;
				hitCounter =0;
			}
		}
		if (hitState == true) {
			image = hit;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
