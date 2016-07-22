package model.simulations;

import model.cells.Cell;
import model.grid.Grid;

public abstract class Simulation {
	
	private Grid grid;
	
	public Simulation(Grid grid) {
		this.grid = grid;
	}
	
	protected abstract void applyRulesToCell(Cell cell);
	
	protected abstract void updateCellColor(Cell cell);
	
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
