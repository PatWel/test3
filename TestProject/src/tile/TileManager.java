package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import core.UtilityTool;
import rpggame.GamePanel;

public class TileManager {

	public GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp)
	{
		this.gp = gp;
		tile = new Tile[20];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		GetTileImage();
		LoadMap("/maps/world01.txt");
	}
	
	public void GetTileImage()
	{
			setup(0, "grass1_32", false, false);
			setup(1, "hills2_32", true, true);
			setup(2, "water00", true, false); 
			setup(3, "tree4_32", true, true); 
			setup(4, "tree4_32", true, false); 
			setup(5, "floor", false, false); 
			setup(6, "town", false, false); 
			setup(7, "castle", false, false); 
			setup(8, "hills3_32", false, false); 
			setup(9, "hills4_32", true, true); 
			setup(10, "hit", false, false); 
			
			//setup(10, "I", true, false); 
			//setup(11, "hit_32", true, false); 			
			
			
			/*
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass01.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water00.png"));
			tile[2].collision = true;

			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree1.png"));
			tile[3].collision = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree1.png"));
			tile[4].collision = true;

			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor.png"));
		}
		*/
	}
		
	public void setup(int index, String path, boolean collision, boolean obstacle) {
		UtilityTool uTool = new UtilityTool();

		try
		{
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + path + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			tile[index].index = index;
			tile[index].obstacle = obstacle;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		
	public void LoadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br= new BufferedReader(new InputStreamReader(is));
			
			int row = 0;
			int col = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
						String numbers[] = line.split(" ");
						int num = Integer.parseInt(numbers[col]);
						mapTileNum[col][row] = num;
						col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			// Optimize to only draw visible tiles
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
			{
				g2.drawImage(tile[tileNum].image, screenX,screenY, gp.tileSize, gp.tileSize, null);				
			}

			worldCol ++;
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
