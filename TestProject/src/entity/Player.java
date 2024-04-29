package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpggame.GamePanel;
import rpggame.KeyHandler;
import tile.Tile;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;

	public boolean playerTurn = true;
	public long playerIdleTime = 0;
	public long playerIdleStart = 0;
	
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
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/party.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/party.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/party.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/party.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/party.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/party.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/party.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/party.png"));

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
	
	public void Update() {

		Console_coords();
		String message;
		
		boolean invalidMove = false;
		
		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true) {



			// Check for collision
			int col = worldX/gp.tileSize;
			int row = worldY/gp.tileSize;
			

			if (keyH.leftPressed == true) {
				direction = "left";
				
				if (gp.cChecker.CheckTargetTile(direction, row, col) == false)
				{
					worldX -= speed;
				}
				else
				{
					invalidMove = true;
				}
				keyH.leftPressed = false;
			} else if (keyH.rightPressed == true) {
				direction = "right";
				
				if (gp.cChecker.CheckTargetTile(direction, row, col) == false)
				{
					worldX += speed;
				}
				else
				{
					invalidMove = true;

				}
				keyH.rightPressed = false;
			} else if (keyH.upPressed == true) {
				direction = "up";

				if (gp.cChecker.CheckTargetTile(direction, row, col) == false)
				{
					worldY -= speed;
				}
				else
				{
					invalidMove = true;

				}				
				keyH.upPressed = false;
			} else if (keyH.downPressed == true) {
				direction = "down";

				if (gp.cChecker.CheckTargetTile(direction, row, col) == false)
				{
					worldY += speed;
				}
				else
				{
					invalidMove = true;

				}
				keyH.downPressed = false;
			}
			
			//gp.mPanel.AddMessageText(direction);
			if (invalidMove == true) {
				message = direction + "\r\nInvalid Move!";
				gp.mPanel.AddMessageText(message);
			}
			else
			{
				gp.mPanel.AddMessageText(direction);
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

				
		// Skip turn after certain number of seconds
		long idleTime = System.nanoTime() - playerIdleStart;
		long passLimit = 10000000000L;
		if (idleTime > passLimit)
		{
			keyH.spacePressed = true;
		}
		
		if (keyH.spacePressed == true)
		{
			gp.mPanel.AddMessageText("pass");
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
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}	
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;		
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}			
			break;			
		}
		//System.out.println("Player WorldX: " + worldX + " WorldY: " + worldY);
		//System.out.println("Player screenX: " + screenX + " screenY: " + screenY);		

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
	}
}
