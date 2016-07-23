package model.grid;
import model.cells.Cell;

/**
 * a Grid with toroidal (wrapping) Grid edges
 * 
 * Created by Stephen.Kwok on 7/21/2016.
 */
public class GridToroidal extends Grid {

	/**
	 * Instantiates a toroidal Grid with edges that wrap around
	 * @param rows: number of rows in the Grid
	 * @param columns: number of columns in the Grid
	 */
    public GridToroidal(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Return alternate neighbor on other side of the Grid 
     * by wrapping 
     */
    @Override
    protected Cell resolveMissingNeighbor(int row, int column) {
        int neighborRow = getNeighborPosition(row, getNumberOfRows());
        int neighborColumn = getNeighborPosition(column, getNumberOfColumns());
        return getCell(neighborRow, neighborColumn);
    }

    /**
     * Finds the row or column of an alternate neighbor by wrapping
     * @param position: the neighbor's original row or column
     * @param totalPositions: the total number of rows or columns
     * @return the row or column of the alternate neighbor
     */
    private int getNeighborPosition(int position, int totalPositions) {
        if (position >= totalPositions) return position % totalPositions;
        else if (position < 0) return position + totalPositions;
        else return position;
    }
}
