package rpggame;

import entity.Entity;
import tile.Tile;

public class CollisionChecker {

	GamePanel gp;
	
	public CollisionChecker(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void CheckTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		int tileNum1, tileNum2;
		
		//System.out.println("Tile1: " + tileNum1);
		//System.out.println("Tile2: " + tileNum1);
		
		
	}
	
	public boolean CheckTargetTile(String direction, int col, int row)
	{
		int tileNum = 0;
		Tile tileCheck;
		boolean ret = false;
		
		switch (direction)
		{
		case "West":
			col --;
			break;
		case "East":
			col++;
			break;
		case "North":
			row--;
			break;
		case "South":
			row++;
			break;
		}

		/*
		if (col>=0 && col < gp.maxWorldCol && row >=0 && row < gp.maxWorldRow) {
			tileNum = gp.tileM.mapTileNum[col][row];
			tileCheck = gp.tileM.tile[tileNum];
			return tileCheck.collision;
		}
		*/
		return IsTileAvailable(col, row);

	}
	
	public boolean IsTileAvailable(int x, int y)
	{
		int tileNum = 0;
		Tile tileCheck;

		tileNum = gp.tileM.mapTileNum[x][y];
		tileCheck = gp.tileM.tile[tileNum];
		return !tileCheck.collision;
	}
}
