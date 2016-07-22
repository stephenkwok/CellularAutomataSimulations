package model.simulations;

import java.util.List;

import javafx.scene.paint.Color;
import model.cells.Cell;
import model.cells.CellStates;
import model.grid.Grid;

/**
 * 
 * @author Stephen
 *
 */
public class GameOfLife extends Simulation {
	
	public GameOfLife(Grid grid) {
		super(grid);
	}
	
	@Override
	protected void applyRulesToCell(Cell cell) {
		int liveNeighbors = countLiveNeighbors(cell);
		int nextState;
		if (isAlive(cell)) {
			if (liveNeighbors == 2 || liveNeighbors == 3) nextState = CellStates.GAME_OF_LIFE_ALIVE.value();
			else nextState = CellStates.GAME_OF_LIFE_DEAD.value();
		}
		else {
			if (liveNeighbors == 3) nextState = CellStates.GAME_OF_LIFE_ALIVE.value();
			else nextState = CellStates.GAME_OF_LIFE_DEAD.value();
		}
		cell.setState(nextState);
		cell.setHasChanged(true);
	}
	
	@Override
	protected void updateCellColor(Cell cell) {
		int currentState = cell.getCurrentState();
		if (currentState == CellStates.GAME_OF_LIFE_DEAD.value()) {
			cell.setFill(Color.BLACK);
		}
		else {
			cell.setFill(Color.WHITE);
		}
	}
	
	private int countLiveNeighbors(Cell cell) {
		List<Cell> neighbors = getGrid().getCardinalNeighbors(cell.getRow(), cell.getColumn());
		int count = 0;
		for (Cell neighbor : neighbors) {
			count += isAlive(neighbor) ? 1 : 0;
		}
		return count;
	}
	
	private boolean isAlive(Cell cell) {
		return cell.getState() == CellStates.GAME_OF_LIFE_ALIVE.value();
	}

}
