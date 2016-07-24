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
	 * Updates the cell's state according to the rules of the simulation 
	 * 
	 * @param cell: cell to apply rules to
	 */
	protected abstract void applyRulesToCell(Cell cell);
	
	/**
	 * Updates the cell's color according to its current state
	 * @param cell: cell whose color is to be updated
	 */
	protected abstract void updateCellColor(Cell cell);
	
	/**
	 * Sets the cell's initial color according to its initial state
	 */
	private void initializeCellColors() {
		for (int row = 0; row < grid.getNumberOfRows(); row++) {
			for (int column = 0; column < grid.getNumberOfColumns(); column++) {
				updateCellColor(grid.getCell(row, column));
			}
		}
	}
	
	/**
	 * Updates the state and color of every cell in the grid. Resets each cell's 
	 * hasChanged value back to false once all cells have been processed
	 */
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
	
	/**
	 * Resets the hasChanged value of each cell in the grid to false
	 */
	private void resetCellChangeStates() {
		for (int row = 0; row < grid.getNumberOfRows(); row++) {
            for (int column = 0; column < grid.getNumberOfColumns(); column++) {
                grid.getCell(row, column).setHasChanged(false);
            }
        }
	}
	
	/**
	 * @return the Simulation's grid
	 */
	public Grid getGrid() {
		return grid;
	}

}
