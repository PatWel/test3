package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import rpggame.GamePanel;

public class Monster extends Entity {
	
	public int screenX;
	public int screenY;
	
	public Monster(GamePanel gp)
	{
		this.gp = gp;

		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2- (gp.tileSize/2);
		SetDefaultValues();
		getMonsterImage();
		
	}
	
	public void SetDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		//worldX = gp.tileSize * 20;
		//worldY = gp.tileSize * 25;

		speed = gp.tileSize;
		direction = "down";
	}
	
	public void getMonsterImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton2.png"));


		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void update(){
		String message;
		
		//System.out.println("Monster WorldX: " + worldX + " WorldY: " + worldY);
		
		// Check for collision
		int col = worldX/gp.tileSize;
		int row = worldY/gp.tileSize;

		
		// Testing - random direction
		Random rand = new Random();
		
		int max = 4;
		int min = 1;
		int dir = rand.nextInt(max - min + 1) + min;
		
		if (dir == 1) {
			direction = "up";
			if (gp.cChecker.CheckTargetTile(direction, row, col) == false)
			{
				worldY -= speed;
			}
		}
	
		if (dir == 2) {
			direction = "down";
			if (gp.cChecker.CheckTargetTile(direction, row, col) == false)
			{
				worldY += speed;
			}
		}
		if (dir == 3) {
			direction = "left";
			if (gp.cChecker.CheckTargetTile(direction, row, col) == false)
			{
				worldX -= speed;
			}
		}
		if (dir == 4) {
			direction = "right";
			if (gp.cChecker.CheckTargetTile(direction, row, col) == false)
			{
				worldX += speed;
			}
		}

		{
			System.out.println("Monster dir: " + direction + " " + dir);
		}
		spriteNum = 1;

		// Adjust the screenX and Y
		//screenX = worldX/gp.tileSize; // - (gp.tileSize/2);
		//screenY = worldY/gp.tileSize;

		col = worldX/gp.tileSize;
		row = worldY/gp.tileSize;

		// TODO: Study why this works
		screenX = worldX - gp.player.worldX + gp.player.screenX;
		screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		//System.out.println("Monster WorldX: " + worldX + " WorldY: " + worldY);
		//System.out.println("Monster screenX: " + screenX + " screenY: " + screenY);
		//System.out.println("Monster mov: x: " + col + ", " + row);
	}
	
	public void draw(Graphics2D g2) {
		//System.out.println("Draw Monster");
		
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
		//System.out.println("Draw Monster screenX: " + screenX + " screenY: " + screenY);
		// Monster is not always in the center of the screen
//		System.out.println("Monster WorldX: " + worldX + " WorldY: " + worldY);
//		System.out.println("Monster screenX: " + screenX + " screenY: " + screenY);
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
