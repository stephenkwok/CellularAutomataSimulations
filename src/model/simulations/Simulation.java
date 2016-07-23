package model.simulations;

import java.util.ArrayList;
import java.util.List;

import model.cells.Cell;
import model.grid.Grid;

public abstract class Simulation {
	
	private Grid grid;
	
	public Simulation(Grid grid) {
		this.grid = grid;
		initializeCellColors();
	}
	
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
		List<Integer> states = new ArrayList<Integer>();
		for (int row = 0; row < grid.getNumberOfRows(); row++) {
			for (int column = 0; column < grid.getNumberOfColumns(); column++) {
				Cell cell = grid.getCell(row, column);
				applyRulesToCell(cell);
				updateCellColor(cell);
				states.add(cell.getState());
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
