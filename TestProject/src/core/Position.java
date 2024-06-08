package core;

import rpggame.GamePanel;

public class Position {
	
	int screenX;
	int screenY;
	int worldX;
	int worldY;
	int col;
	int row;
	int tileSize;
	
	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	//GamePanel gp;
	
	public Position(int x, int y, int tileSize) {
		worldY = y;
		worldX = x;
		col = x / tileSize;
		row = y / tileSize;
		this.tileSize = tileSize;
		
		//System.out.println("WorldX: " + worldX + " WorldY: " + worldY );
		//System.out.println("GridX: " + col + " GridY: " + row);
	}
	
	public static Position ofGridPosition(int col, int row, int tileSize) {
		return new Position(col*tileSize, row*tileSize, tileSize);
	}
}
