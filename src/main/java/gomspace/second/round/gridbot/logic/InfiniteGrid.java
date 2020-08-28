package gomspace.second.round.gridbot.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import gomspace.second.round.gridbot.utils.Position;

public class InfiniteGrid {

	private Map<Position, boolean[][]> gridMap;
	private final int width = 100;
	private final int height = 100;
	private Position currentGridPos;
	private int gridTop = 0, gridLeft = 0, gridRight = 0, gridBottom = 0;

	public InfiniteGrid() {
		currentGridPos = new Position(0, 0);
		gridMap = new HashMap<Position, boolean[][]>();
		putGridToPosition(currentGridPos);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isBlackInLocal(Position cell) {
		return gridMap.get(currentGridPos)[cell.x][cell.y];
	}

	public void flipInLocal(Position baseCell) {
		gridMap.get(currentGridPos)[baseCell.x][baseCell.y] = !gridMap.get(currentGridPos)[baseCell.x][baseCell.y];
	}

	private void putGridToPosition(Position pos) {
		if (!gridMap.containsKey(pos)) {
			boolean[][] newGrid = new boolean[width][height];
			gridMap.put(pos, newGrid);
		}
	}
	
	public Position calculatePositionAbsolute(Position localPosition) {
		Position absolute = new Position(currentGridPos.x * width + localPosition.x,currentGridPos.y * height + localPosition.y);
		return absolute;
	}

	public Position update(Position newPosition) {
		// the newPosition of the machine could be out of the current grid.
		// shift the current grid to the new grid
		// and return the position of the machine relative to the selected grid.
		Position gridPosition = new Position(currentGridPos);
		Position adjustedPosition = new Position(newPosition);
		if (newPosition.x >= width) {
			if (++gridPosition.x > gridRight)
				gridRight = gridPosition.x;
			adjustedPosition.x -= width;
		} else if (newPosition.x < 0) {
			if (--gridPosition.x < gridLeft)
				gridLeft = gridPosition.x;
			adjustedPosition.x += width;
		} else if (newPosition.y >= height) {
			if (++gridPosition.y > gridBottom)
				gridBottom = gridPosition.y;
			adjustedPosition.y -= height;
		} else if (newPosition.y < 0) {
			if (--gridPosition.y < gridTop)
				gridTop = gridPosition.y;
			adjustedPosition.y += height;
		}
		putGridToPosition(gridPosition);
		return adjustedPosition;
	}

	public boolean[][] renderWorld() {
		int worldWidth = gridRight - gridLeft + 1;
		int worldHeight = gridBottom - gridTop + 1;

		boolean[][] world = new boolean[worldWidth][worldHeight];

		for (Entry<Position, boolean[][]> entry : gridMap.entrySet()) {
			Position key = entry.getKey();
			boolean[][] grid = entry.getValue();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					// project the cell onto the world
					world[(key.x - gridLeft) * width + i][(key.y - gridTop) * height + j] = grid[i][j];
				}
			}
		}

		return world;
	}

	private boolean isBlackInWorld(Position cell) {
		// get the possible grid block that contains the cell
		Position cellInGrid = new Position(0, 0);
		Position grid = new Position(0, 0);

		grid.x = cell.x / width;
		grid.y = cell.y / height;
		cellInGrid.x = cell.x % width;
		cellInGrid.y = cell.y % height;

		// cell position in grid has to be positive
		if (cell.x < 0) {
			grid.x--;
			cellInGrid.x += width;
		}
		if (cell.y < 0) {
			grid.y--;
			cellInGrid.y += height;
		}

		// check the existence of such grid
		if (gridMap.containsKey(grid))
			return gridMap.get(grid)[cellInGrid.x][cellInGrid.y];
		else
			return false;
	}

	public boolean[][] renderAtPosition(Position start, int width, int height) {
		boolean[][] window = new boolean[width][height];
		Position cell = new Position(0, 0);
		for (int i = start.x; i < start.x + width; i++) {
			for (int j = start.y; j < start.y + height; j++) {
				cell.x = i;
				cell.y = j;
				window[i][j] =  isBlackInWorld(cell);
			}
		}
		return window;
	}

}
