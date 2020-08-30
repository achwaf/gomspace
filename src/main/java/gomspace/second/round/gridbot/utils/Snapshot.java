package gomspace.second.round.gridbot.utils;

public class Snapshot {
	public boolean hideMachine = false;
	public int machineX;
	public int machineY;
	public int move;
	public Direction machineDirection;
	public int gridheight;
	public int gridWidth;
	public int[][] grid;
	
	
	public Snapshot(int width, int height) {
		this.gridheight = height;
		this.gridWidth = width;
	}
	
	public void setGrid(boolean[][] grid) {
		this.grid = new int[gridWidth][gridheight];
		for (int i = 0; i < gridWidth; i++) {
			for (int j =0; j < gridheight; j++) {
				this.grid[i][j] = grid[i][j]?1:0;
			}
		}
	}
}
