package model.simulations;

import java.util.List;

import javafx.scene.paint.Color;
import model.cells.Cell;
import model.cells.CellStates;
import model.grid.Grid;

public class SpreadingFire extends Simulation {

	private double probabilityCatch;
	
	public SpreadingFire(Grid grid, double probabilityCatch) {
		super(grid);
		this.probabilityCatch = probabilityCatch;
	}

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

	@Override
	protected void updateCellColor(Cell cell) {
		int currentState = cell.getCurrentState();
		if (currentState == CellStates.SPREADING_FIRE_EMPTY.value()) cell.setFill(Color.YELLOW);
		if (currentState == CellStates.SPREADING_FIRE_TREE.value()) cell.setFill(Color.GREEN);
		if (currentState == CellStates.SPREADING_FIRE_BURNING.value()) cell.setFill(Color.RED);		
	}
	
	private boolean hasNeighborOnFire(Cell cell) {
		List<Cell> neighbors = getGrid().getCardinalNeighbors(cell.getRow(), cell.getColumn());
		for (Cell neighbor : neighbors) {
			if (isBurning(neighbor)) return true;
		}
		return false;
	}
	
	private boolean isBurning(Cell cell) {
		return cell.getState() == CellStates.SPREADING_FIRE_BURNING.value();
	}
	
	private boolean isTree(Cell cell) {
		return cell.getState() == CellStates.SPREADING_FIRE_TREE.value();
	}

}
