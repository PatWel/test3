package entity;

import java.awt.Color;

import rpggame.GamePanel;

public class Actor {
	
	public int column = 0;
	public int row = 0;
	public int energy = 0;
	public String name;
	public int energyLimit =10;
	private int moves = 0;
	Color color;
	Action nextAction = null;
	boolean PlayerCharacter = false;
	GamePanel gp = null;
	
	public Actor(String name, Color color, int limit, int column, int row, GamePanel gp) {
		this.name = name;
		this.color = color;
		this.energyLimit = limit;
		this.column = column;
		this.row = row;
		this.gp = gp;
	}
	
	public void setNextAction(Action newAction)
	{
		this.nextAction = newAction;
	}
	
	public Action getAction() {
		
		Action action = nextAction;
		if (action == null) return null;
		
//		energy += 1;
//		if (energy >= energyLimit)
//		{
//		   //System.out.println(name + " Energy met: " + energy + " moves: " + moves);
//		   energy = 0;
//		   moves++;
//		   nextAction = null;
//		   }
//		else
//		{
//			action = null;
//			System.out.println(name + " not enough Energy: " + energy);
//		}
		return action;
	}
	
	public void update()
	{
		
	}
	//publ2ic void draw()
}



