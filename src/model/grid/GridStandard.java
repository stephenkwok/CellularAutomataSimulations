package model.grid;
import model.cells.Cell;

/**
 *
 * Creates Grid with standard edges that do not wrap around.
 *
 * Created by Stephen.Kwok on 7/21/2016.
 */
public class GridStandard extends Grid {

	/**
	 * Instantiates a standard Grid with edges that do not wrap around
	 * @param rows: number of rows in the Grid
	 * @param columns: number of columns in the Grid
	 */
    public GridStandard(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Returns a null Cell indicating that no alternate neighbor is available
     */
    @Override
    protected Cell resolveMissingNeighbor(int row, int column) {
        return null;
    }
}
