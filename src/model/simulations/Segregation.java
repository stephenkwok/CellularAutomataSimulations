package model.simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import model.cells.Cell;
import model.cells.CellStates;
import model.grid.Grid;

/**
 * Simulation for Segregation cellular automata
 * 
 * @author Stephen
 *
 */
public class Segregation extends Simulation {

	private List<Cell> currentEmptyCells;
	private List<Cell> newEmptyCells;
	private List<Cell> processedCells;
	private Random randomNumberGenerator;
	private double threshold;
	
	/**
	 * Instantiates Simulation of Segregation
	 * @param grid: Simulation's Grid of Cells
	 * @param threshold: threshold above which Cell's ratio of similar neighbors must be in order for Cell 
	 * to be satisfied with current location
	 */
	public Segregation(Grid grid, double threshold) {
		super(grid);
		this.threshold = threshold;
		randomNumberGenerator = new Random();
		currentEmptyCells = new ArrayList<>();
		newEmptyCells = new ArrayList<>();
		processedCells = new ArrayList<>();
		initializeEmptyCellList();
	}

	/**
	 * Updates the Grid according to the rules of the Simulation;
	 * Adds all newly vacated calls to list of current empty cells.
	 * Clears list of processed cells and newly vacated cells
	 */
	@Override
	public void updateGrid() {
		super.updateGrid();
		currentEmptyCells.addAll(newEmptyCells);
		processedCells.clear();
		newEmptyCells.clear();
	}
	
	/**
	 * Applies rules of Segregation to Cell depending on whether the Cell
	 * is empty of not empty
	 */
	@Override
	protected void applyRulesToCell(Cell cell) {
		if (cellIsEmpty(cell)) handleEmptyCell(cell);
		else handleNonEmptyCell(cell);
	}

	/**
	 * Changes the Cell's color according to its current state:
	 * Color is changed to red if the Cell is in state X, blue if the Cell is in state O, 
	 * and white if the Cell is empty
	 */
	@Override
	protected void updateCellColor(Cell cell) {
		int currentState = cell.getCurrentState();
		if (currentState == CellStates.SEGREGATION_X.value()) cell.setFill(Color.RED);
		if (currentState == CellStates.SEGREGATION_O.value()) cell.setFill(Color.BLUE);
		if (currentState == CellStates.SEGREGATION_EMPTY.value()) cell.setFill(Color.WHITE);
	}
	
	/**
	 * Leaves Cell alone if the cell has already been processed in the current frame;
	 * Otherwise, updates the Cell's state
	 * @param cell: empty Cell to be processed 
	 */
	private void handleEmptyCell(Cell cell) {
		if (cellAlreadyProcessed(cell)) return;
		else cell.setState(cell.getState());
	}
	
	/**
	 * Leaves Cell alone if the Cell has already been processed in the current frame
	 * or if there are no vacant locations for the Cell to move to; Moves cell to 
	 * vacancy if the Cell is not satisfied with its current location. 
	 * @param cell: non empty Cell to be processed
	 */
	private void handleNonEmptyCell(Cell cell) {
		if (currentEmptyCells.isEmpty() || cellAlreadyProcessed(cell)) return;
		if (!cellIsSatisfied(cell)) moveCellToVacancy(cell);
		else cell.setState(cell.getState());
	}
	
	/**
	 * Finds the number of similar neighbors surrounding a given Cell and 
	 * determines whether the Cell is satisfied with its current location.
	 * The Cell is considered satisfied if the percent of its neighbors
	 * that are similar to it is higher than the given threshold.
	 * 
	 * @param cell: Cell whose satisfaction is to be checked
	 * @return true if the Cell is satisfied; false otherwise
	 */
	private boolean cellIsSatisfied(Cell cell) {
		double totalNonEmptyNeighbors = 0;
		double similarNeighbors = 0;
		List<Cell> neighbors = getGrid().getAllNeighbors(cell.getRow(), cell.getColumn());
		for (Cell neighbor : neighbors) {
			if (!cellIsEmpty(neighbor)) {
				totalNonEmptyNeighbors++;
				if (cellsShareSameState(cell, neighbor)) {
					similarNeighbors++;
				}
			}
		}
		return totalNonEmptyNeighbors == 0 ? false : similarNeighbors / totalNonEmptyNeighbors > threshold;
	}
	
	/**
	 * Swaps the position of a given Cell with an empty Cell
	 * @param cell: Cell to be moved to a vacant location
	 */
	private void moveCellToVacancy(Cell cell) {
		Cell emptyCell = getEmptyCell();
		emptyCell.setState(cell.getState());
		updateCellColor(emptyCell);
		processedCells.add(emptyCell);
		currentEmptyCells.remove(emptyCell);
		cell.setState(CellStates.SEGREGATION_EMPTY.value());
		newEmptyCells.add(cell);
		processedCells.add(cell);
	}
	
	/**
	 * @return a random empty Cell
	 */
	private Cell getEmptyCell() {
		int numberOfEmptyCells = currentEmptyCells.size();
		int indexEmptyCell = randomNumberGenerator.nextInt(numberOfEmptyCells);
		return currentEmptyCells.get(indexEmptyCell);
	}
	
	/**
	 * Initializes the list of empty cells
	 */
	private void initializeEmptyCellList() {
		Grid grid = getGrid();
		for (int row = 0; row < grid.getNumberOfRows(); row++) {
			for (int column = 0; column < grid.getNumberOfColumns(); column++) {
				Cell cell = grid.getCell(row, column);
				if (cellIsEmpty(cell)) {
					currentEmptyCells.add(cell);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param cell: Cell whose state is being checked
	 * @return true if the Cell is empty; false otherwise
	 */
	private boolean cellIsEmpty(Cell cell) {
		return cell.getState() == CellStates.SEGREGATION_EMPTY.value();
	}
	
	/**
	 * Indicates whether two cells share the same state
	 * @param cell1: a Cell
	 * @param cell2: Cell first Cell is being compared to
	 * @return true if the two Cells have the same state; false otherwise
	 */
	private boolean cellsShareSameState(Cell cell1, Cell cell2) {
		return cell1.getState() == cell2.getState();
	}

	/**
	 * 
	 * @return true if given Cell has already been processed in the current frame; false otherwise
	 */
	private boolean cellAlreadyProcessed(Cell cell) {return processedCells.contains(cell);}

}
