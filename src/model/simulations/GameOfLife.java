package model.simulations;

import java.util.List;

import javafx.scene.paint.Color;
import model.cells.Cell;
import model.cells.CellStates;
import model.grid.Grid;

/**
 * 
 * A Simulation of Conway's Game of Life
 * 
 * @author Stephen
 *
 */
public class GameOfLife extends Simulation {
	
	/**
	 * Instantiates a Simulation of Game of Life
	 * @param grid: the Simulation's Grid of Cells
	 */
	public GameOfLife(Grid grid) {
		super(grid);
	}
	
	/**
	 * Updates the Cell's state according to its current state and the number of live neighbors it has;
	 * If the Cell is alive and has 2 or 3 live neighbors, it remains alive; Otherwise, it dies.
	 * If the Cell is dead, it can be revived if it has exactly 3 live neighbors; Otherwise, it remains dead
	 */
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
	
	/**
	 * Changes Cell's color to white if the Cell is dead or black if the Cell is alive
	 */
	@Override
	protected void updateCellColor(Cell cell) {
		int currentState = cell.getCurrentState();
		if (currentState == CellStates.GAME_OF_LIFE_DEAD.value()) {
			cell.setFill(Color.WHITE);
		}
		else {
			cell.setFill(Color.BLACK);
		}
	}
	
	/**
	 * Counts the number of live neighbors the given Cell has
	 * @param cell: Cell whose live neighbors are to be counted
	 * @return the number of live neighbors the given Cell has
	 */
	private int countLiveNeighbors(Cell cell) {
		List<Cell> neighbors = getGrid().getAllNeighbors(cell.getRow(), cell.getColumn());
		int count = 0;
		for (Cell neighbor : neighbors) {
			if (isAlive(neighbor)) {
				count++;
			}	
		}
		return count;
	}
	
	/**
	 * 
	 * @param cell: Cell whose state is being checked
	 * @return true if the Cell is alive; false otherwise
	 */
	private boolean isAlive(Cell cell) {
		return cell.getState() == CellStates.GAME_OF_LIFE_ALIVE.value();
	}

}
