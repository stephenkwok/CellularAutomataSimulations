package model.grid;
import java.util.ArrayList;
import java.util.List;

import model.cells.Cell;

/**
 * Created by Stephen.Kwok on 7/21/2016.
 */
public abstract class Grid {

	private final int[] CARDINAL_ROW_OFFSETS = { -1, 1, 0, 0 };
	private final int[] CARDINAL_COLUMN_OFFSETS = { 0, 0, -1, 1 };
	private List<List<Cell>> cells;
	private int rows;
	private int columns;

	public Grid(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		initializeGrid();
	}

	/**
	 * Finds alternate neighbor for cell whose neighbor at given row
	 * and column does not exist because that point is off the grid
	 * 
	 * @param row: row of missing neighbor
	 * @param column of missing neighbor
	 * @return alternate neighbor as defined by subclass
	 */
	protected abstract Cell resolveMissingNeighbor(int row, int column);

	private void initializeGrid() {
		cells = new ArrayList<>();
		for (int row = 0; row < rows; row++) {
			List<Cell> rowOfCells = new ArrayList<>();
			for (int col = 0; col < columns; col++) {
				rowOfCells.add(null);
			}
			cells.add(rowOfCells);
		}
	}

	public void setCell(int row, int column, Cell cell) {
		cells.get(row).set(column, cell);
	}

	public Cell getCell(int row, int column) {
		return cells.get(row).get(column);
	}

	public List<Cell> getCardinalNeighbors(int row, int column) {
		List<Cell> neighbors = new ArrayList<>();
		for (int i = 0; i < CARDINAL_ROW_OFFSETS.length; i++) {
			int neighborRow = row + CARDINAL_ROW_OFFSETS[i];
			int neighborColumn = column + CARDINAL_COLUMN_OFFSETS[i];
			Cell neighbor = getNeighbor(neighborRow, neighborColumn);
			if (neighbor != null)
				neighbors.add(neighbor);
		}
		return neighbors;
	}

	public List<Cell> getAllNeighbors(int row, int column) {
		List<Cell> neighbors = new ArrayList<>();
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
				if (!(rowOffset == 0 && columnOffset == 0)) {
					int neighborRow = row + rowOffset;
					int neighborColumn = column + columnOffset;
					Cell neighbor = getNeighbor(neighborRow, neighborColumn);
					if (neighbor != null) {
						neighbors.add(neighbor);
					}
				}
			}
		}
		return neighbors;
	}

	private Cell getNeighbor(int row, int column) {
		if (inBounds(row, column))
			return cells.get(row).get(column);
		return resolveMissingNeighbor(row, column);
	}

	private boolean inBounds(int row, int column) {
		return row >= 0 && column >= 0 && row < rows && column < columns;
	}

	public int getNumberOfRows() {
		return rows;
	}

	public int getNumberOfColumns() {
		return columns;
	}
}
