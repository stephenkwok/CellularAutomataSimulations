package model.simulations;

import model.cells.Cell;
import model.grid.Grid;

/**
 * Base class for all Simulations. Provides framework for updating a Grid based on the rules of a Simulation
 * 
 * @author Stephen
 *
 */
public abstract class Simulation {
	
	private final Grid grid;
	
	/**
	 * Instantiates a Simulation
	 * @param grid: Simulation's Grid of Cells
	 */
	public Simulation(Grid grid) {
		this.grid = grid;
		initializeCellColors();
	}
	
	/**
	 * 
	 * @param cell
	 */
	protected abstract void applyRulesToCell(Cell cell);
	
	protected abstract void updateCellColor(Cell cell);
	
	private void initializeCellColors() {
		for (int row = 0; row < grid.getNumberOfRows(); row++) {
			for (int column = 0; column < grid.getNumberOfColumns(); column++) {
				updateCellColor(grid.getCell(row, column));
			}
		}
	}
	
	public void updateGrid() {
		for (int row = 0; row < grid.getNumberOfRows(); row++) {
			for (int column = 0; column < grid.getNumberOfColumns(); column++) {
				Cell cell = grid.getCell(row, column);
				applyRulesToCell(cell);
				updateCellColor(cell);
			}
		}
		resetCellChangeStates();
	}
	
	private void resetCellChangeStates() {
		for (int row = 0; row < grid.getNumberOfRows(); row++) {
            for (int column = 0; column < grid.getNumberOfColumns(); column++) {
                grid.getCell(row, column).setHasChanged(false);
            }
        }
	}
	
	public Grid getGrid() {
		return grid;
	}

}
