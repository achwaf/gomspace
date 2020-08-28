package gomspace.second.round.gridbot.utils;

public class Snapshot {
	public boolean hideMachine = false;
	public int machineX;
	public int machineY;
	public Direction machineDirection;
	public int gridheight;
	public int gridWidth;
	public boolean[][] grid;
	
	
	public Snapshot(int width, int height) {
		this.gridheight = height;
		this.gridWidth = width;
	}
}
