package gomspace.second.round.gridbot.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import gomspace.second.round.gridbot.logic.InfiniteGrid;
import gomspace.second.round.gridbot.logic.Machine;
import gomspace.second.round.gridbot.utils.Position;
import gomspace.second.round.gridbot.utils.Snapshot;
import gomspace.second.round.gridbot.utils.Step;

@Service
public class SimulationService {

	private Machine machine;
	private InfiniteGrid grid;
	private int renderHeight = 20;
	private int renderWidth = 20;
	private int slideEffectMargin = 5;
	private Position renderStart;
	private Step currentStep;
	private Position baseCell, newCell, adjustedPosition;
	private Snapshot snapshot;

	@PostConstruct
	public void initialize() {
		// create the grid
		grid = new InfiniteGrid();
		renderStart = new Position(0, 0);
		// put the machine inside of the rendering window
		// which is inside the grid block
		machine = new Machine(new Position(9, 9));
		snapshot = new Snapshot(renderWidth, renderHeight);

		currentStep = Step.START;
	}

	public Snapshot nextFrame() {
		// running the simulation

		// get machine's position in the current grid block
		baseCell = machine.getPosition();

		// move the machine based on the color of the cell in the grid
		machine.turn(grid.isBlackInLocal(baseCell));
		newCell = machine.moveForward();

		// flip the color
		grid.flipInLocal(baseCell);

		// update simulation when the machine moves out of the current grid block
		// this ensures the machine is in the selected grid block at the end of the move
		// and it's position is local to the block in question
		updateSimulation(newCell);
		snapshot.move++;
		return render();
	}

	public Snapshot nextFrameByStep() {
		switch (currentStep) {
		case START:
		case TURN_MACHINE:
			baseCell = machine.getPosition();
			machine.turn(grid.isBlackInLocal(baseCell));
			break;
		case MOVE_FORWARD:
			newCell = machine.moveForward();
			break;
		case FLIP_BASE_CELL:
			grid.flipInLocal(baseCell);
			updateSimulation(newCell);
			snapshot.move++;
			break;
		}
		currentStep = currentStep.next();
		return render();
	}

	public String SimulationAfter(int moves) {
		// return the whole grid
		for (int i = 0; i < moves; i++) {
			baseCell = machine.getPosition();
			machine.turn(grid.isBlackInLocal(baseCell));
			newCell = machine.moveForward();
			grid.flipInLocal(baseCell);
			updateSimulation(newCell);
		}
		snapshot.hideMachine = true;
		String world = grid.renderWorld();
		return world;
	}

	private void updateSimulation(Position newPosition) {
		// update the grid with the new position and adjust machine's position
		// accordingly
		adjustedPosition = grid.update(newPosition);
		machine.setPositionAt(adjustedPosition);
	}

	private Snapshot render() {

		// if the machin's position is in the slideEffectMargin
		// slide the renderingStart along with the machine
		// to keep it inside the rendered window
		Position machineAbsolutePos = grid.calculatePositionAbsolute(machine.getPosition());

		if (machineAbsolutePos.x - renderStart.x < slideEffectMargin)
			renderStart.x--;
		else if (renderStart.x + renderWidth - machineAbsolutePos.x < slideEffectMargin)
			renderStart.x++;

		if (machineAbsolutePos.y - renderStart.y < slideEffectMargin)
			renderStart.y--;
		else if (renderStart.y + renderHeight - machineAbsolutePos.y < slideEffectMargin)
			renderStart.y++;

		// render the grid based on the rendering start
		boolean[][] gridResult = grid.renderAtPosition(renderStart, renderWidth, renderHeight);
		snapshot.setGrid(gridResult);
		snapshot.machineDirection = machine.d;
		snapshot.machineX = machineAbsolutePos.x - renderStart.x;
		snapshot.machineY = machineAbsolutePos.y - renderStart.y;
		return snapshot;
	}

}
