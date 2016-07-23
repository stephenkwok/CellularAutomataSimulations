package model.grid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.cells.Cell;

/**
 * 
 * Base class for all Grids. Holds Simulation's matrix of Cells and retrieves neighbors for any given Cell
 * 
 * @author Stephen
 * 
 */
public abstract class Grid {

	private final int[] CARDINAL_ROW_OFFSETS = { -1, 1, 0, 0 };
	private final int[] CARDINAL_COLUMN_OFFSETS = { 0, 0, -1, 1 };
	private List<List<Cell>> cells;
	private int rows;
	private int columns;

	/**
	 * Instantiates a Grid containing a matrix of Cells
	 * @param rows: the number of rows in the Grid
	 * @param columns: the number of columns in the Grid
	 */
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

	/**
	 * Initializes Grid with row * column null Cells
	 */
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

	/**
	 * Sets Cell at given row and column to given Cell
	 * @param row: row where given Cell is to be placed
	 * @param column: column where given Cell is to be placed
	 * @param cell: the Cell to be placed at the given row and column
	 */
	public void setCell(int row, int column, Cell cell) {
		cells.get(row).set(column, cell);
	}

	/**
	 * Retrieves the Cell at a given row and column
	 * @param row: row of the Cell to be retrieved
	 * @param column: column of the Cell to be retrieved
	 * @return: Cell at the given row and column
	 */
	public Cell getCell(int row, int column) {
		return cells.get(row).get(column);
	}

	/**
	 * Searches the 4 cardinal directions (NORTH, SOUTH, EAST, WEST)
	 * for neighbors of the Cell at the given row and column
	 * @param row: row of the Cell
	 * @param column: column of the Cell
	 * @return List of cardinal neighbors for the Cell at given row and column
	 */
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

	/**
	 * Searches the 8 Cells adjacent to the Cell at the given row and column 
	 * @param row: row of the Cell
	 * @param column: column of the Cell
	 * @return List of all neighbors surrounding the Cell at the given row and column
	 */
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
	
	/**
	 * Searches for neighbors in the 4 cardinal directions that are multiplier - 1
	 * cells away from the given row and column
	 * @param multiplier: multiplier to be applied to base row/column offsets 
	 * @return List of extended cardinal neighbors (For example, if the row = 3, column = 3,
	 * multiplier = 2, then the method will return the neighbors at (5,3), (1,3), (3,5), (3,1)
	 */
	public List<Cell> getExtendedCardinalNeighobors(int row, int column, int multiplier) {
		if (multiplier == 0) return getCardinalNeighbors(row, column);
		for (int i = 0; i < CARDINAL_ROW_OFFSETS.length; i++) {
			CARDINAL_ROW_OFFSETS[i] *= multiplier;
			CARDINAL_COLUMN_OFFSETS[i] *= multiplier;
		}
		List<Cell> extendedCardinalNeighbors = getCardinalNeighbors(row, column);
		extendedCardinalNeighbors = filterNeighborList(extendedCardinalNeighbors, row, column);
		for (int i = 0; i < CARDINAL_ROW_OFFSETS.length; i++) {
			CARDINAL_ROW_OFFSETS[i] /= multiplier;
			CARDINAL_COLUMN_OFFSETS[i] /= multiplier;
		}
		return extendedCardinalNeighbors;
	}
	
	/**
	 * Removes neighbors from list of neighbors if neighbor found is actually
	 * the original Cell
	 * @param neighbors: list of neighbors to be filtered
	 * @param row: row of the original Cell
	 * @param column: column of the original Cell
	 */
	private List<Cell> filterNeighborList(List<Cell> neighbors, int row, int column) {
		return neighbors.stream().filter(cell -> !(cell.getRow() == row && cell.getColumn() == column)).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param row: row of neighbor to be returned
	 * @param column: column of neighbor to be returned
	 * @return the Cell at the given row and column, or an alternate Cell as defined by the subclass
	 */
	private Cell getNeighbor(int row, int column) {
		if (inBounds(row, column))
			return cells.get(row).get(column);
		return resolveMissingNeighbor(row, column);
	}

	/**
	 * Checks whether a point is within the bounds of the grid
	 * @param row: row of the point
	 * @param column: column of the point
	 * @return true if the point is on the Grid; false otherwise
	 */
	private boolean inBounds(int row, int column) {
		return row >= 0 && column >= 0 && row < rows && column < columns;
	}

	/**
	 * @return the number of rows in the grid
	 */
	public int getNumberOfRows() {
		return rows;
	}

	/**
	 * @return the number of columns in the grid
	 */
	public int getNumberOfColumns() {
		return columns;
	}
}
