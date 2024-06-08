package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import core.Position;
import rpggame.GamePanel;
import rpggame.Pathfinder;

public class Monster extends Entity {
	
	public int screenX;
	public int screenY;
	private int monsterType = 0;
	
	public Monster(GamePanel gp, int monsterType)
	{
		this.gp = gp;
		this.monsterType = monsterType;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2- (gp.tileSize/2);
		SetDefaultValues();
		getMonsterImage();
		
		hitPoints = 4;
		armorClass = 10;
		
	}
	
	public void SetDefaultValues() {
		worldX = gp.tileSize * 8;
		worldY = gp.tileSize * 22;
		//worldX = gp.tileSize * 20;
		//worldY = gp.tileSize * 25;

		speed = gp.tileSize;
		direction = "down";
	}
	
	public void getMonsterImage() {
		if (this.monsterType == 1)
		{
			try {
				up1 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton1_32.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton2_32.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton3_32.png"));
				//down2 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton2.png"));
				//right1 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton.png"));
				//right2 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton2.png"));
				//left1 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton.png"));
				//left2 = ImageIO.read(getClass().getResourceAsStream("/tiles/skeleton2.png"));
	
				hit = ImageIO.read(getClass().getResourceAsStream("/tiles/hit.png"));
	
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public void update(){
		String message;
		
		//System.out.println("Monster WorldX: " + worldX + " WorldY: " + worldY);
		
		// Check for collision
		int col = worldX/gp.tileSize;
		int row = worldY/gp.tileSize;

		// Death
		if (hitPoints < 1) {
			gp.monsters.remove(this);
			return;
		}
		
		// Pathfinding
		Position start = new Position(worldX, worldY, gp.tileSize);
		Position target = new Position(gp.player.worldX, gp.player.worldY, gp.tileSize);
		
		List<Position> path = Pathfinder.findPath(start, target, gp);
		
		if (!path.isEmpty()) {
			Iterator itr = path.iterator();
			while (itr.hasNext()) {
				Position pos = (Position)itr.next();
				//System.out.println("Pos: [" + pos.getCol() + "].[" + pos.getRow()+ "]");
			}
			
			Position pos = path.get(0);
			
			// Auto Attack
			if (pos.getCol() == gp.player.worldX / gp.tileSize
					&& pos.getRow() == gp.player.worldY / gp.tileSize)
			{
				//System.out.printf("Monster attack from [%d].[%d]\n", pos.getCol(), pos.getRow());
				this.attack(pos, gp.player);
				gp.player.playerTurn = true;
			}
			else 
			{
				// Check for open tile around the 
				if (gp.checkTileForMonster(pos.getCol(), pos.getRow(), gp.tileSize) != null)
				{
					System.out.printf("%s Blocked by [%d][%d]\n", name, pos.getCol(), pos.getRow());
				}
				else
				{
				worldX = pos.getCol() * gp.tileSize;
				worldY = pos.getRow() * gp.tileSize;
				}
			}
		}
		else {
			System.out.println("monsterEmpty path");
		
		}
		
		
		// TODO: Study why this works
		screenX = worldX - gp.player.worldX + gp.player.screenX;
		screenY = worldY - gp.player.worldY + gp.player.screenY;
		

		
//		System.out.println("Monster WorldX: " + worldX + " WorldY: " + worldY);
		//System.out.println("Monster screenX: " + screenX + " screenY: " + screenY);
		//System.out.println("Monster mov: x: " + col + ", " + row);
	}
	
	public void draw(Graphics2D g2) {
		//System.out.println("Draw Monster");
		
		// hit image count
		if (hitState == true)
		{
			hitCounter++;
			if (hitCounter > 50) {
				hitState = false;
				hitCounter =0;
			}
		}
		 
		// toggle the image for "animation"
		spriteCounter++;
		if (spriteCounter > 30) 
		{
			if (spriteNum == 1) 
			{
				spriteNum = 2;
			} else if (spriteNum == 2) 
			{
				spriteNum = 3;
			} else if (spriteNum == 3) 
			{
				spriteNum = 1;
				
			}
			spriteCounter = 0;
		}
		
		
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		BufferedImage image = null;
		//System.out.printf("monster dir: %s\n", direction);
		if (spriteNum == 1) {
			image = up1;
		}
		if (spriteNum == 2) {
			image = up2;
		}
		if (spriteNum == 3) {
			image = down1;
		}
		
		/*
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
		if (hitState == true)
		{
			image = hit;
		}
		*/
		//System.out.println("Draw Monster screenX: " + screenX + " screenY: " + screenY);
		// Monster is not always in the center of the screen
//		System.out.println("Monster WorldX: " + worldX + " WorldY: " + worldY);
//		System.out.println("Monster screenX: " + screenX + " screenY: " + screenY);
		//System.out.println("monster draw");
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
		
	}
	public Position getMonsterPosition()
	{
		int col = worldX/gp.tileSize;
		int row = worldY/gp.tileSize;
		Position pos = new Position(col, row, gp.tileSize);
		return pos;
	}
}
