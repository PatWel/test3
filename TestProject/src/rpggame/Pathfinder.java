package rpggame;

	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.HashSet;
	import java.util.Iterator;
	import java.util.List;
	import java.util.PriorityQueue;
	import java.util.Set;

	import core.Position;

public class Pathfinder 
{
	public static List<Position> findPath(Position source, Position target, GamePanel gp)
	{
		final PriorityQueue<Node> open = new PriorityQueue<>();
		final Set<Node> closed = new HashSet<>();
		final Node[][] nodeMap = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		Node current;
		
		for (int x = 0; x< nodeMap.length; x++)
		{
			for (int y=0; y<nodeMap[0].length; y++ )
			{
				int heuristic = Math.abs(x - target.getCol()) + Math.abs(y - target.getRow());
				
				// Assign a heavy movement cost if the Tile contains a monster
				int nodeCost = 1;
				if (gp.checkTileForMonster(x, y, gp.tileSize) != null)
				{
					nodeCost = 50;
				}
				Node node = new Node(nodeCost, heuristic, x, y, gp.tileSize);
				
				if (!gp.cChecker.IsTileAvailable(x, y)) {
					closed.add(node);
				}
				
//				else
//				{
	//				if (gp.checkTileForMonster(x, y, gp.tileSize) != null) {
	//					closed.add(node);
		//			}
 			//	}

				nodeMap[x][y] = node;
				
			}
		}
		
		Node startNode = nodeMap[source.getCol()][source.getRow()];
		Node targetNode = nodeMap[target.getCol()][target.getRow()];
		
		open.add(startNode);
		
		do {
			current = open.poll();
			closed.add(current);
			
			// monster title?
//			if (gp.checkTileForMonster(current.col, current.row, gp.tileSize) != null)
//			{
//				return extractPath(current, gp.tileSize);				
//			}
			
			if (current.equals(targetNode)) {
				return extractPath(current, gp.tileSize);
			}
			
			// Get neighbors
			ArrayList<Node> neighbors = new ArrayList<>();
			
			Node neighbor;
			
			//West
			int x = current.col -1;
			int y = current.row;
			
			if (x >= 0)
			{
				neighbor = nodeMap[x][y];
				neighbors.add(neighbor);
			}
			
			//North
			x = current.col;
			y = current.row - 1;
			
			if (y>= 0) 
			{
				neighbor = nodeMap[x][y];
				neighbors.add(neighbor);
			}
			
			//East
			x = current.col + 1;
			y = current.row;
			
			if (x<gp.maxWorldCol)
			{
				neighbor = nodeMap[x][y];
				neighbors.add(neighbor);
			}
			
			//South
			x = current.col;
			y = current.row + 1;
			
			if (y<gp.maxWorldRow)
			{
				neighbor = nodeMap[x][y];
				neighbors.add(neighbor);
			}
			
			Iterator itr = neighbors.iterator();
			
			while (itr.hasNext())
			{
				neighbor = (Node)itr.next();
				
				if (closed.contains(neighbor)) {
					continue; 
				}
				
				int calculatedCost = neighbor.heuristic + neighbor.moveCost + current.totalCost;
				
				if (calculatedCost < neighbor.totalCost || !open.contains(neighbor)) {
					neighbor.totalCost = calculatedCost;
					neighbor.parent = current;
					
					if (!open.contains(neighbor)) {
						open.add(neighbor);
					}
				}
			}
			
		} while (!open.isEmpty());
		
		System.out.println("Unreachable");
		
		return List.of(source);
		
	}
	
	private static List<Position> extractPath(Node current, int tileSize) {
		List<Position> path = new ArrayList<>();
		
		while (current.parent != null) {
			path.add(current.getPosition(current.tileSize));
			current = current.parent;
		}
		
		Collections.reverse(path);
		return path;
	}

	//public static 
	private static class Node implements Comparable<Node>{
		private int moveCost;
		private int heuristic;
		private int col;
		private int row;
		private int totalCost;
		private Node parent;
		private int tileSize;


		public Node(int moveCost, int heuristic, int col, int row, int tileSize) {

			this.moveCost = moveCost;
			this.col = col;
			this.row = row;
			this.heuristic = heuristic;
			this.tileSize = tileSize;
		}


		@Override
		public int compareTo(Node that) {
			return Integer.compare(this.totalCost,  that.totalCost);
		}

		public Position getPosition(int tileSize)
		{
			return Position.ofGridPosition(col, row, tileSize);
		}
	}
}
