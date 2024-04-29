package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import rpggame.GamePanel;

public class Entity {
	public int worldX,worldY;
	public int speed;
	
	//public  int screenX;
	//public  int screenY;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction; 
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea;
	public boolean collisionOn = false;
	
	// Try this here
	public GamePanel gp;
	
}
