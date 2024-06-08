package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Position;
import rpggame.GamePanel;

public class Entity {
	public int worldX,worldY;
	public int speed;
	
	//public  int screenX;
	//public  int screenY;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction; 
	public BufferedImage hit;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea;
	public boolean collisionOn = false;
	
	// Try this here
	public GamePanel gp;
	public int initiative = 0;
	
	// D&D Stats
	public int hitPoints = 10;
	public int armorClass = 15;
	public int weaponDamage = 3;
	public String name = "";

	public boolean hitState = false;
	public int hitCounter = 0;
	
	public void setWorldPos( int x, int y)
	{
		this.worldX = x * gp.tileSize;
		this.worldY = y * gp.tileSize;
	}
	
	public int calculateInitiative(int max, int min)
	{
		int initiative = gp.rand.nextInt((max - min) + min)+1;
		return initiative;
	}
	
	// Start with attack function
	public int attack(Position pos, Entity target)
	{
		int roll = gp.rollDice(20, 1);
		int damage = 0;
		System.out.print(this.name + " attacks " + target.name + " rolls: " + roll);
		if (roll >= target.armorClass)
		{
			System.out.println(" hit");
			target.hitState = true;
			damage = gp.rollDice(8, 2);
			damage =1;
			System.out.println("For damage: " + damage + " leaving " + target.name + " HP:" + (target.hitPoints - damage));
			
			target.hitPoints -= damage;
			if (target.hitPoints  < 1)
			{
				System.out.println(target.name + " Died");
			}
		}
		else
		{
			System.out.println(" miss");
		}
		
		return damage;
	}
}
