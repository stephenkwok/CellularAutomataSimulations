package model.grid;
import model.cells.Cell;

/**
 *
 * Creates Grid with standard edges that do not wrap around.
 *
 * Created by Stephen.Kwok on 7/21/2016.
 */
public class GridStandard extends Grid {

    public GridStandard(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    protected Cell resolveMissingNeighbor(int row, int column) {
        return null;
    }
}
