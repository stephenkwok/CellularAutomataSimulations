package model.simulations;

import java.util.List;

import javafx.scene.paint.Color;
import model.cells.Cell;
import model.cells.CellStates;
import model.grid.Grid;

/**
 * 
 * Simulation of Spreading Fire
 *
 * @author Stephen
 *
 */
public class SpreadingFire extends Simulation {

	private double probabilityCatch;
	
	/**
	 * Instantiates a Simulation of Spreading Fire
	 * @param grid: Simulation's Grid of Cells
	 * @param probabilityCatch: the probability that a tree with a neighbor on fire ends up catching on fire
	 */
	public SpreadingFire(Grid grid, double probabilityCatch) {
		super(grid);
		this.probabilityCatch = probabilityCatch;
	}

	/**
	 * Updates the Cell's state according to its current state and whether or not it has any neighbors on fire
	 * If the Cell is a tree and has a neighbor on fire, then it has a probabilityCatch chance of catching on fire
	 * If the Cell is burning, it becomes an empty Cell
	 * Otherwise, the Cell's state remains the same
	 */
	@Override
	protected void applyRulesToCell(Cell cell) {
		int nextState = cell.getState();
		if (isTree(cell) && hasNeighborOnFire(cell) && Math.random() < probabilityCatch) {
			nextState = CellStates.SPREADING_FIRE_BURNING.value();
		}
		else if (isBurning(cell)) {
			nextState = CellStates.SPREADING_FIRE_EMPTY.value();
		}
		cell.setState(nextState);
		cell.setHasChanged(true);
	}

	/**
	 * Changes color of the Cell to yellow if it is empty, green if it is a tree, and red if it is burning
	 */
	@Override
	protected void updateCellColor(Cell cell) {
		int currentState = cell.getCurrentState();
		if (currentState == CellStates.SPREADING_FIRE_EMPTY.value()) cell.setFill(Color.YELLOW);
		if (currentState == CellStates.SPREADING_FIRE_TREE.value()) cell.setFill(Color.GREEN);
		if (currentState == CellStates.SPREADING_FIRE_BURNING.value()) cell.setFill(Color.RED);		
	}
	
	/**
	 * 
	 * @param cell: Cell whose neigbhor's are being checked for burning
	 * @return true if any of the Cell's neighbors are on fire; false otherwise
	 */
	private boolean hasNeighborOnFire(Cell cell) {
		List<Cell> neighbors = getGrid().getCardinalNeighbors(cell.getRow(), cell.getColumn());
		for (Cell neighbor : neighbors) {
			if (isBurning(neighbor)) return true;
		}
		return false;
	}
	
	/**
	 * @param cell: Cell whose state is being checked
	 * @return true if the Cell is burning; false otherwise
	 */
	private boolean isBurning(Cell cell) {
		return cell.getState() == CellStates.SPREADING_FIRE_BURNING.value();
	}
	
	/**
	 * @param cell: Cell whose state is being checked
	 * @return true if the Cell is a tree; false otherwise
	 */
	private boolean isTree(Cell cell) {
		return cell.getState() == CellStates.SPREADING_FIRE_TREE.value();
	}

}
