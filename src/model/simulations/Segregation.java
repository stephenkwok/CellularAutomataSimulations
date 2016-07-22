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
	private double threshold;
	private Random randomNumberGenerator;
	
	public Segregation(Grid grid, double threshold) {
		super(grid);
		this.threshold = threshold;
		currentEmptyCells = new ArrayList<>();
		newEmptyCells = new ArrayList<>();
		processedCells = new ArrayLis<>();
		initializeEmptyCellList();
	}

	@Override
	public void updateGrid() {
		super.updateGrid();
		currentEmptyCells.addAll(newEmptyCells);
		processedCells.clear();
		newEmptyCells.clear();
	}
	
	@Override
	protected void applyRulesToCell(Cell cell) {
		if (cellIsEmpty(cell)) handleEmptyCell(cell);
		else handleNonEmptyCell(cell);
	}

	@Override
	protected void updateCellColor(Cell cell) {
		int currentState = cell.getCurrentState();
		if (currentState == CellStates.SEGREGATION_X.value()) cell.setFill(Color.RED);
		if (currentState == CellStates.SEGREGATION_O.value()) cell.setFill(Color.BLUE);
		if (currentState == CellStates.SEGREGATION_EMPTY.value()) cell.setFill(Color.WHITE);
	}
	
	private void handleEmptyCell(Cell cell) {
		if (cellAlreadyProcessed(cell)) return;
		else cell.setState(cell.getState());
	}
	
	private void handleNonEmptyCell(Cell cell) {
		if (currentEmptyCells.isEmpty() || cellAlreadyProcessed(cell)) return;
		if (!cellIsSatisfied(cell)) moveCellToVacancy(cell);
		else cell.setState(cell.getState());
	}
	
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
	
	private Cell getEmptyCell() {
		int numberOfEmptyCells = currentEmptyCells.size();
		int indexEmptyCell = randomNumberGenerator.nextInt(numberOfEmptyCells);
		return currentEmptyCells.get(indexEmptyCell);
	}
	
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
	
	private boolean cellIsEmpty(Cell cell) {
		return cell.getState() == CellStates.SEGREGATION_EMPTY.value();
	}
	
	private boolean cellsShareSameState(Cell cell1, Cell cell2) {
		return cell1.getState() == cell2.getState();
	}

	private boolean cellAlreadyProcessed(Cell cell) (return processedCells.contains(cell));}

}
